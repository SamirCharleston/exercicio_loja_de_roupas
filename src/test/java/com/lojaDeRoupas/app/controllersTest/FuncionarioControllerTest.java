package com.lojaDeRoupas.app.controllersTest;

import com.lojaDeRoupas.app.config.messageHandling.errorMessages.ExceptionMessages;
import com.lojaDeRoupas.app.config.messageHandling.successMessages.SuccessMessages;
import com.lojaDeRoupas.app.controllers.FuncionarioController;
import com.lojaDeRoupas.app.coreClasses.ResponseWrapper;
import com.lojaDeRoupas.app.dtos.dtoEntrada.FuncionarioDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoSaida.FuncionarioDtoSaida;
import com.lojaDeRoupas.app.entities.FuncionarioEntity;
import com.lojaDeRoupas.app.repositories.FuncionarioRepository;
import com.lojaDeRoupas.app.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class FuncionarioControllerTest {
    @MockBean
    FuncionarioRepository funcionarioRepository;
    @Autowired
    FuncionarioController funcionarioController;
    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    ModelMapper modelMapper;
    List<FuncionarioEntity> funcionarios = new ArrayList<>();
    FuncionarioEntity funcionario;
    FuncionarioControllerTest(){
        funcionario = new FuncionarioEntity();

        FuncionarioEntity funcionario1 = new FuncionarioEntity();
        FuncionarioEntity funcionario2 = new FuncionarioEntity();

        funcionario.setId(UUID.randomUUID());
        funcionario.setStatus(true);
        funcionario.setNome("Pedro");
        funcionario.setIdade(25L);
        funcionario.setMatricula(33333L);

        funcionario1.setId(UUID.randomUUID());
        funcionario1.setStatus(true);
        funcionario1.setNome("Samir");
        funcionario1.setIdade(30L);
        funcionario1.setMatricula(11111L);
        funcionarios.add(funcionario1);

        funcionario2.setId(UUID.randomUUID());
        funcionario2.setStatus(true);
        funcionario2.setNome("Joao");
        funcionario2.setIdade(18L);
        funcionario2.setMatricula(22222L);
        funcionarios.add(funcionario2);
    }
    @BeforeEach
    void setup(){
        List<FuncionarioEntity> funcionariosOrdemInversa = new ArrayList<FuncionarioEntity>();
        funcionariosOrdemInversa.add(funcionarios.get(1));
        funcionariosOrdemInversa.add(funcionarios.get(0));

        when(funcionarioRepository.save(funcionarios.get(0)))
                .thenReturn(funcionario);
        when(funcionarioRepository.existsById(funcionarios.get(0).getId()))
                .thenReturn(true);
        when(funcionarioRepository.getReferenceById(funcionarios.get(0).getId()))
                .thenReturn(funcionarios.get(0));
        when(funcionarioRepository.findById(funcionario.getId()))
                .thenReturn(null);
        when(funcionarioRepository.existePorMatricula(funcionarios.get(0).getMatricula()))
                .thenReturn(true);
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);
        when(funcionarioRepository.findAll(Sort.by(Sort.Direction.DESC, "id")))
                .thenReturn(funcionariosOrdemInversa);
        when(funcionarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(funcionarios);
    }
    @Test
    @DisplayName("Teste de integracao de um registro bem sucedido de um funcionario")
    void cadastrarFuncionario_bemSucedido(){
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.register(modelMapper.map(funcionario, FuncionarioDtoEntrada.class));

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessages.CREATED, response.getBody().getMessage());
    }
    @Test
    @DisplayName("Teste de integracao que lanca uma excecao por matricula ja esxistente")
    void cadastrarFuncionario_comExcecaoDeMatriculaExistente(){
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.register(modelMapper.map(funcionarios.get(0), FuncionarioDtoEntrada.class));

        assertNotNull(response.getBody());
        assertThrows(Exception.class, () -> {
            funcionarioService.register(modelMapper.map(funcionarios.get(0), FuncionarioDtoEntrada.class));
        });
        assertEquals(response.getBody().getErrors(), ExceptionMessages.IDENTIFIER_ALREADY_REGISTERED);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao de procura de um funcionario pelo id bem sucedido")
    void procurarFuncionarioPorId_bemSucedido(){
        ResponseEntity<ResponseWrapper<FuncionarioDtoSaida>> response =
                funcionarioController.findById(funcionarios.get(0).getId());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getObject());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao que lanca uma excecao de funcionario nao encontrado")
    void procurarFuncionarioPorId_comExecaoFuncionarioNaoEncontrado(){
        ResponseEntity<ResponseWrapper<FuncionarioDtoSaida>> response =
                funcionarioController.findById(funcionario.getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getErrors(), ExceptionMessages.ENTITY_NOT_FOUND);
        assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.findById(funcionario.getId());
        });
    }
    @Test
    @DisplayName("Teste de integracao para listar os funcionarios, bem sucedido")
    void listarFuncionarios_bemSucedido(){
        ResponseEntity<ResponseWrapper<List<FuncionarioDtoSaida>>> response =
                funcionarioController.find(2L, Sort.Direction.ASC.name());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getObject());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getObject().get(0).getId(), funcionarios.get(0).getId());
    }
    @Test
    @DisplayName("Teste de integracao para listar os funcionarios em ordem inversa")
    void listarFuncionarios_emOrdemInversa(){
        ResponseEntity<ResponseWrapper<List<FuncionarioDtoSaida>>> response =
                funcionarioController.find(2L, Sort.Direction.DESC.name());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getObject());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getObject().get(0).getId(), funcionarios.get(1).getId());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo atualizar quando o funcionario existe")
    void atualizarFuncionario_bemSucedido(){
        funcionario.setId(funcionarios.get(0).getId());
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.update(modelMapper.map(funcionario, FuncionarioDtoEntrada.class));

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessages.UPDATED, response.getBody().getMessage());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo atualizar quando o funcionario nao existe")
    void atualizarFuncionario_comExcecaoFuncionarioNaoEncontrado(){
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.update(modelMapper.map(funcionario, FuncionarioDtoEntrada.class));

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getErrors(), ExceptionMessages.ENTITY_NOT_FOUND);
        assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.update(modelMapper.map(funcionario, FuncionarioDtoEntrada.class));
        });
    }
    @Test
    @DisplayName("Teste de integracao para o metodo deletar quando o funcionario existe")
    void deletarFuncionario_bemSucedido(){
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.delete(funcionarios.get(0).getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessages.DELETED, response.getBody().getMessage());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo deletar quando o funcionario nao existe")
    void deletarFuncionario_comExcecaoFuncionarioNaoEncontrado(){
        ResponseEntity<ResponseWrapper<String>> response =
                funcionarioController.delete(funcionario.getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getErrors(), ExceptionMessages.ENTITY_NOT_FOUND);
        assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.delete(funcionario.getId());
        });
    }
}
