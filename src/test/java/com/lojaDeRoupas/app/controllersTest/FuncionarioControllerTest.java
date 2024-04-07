package com.lojaDeRoupas.app.controllersTest;

import com.lojaDeRoupas.app.config.messageHandling.successMessages.SuccessMessages;
import com.lojaDeRoupas.app.controllers.FuncionarioController;
import com.lojaDeRoupas.app.coreClasses.ResponseWrapper;
import com.lojaDeRoupas.app.dtos.dtoEntrada.FuncionarioDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoSaida.FuncionarioDtoSaida;
import com.lojaDeRoupas.app.entities.FuncionarioEntity;
import com.lojaDeRoupas.app.repositories.FuncionarioRepository;
import com.lojaDeRoupas.app.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
public class FuncionarioControllerTest {
    @MockBean
    FuncionarioRepository funcionarioRepository;
    @Autowired
    FuncionarioController funcionarioController;
    @Autowired
    ModelMapper modelMapper;
    List<FuncionarioEntity> funcionarios;
    FuncionarioEntity funcionario;
    FuncionarioControllerTest(){
        funcionarios = new ArrayList<>();
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
        List<FuncionarioEntity> funcionarios = this.funcionarios;
        when(funcionarioRepository.save(funcionarios.get(0))).thenReturn(funcionario);
        when(funcionarioRepository.existsById(funcionarios.get(0).getId())).thenReturn(true);
        when(funcionarioRepository.findById(funcionarios.get(0).getId())).thenReturn(Optional.of(funcionarios.get(0)));
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
    @DisplayName("Teste de integracao de procura de um funcionario pelo id bem sucedido")
    void procurarFuncionarioPorId_bemSucedido(){
        ResponseEntity<ResponseWrapper<FuncionarioDtoSaida>> response =
                funcionarioController.findById(funcionario.getId());

        System.out.println(response.getBody().getErrors());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getObject());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
