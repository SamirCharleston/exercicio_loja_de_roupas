package com.lojaDeRoupas.app.controllersTest;

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
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteControllerTest {
    @MockBean
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteController clienteController;
    @Autowired
    private ModelMapper modelMapper;
    private List<ClienteEntity> clientes = new ArrayList<ClienteEntity>();
    private ClienteEntity cliente = new ClienteEntity();
    @BeforeEach
    void setup() {
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

        when(clienteRepository.findById(cliente1.getId())).thenReturn(Optional.of(cliente1));
        when(clienteRepository.findByTelefone(cliente1.getTelefone())).thenReturn(cliente1);
        when(clienteRepository.findByCpf(cliente1.getCpf())).thenReturn(cliente1);
        when(clienteRepository.existsByCpf(cliente1.getCpf())).thenReturn(true);
        when(clienteRepository.getReferenceById(cliente1.getId())).thenReturn(cliente1);
        when(clienteRepository.save(cliente1)).thenReturn(cliente1);

        when(clienteRepository.findById(cliente2.getId())).thenReturn(Optional.of(cliente2));
        when(clienteRepository.findByTelefone(cliente2.getTelefone())).thenReturn(cliente2);
        when(clienteRepository.findByCpf(cliente2.getCpf())).thenReturn(cliente2);
        when(clienteRepository.existsByCpf(cliente2.getCpf())).thenReturn(true);
        when(clienteRepository.getReferenceById(cliente2.getId())).thenReturn(cliente2);
        when(clienteRepository.save(cliente2)).thenReturn(cliente2);
    }
    @Test
    @DisplayName("Teste de integracao para o metodo criar")
    void Cliente_Cadastrado_ComSucesso() {
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(cliente, ClienteDtoEntrada.class);
        //O front envia os dados do usuario para a controller
        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.register(clienteDtoEntrada);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(SuccessMessages.CREATED, response.getBody().getMessage());
    }
    @Test
    @DisplayName("Teste de integracao para buscar um cliente pelo id")
    void Clente_BuscarPorId_Id() {
        ResponseEntity<ResponseWrapper<ClienteDtoSaida>> response =
                clienteController.findById(this.clientes.get(0).getId());

        System.out.println(response.getBody());
        Assertions.assertNotNull(response);
    }
    @Test
    @DisplayName("Teste de integracao para o metodo listar")
    void clienteControllerTestListar() {
        ResponseEntity<ResponseWrapper<List<ClienteDtoSaida>>> response =
                clienteController.find(2L, "ASC");

        System.out.println(response.getBody().getErrors());
        Assertions.assertNotNull(response);
//        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Teste de integracao para o metodo atualizar quando o usuario existe")
    void clienteControllerTestAtualizar() {
        this.cliente.setId(clientes.get(0).getId());
        ClienteDtoEntrada clienteDtoEntrada = modelMapper.map(clientes.get(0), ClienteDtoEntrada.class);

        ResponseEntity<ResponseWrapper<String>> response =
                clienteController.update(clienteDtoEntrada);

//        System.out.println(cliente.getId());
        System.out.println(response.getBody().getErrors());
        Assertions.assertNotNull(response);
//        Assertions.assertEquals(200, response.getStatusCode().value());
//        Assertions.assertEquals(SuccessMessages.UPDATED, response.getBody().getMessage());
    }

}
