package com.lojaDeRoupas.app.controllersTest;

import com.lojaDeRoupas.app.config.messageHandling.errorMessages.ExceptionMessages;
import com.lojaDeRoupas.app.config.messageHandling.successMessages.SuccessMessages;
import com.lojaDeRoupas.app.controllers.ClienteController;
import com.lojaDeRoupas.app.coreClasses.ResponseWrapper;
import com.lojaDeRoupas.app.dtos.dtoEntrada.ClienteDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoSaida.ClienteDtoSaida;
import com.lojaDeRoupas.app.entities.ClienteEntity;
import com.lojaDeRoupas.app.repositories.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteControllerTest {
    @MockBean
    ClienteRepository clienteRepository;
    @Autowired
    ClienteController clienteController;
    @Autowired
    ModelMapper modelMapper;
    List<ClienteEntity> clientes = new ArrayList<ClienteEntity>();
    ClienteEntity cliente = new ClienteEntity();
    ClienteControllerTest(){
        List<ClienteEntity> clientes = this.clientes;

        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setId(UUID.randomUUID());
        cliente1.setStatus(true);
        cliente1.setNome("Samir");
        cliente1.setIdade(34L);
        cliente1.setCpf("088888888");
        cliente1.setTelefone("11111111111");
        clientes.add(cliente1);

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setId(UUID.randomUUID());
        cliente2.setStatus(true);
        cliente2.setNome("Maria");
        cliente2.setIdade(35L);
        cliente2.setCpf("0880000000");
        cliente2.setTelefone("35555455");
        clientes.add(cliente2);

        cliente.setId(UUID.randomUUID());
        cliente.setStatus(true);
        cliente.setNome("João");
        cliente.setIdade(36L);
        cliente.setCpf("08800800");
        cliente.setTelefone("3555258");
    }
    @BeforeEach
    void setup() {
        ClienteEntity cliente1 = clientes.get(0);
        ClienteEntity cliente2 = clientes.get(1);

        //Lista para montada com a ordem de clientes inversa, para simular as ordenacoes
        List<ClienteEntity> clientesOrdemInversa = new ArrayList<>();
        clientesOrdemInversa.add(cliente2);
        clientesOrdemInversa.add(cliente1);

        when(clienteRepository.findAll()).thenReturn(clientes);
        when(clienteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(clientesOrdemInversa);

        when(clienteRepository.findById(cliente1.getId())).thenReturn(Optional.of(cliente1));
        when(clienteRepository.findByTelefone(cliente1.getTelefone())).thenReturn(cliente1);
        when(clienteRepository.findByCpf(cliente1.getCpf())).thenReturn(cliente1);
        when(clienteRepository.existsByCpf(cliente1.getCpf())).thenReturn(true);
        when(clienteRepository.existsById(cliente1.getId())).thenReturn(true);
        when(clienteRepository.getReferenceById(cliente1.getId())).thenReturn(cliente1);
        when(clienteRepository.save(cliente1)).thenReturn(cliente1);

        when(clienteRepository.findById(cliente2.getId())).thenReturn(Optional.of(cliente2));
        when(clienteRepository.findByTelefone(cliente2.getTelefone())).thenReturn(cliente2);
        when(clienteRepository.findByCpf(cliente2.getCpf())).thenReturn(cliente2);
        when(clienteRepository.existsById(cliente2.getId())).thenReturn(true);
        when(clienteRepository.existsByCpf(cliente2.getCpf())).thenReturn(true);
        when(clienteRepository.getReferenceById(cliente2.getId())).thenReturn(cliente2);
        when(clienteRepository.save(cliente2)).thenReturn(cliente2);
    }
    @Test
    @DisplayName("Teste de integracao para o metodo criar")
    void clienteCadastrado_ComSucesso() {
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(cliente, ClienteDtoEntrada.class);
        //O front envia os dados do usuario para a controller
        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.register(clienteDtoEntrada);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessages.CREATED, response.getBody().getMessage());
    }
    @Test
    @DisplayName("Teste de integracao para lancar uma execao quando o cpf ja existe")
    void clienteCadastrado_ExcecaoCpfJaExiste() {
        //Pega um cliente que ja existe no array de clientes e tenta pesistir novamente
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(clientes.get(0), ClienteDtoEntrada.class);
        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.register(clienteDtoEntrada);

        //Faz as verificacoes para se certificar que a execao foi lancada
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ExceptionMessages.CPF_ALREADY_IN_USE, response.getBody().getErrors());
    }
    @Test
    @DisplayName("Teste de integracao para buscar um cliente pelo id")
    void clienteBuscar_PorIdComSucesso() {
        ResponseEntity<ResponseWrapper<ClienteDtoSaida>> response =
                clienteController.findById(clientes.get(0).getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao para lancar uma excessao quando o usuario nao for encontrado no banco de daddos")
    void clienteBusca_ExcecaoUsuarioNaoEncontrado(){
        ResponseEntity<ResponseWrapper<ClienteDtoSaida>> response =
                clienteController.findById(cliente.getId());

        Assertions.assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ExceptionMessages.ENTITY_NOT_FOUND, response.getBody().getErrors());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo listar bem sucedido")
    void clienteListar_bemSucedido() {
        ResponseEntity<ResponseWrapper<List<ClienteDtoSaida>>> response =
                clienteController.find(2L, Sort.Direction.ASC.name());

        assertNotNull(response.getBody());
        assertFalse(response.getBody().getObject().isEmpty());
        assertEquals(clientes.get(0).getId(), response.getBody().getObject().get(0).getId());
        assertEquals(2, response.getBody().getObject().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo listar bem sucedido com ordem invertida")
    void clienteListar_bemSucedidoComOrdemInvertida() {
        ResponseEntity<ResponseWrapper<List<ClienteDtoSaida>>> response =
                clienteController.find(2L, Sort.Direction.DESC.name());

        assertNotNull(response.getBody());
        assertEquals(clientes.get(1).getId(), response.getBody().getObject().get(0).getId());
        assertEquals(2, response.getBody().getObject().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste de integracao para o metodo atualizar quando o usuario existe")
    void clienteAtualizar_bemSucedido() {
        this.cliente.setId(clientes.get(0).getId());
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(cliente, ClienteDtoEntrada.class);

        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.update(clienteDtoEntrada);

        assertNotNull(response.getBody());
        assertEquals(SuccessMessages.UPDATED, response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo atualizar quando o usuario nao existe")
    void clienteAtualizar_excecaoUsuarioNaoExiste(){
        this.cliente.setId(UUID.randomUUID());
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(cliente, ClienteDtoEntrada.class);

        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.update(clienteDtoEntrada);

        assertNotNull(response.getBody());
        assertEquals(ExceptionMessages.ENTITY_NOT_FOUND, response.getBody().getErrors());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo deletar quando o usuario existe")
    void clienteDeletar_bemSucedido() {
        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.delete(clientes.get(0).getId());

        assertNotNull(response.getBody());
        assertEquals(SuccessMessages.DELETED, response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("Teste de integracao para o metodo deletar quando o usuario nao existe")
    void clienteDeletar_excecaoUsuarioNaoExiste(){
        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.delete(cliente.getId());

        assertNotNull(response.getBody());
        assertEquals(ExceptionMessages.ENTITY_NOT_FOUND, response.getBody().getErrors());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
