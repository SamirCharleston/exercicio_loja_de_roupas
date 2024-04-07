package com.lojaDeRoupas.app.controllersTest;

import com.lojaDeRoupas.app.config.messageHandling.successMessages.SuccessMessages;
import com.lojaDeRoupas.app.controllers.VendaController;
import com.lojaDeRoupas.app.coreClasses.ResponseWrapper;
import com.lojaDeRoupas.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.lojaDeRoupas.app.entities.ClienteEntity;
import com.lojaDeRoupas.app.entities.FuncionarioEntity;
import com.lojaDeRoupas.app.entities.ProdutoEntity;
import com.lojaDeRoupas.app.entities.VendaEntity;
import com.lojaDeRoupas.app.repositories.ClienteRepository;
import com.lojaDeRoupas.app.repositories.FuncionarioRepository;
import com.lojaDeRoupas.app.repositories.VendaRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VendaControllerTest {
    @MockBean
    VendaRepository vendaRepository;
    @MockBean
    ClienteRepository clienteRepository;
    @MockBean
    FuncionarioRepository funcionarioRepository;
    @Autowired
    VendaController vendaController;
    @Autowired
    ModelMapper modelMapper;
    List<VendaEntity> vendas = new ArrayList<VendaEntity>();
    VendaControllerTest(){
        VendaEntity venda1 = new VendaEntity();
        VendaEntity venda2 = new VendaEntity();

        ProdutoEntity produto1 = new ProdutoEntity();
        ProdutoEntity produto2 = new ProdutoEntity();
        ProdutoEntity produto3 = new ProdutoEntity();

        produto1.setId(UUID.randomUUID());
        produto1.setStatus(true);
        produto1.setNome("Camiseta");
        produto1.setValor(BigDecimal.valueOf(100.0));

        produto2.setId(UUID.randomUUID());
        produto2.setStatus(true);
        produto2.setNome("Calca");
        produto2.setValor(BigDecimal.valueOf(200.0));

        produto3.setId(UUID.randomUUID());
        produto3.setStatus(true);
        produto3.setNome("Bermuda");
        produto3.setValor(BigDecimal.valueOf(150.0));

        venda1.setId(UUID.randomUUID());
        venda1.setStatus(true);
        venda1.setStatusDaVenda("concluido");
        venda1.setValorTotal(BigDecimal.valueOf(2.5d));
        venda1.setEnderecoDaEntrega("Rua arapongas");
        venda1.setFuncionario(new FuncionarioEntity());
        venda1.setCliente(new ClienteEntity());

        venda1.getFuncionario().setId(UUID.randomUUID());
        venda1.getFuncionario().setStatus(true);
        venda1.getFuncionario().setMatricula(15255L);
        venda1.getFuncionario().setNome("Samir");
        venda1.getFuncionario().setIdade(34L);

        venda1.getCliente().setId(UUID.randomUUID());
        venda1.getCliente().setStatus(true);
        venda1.getCliente().setCpf("088888888");
        venda1.getCliente().setTelefone("11111111111");

        venda1.setProdutos(new ArrayList<>());
        venda1.getProdutos().add(produto1);
        venda1.getProdutos().add(produto2);

        venda2.setId(UUID.randomUUID());
        venda2.setStatus(true);
        venda2.setStatusDaVenda("concluido");
        venda2.setValorTotal(BigDecimal.valueOf(15d));
        venda2.setEnderecoDaEntrega("Rua morena");
        venda2.setFuncionario(new FuncionarioEntity());
        venda2.setCliente(new ClienteEntity());

        venda2.getFuncionario().setId(UUID.randomUUID());
        venda2.getFuncionario().setStatus(true);
        venda2.getFuncionario().setMatricula(15333L);
        venda2.getFuncionario().setNome("Douglas");
        venda2.getFuncionario().setIdade(25L);

        venda2.getCliente().setId(UUID.randomUUID());
        venda2.getCliente().setStatus(true);
        venda2.getCliente().setCpf("52856325");
        venda2.getCliente().setTelefone("55555555");

        venda2.setProdutos(new ArrayList<>());
        venda2.getProdutos().add(produto3);

        vendas.add(venda1);
        vendas.add(venda2);
    }
    @BeforeEach
    void setUp() {
        VendaEntity venda1 = vendas.get(0);

        when(vendaRepository.save(venda1)).thenReturn(venda1);
        when(funcionarioRepository.findByMatricula(venda1.getFuncionario().getMatricula()))
                .thenReturn(Optional.of(venda1.getFuncionario()));
        when(funcionarioRepository.existsByMatricula(venda1.getFuncionario().getMatricula()))
                .thenReturn(true);
        when(clienteRepository.existsByCpf(venda1.getCliente().getCpf()))
                .thenReturn(true);
        when(clienteRepository.findByCpf(venda1.getCliente().getCpf()))
                .thenReturn(venda1.getCliente());
    }
    @Test
    @DisplayName("Teste de integracao cadastrar nova venda com sucesso")
    void verificarStatusComStatusInvalido() {
        ResponseEntity<ResponseWrapper<String>> response =
                vendaController.register(modelMapper.map(vendas.get(0), VendaDtoEntrada.class));

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
