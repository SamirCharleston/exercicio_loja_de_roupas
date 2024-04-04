package com.lojaDeRoupas.app.servicesTest;

import com.lojaDeRoupas.app.dtos.dtoEntrada.ProdutoDtoEntrada;
import com.lojaDeRoupas.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.lojaDeRoupas.app.service.VendaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class VendaServiceTest {
    @Autowired
    VendaService vendaService;
    @Test
    @DisplayName("teste de calculo do valor total")
    void testeDeCalculoDoTotal(){
        ProdutoDtoEntrada produto1 = new ProdutoDtoEntrada();
        ProdutoDtoEntrada produto2 = new ProdutoDtoEntrada();

        produto1.setValor(BigDecimal.valueOf(10));
        produto2.setValor(BigDecimal.valueOf(20.5));

        List<ProdutoDtoEntrada> produtos = Arrays.asList(produto1, produto2);

        double resultado = vendaService.calcularValorTotal(produtos);

        Assertions.assertEquals(30.5, resultado);
    }
    @Test
    @DisplayName("Teste de calculo de valor total com lista vazia")
    void calcularValorTotalComListaVazia() {

        List<ProdutoDtoEntrada> produtos = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendaService.calcularValorTotal(produtos));
    }

    @Test
    @DisplayName("Teste se a venda for cancelada deve zerar o valor total")
    void verificarStatusQuandoCancelada() {

        VendaDtoEntrada venda = new VendaDtoEntrada();
        venda.setStatusDaVenda("CANCELADO");

        ProdutoDtoEntrada produto1 = new ProdutoDtoEntrada();
        ProdutoDtoEntrada produto2 = new ProdutoDtoEntrada();
        produto1.setValor(BigDecimal.valueOf(100.0));
        produto2.setValor(BigDecimal.valueOf(200.0));

        venda.setProdutos(Arrays.asList(produto1, produto2));
        venda.setValorTotal(BigDecimal.valueOf(300.0));

        VendaDtoEntrada vendaAtualizada = vendaService.verificarStatus(venda);

        Assertions.assertEquals("CANCELADO", vendaAtualizada.getStatusDaVenda());
        Assertions.assertNull(vendaAtualizada.getProdutos());
        Assertions.assertEquals(BigDecimal.valueOf(0.0), vendaAtualizada.getValorTotal());
    }

    @Test
    @DisplayName("Teste com status invalido")
    void verificarStatusComStatusInvalido() {
        VendaDtoEntrada venda = new VendaDtoEntrada();
        venda.setStatusDaVenda(null);

        Exception exception = Assertions.assertThrows(Exception.class, () -> vendaService.verificarStatus(venda));
    }
}
