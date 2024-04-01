package com.lojaDeRoupas.app.controllerTest;

import com.lojaDeRoupas.app.config.messageHandling.successMessages.SuccessMessages;
import com.lojaDeRoupas.app.controllers.VendaController;
import com.lojaDeRoupas.app.coreClasses.ResponseWrapper;
import com.lojaDeRoupas.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoSaida.VendaDtoSaida;
import com.lojaDeRoupas.app.service.VendaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VendaControllerTest {
    @Mock
    private VendaService vendaService;

    @Autowired
    private VendaController vendaController;

    @Test
    void registerTest() throws Exception {
        VendaDtoEntrada vendaDtoEntrada = new VendaDtoEntrada();
        vendaDtoEntrada.setQuantityToList(10L);
        vendaDtoEntrada.setDescOrder(true);

        when(vendaService.register(vendaDtoEntrada)).thenReturn(SuccessMessages.CREATED);

        ResponseEntity<ResponseWrapper<String>> responseEntity = vendaController.register(vendaDtoEntrada);

        assertEquals(SuccessMessages.CREATED, responseEntity.getBody().getMessage());
    }

    @Test
    void findTest() throws Exception {
        Long quantity = 10L;
        String order = "ASC";

        when(vendaService.find(quantity, order)).thenReturn(Collections.emptyList());

        ResponseEntity<ResponseWrapper<List<VendaDtoSaida>>> responseEntity = vendaController.find(quantity, order);

        assertEquals(10, responseEntity.getBody().getObject().size());
    }

    @Test
    void findByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        VendaDtoSaida vendaDtoSaida = new VendaDtoSaida();
        vendaDtoSaida.setId(id);

        when(vendaService.findById(id)).thenReturn(vendaDtoSaida);

        ResponseEntity<ResponseWrapper<VendaDtoSaida>> responseEntity = vendaController.findById(id);

        assertEquals(id, responseEntity.getBody().getObject().getId());
    }

    @Test
    void updateTest() throws Exception {
        VendaDtoEntrada vendaDtoEntrada = new VendaDtoEntrada();
        vendaDtoEntrada.setQuantityToList(5L);
        vendaDtoEntrada.setDescOrder(true);

        when(vendaService.update(vendaDtoEntrada)).thenReturn(SuccessMessages.UPDATED);

        ResponseEntity<ResponseWrapper<String>> responseEntity = vendaController.update(vendaDtoEntrada);

        assertEquals(SuccessMessages.UPDATED, responseEntity.getBody().getMessage());
    }

    @Test
    void deleteTest() throws Exception {
        UUID id = UUID.randomUUID();

        when(vendaService.delete(id)).thenReturn(SuccessMessages.DELETED);

        ResponseEntity<ResponseWrapper<String>> responseEntity = vendaController.delete(id);

        assertEquals(SuccessMessages.DELETED, responseEntity.getBody().getMessage());
    }
}
