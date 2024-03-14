package com.crudBoilerplate.app.dtos.dtoSaida;

import com.crudBoilerplate.app.entities.ClienteEntity;
import com.crudBoilerplate.app.entities.FuncionarioEntity;
import com.crudBoilerplate.app.entities.ProdutoEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaDtoSaida {
    private UUID id;
    private String enderecoDaEntrega;
    private BigDecimal valorTotal;
    private ClienteEntity cliente;
    private FuncionarioEntity funcionario;
    private List<ProdutoEntity> produtos;
}
