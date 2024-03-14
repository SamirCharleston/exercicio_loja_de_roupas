package com.crudBoilerplate.app.dtos.dtoEntrada;

import com.crudBoilerplate.app.config.messageHandling.errorMessages.ValidationErrorMessages;
import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericDTOIn;
import com.crudBoilerplate.app.entities.ClienteEntity;
import com.crudBoilerplate.app.entities.FuncionarioEntity;
import com.crudBoilerplate.app.entities.ProdutoEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaDtoEntrada extends CrudGenericDTOIn {
    private String enderecoDaEntrega;
    @Positive(message = ValidationErrorMessages.POSITIVE)
    @NotNull(message = ValidationErrorMessages.NOT_NULL)
    private BigDecimal valorTotal;
    private ClienteEntity cliente;
    private FuncionarioEntity funcionario;
    private List<ProdutoEntity> produtos;
}
