package com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses;

import com.crudBoilerplate.app.config.messageHandling.errorMessages.ValidationErrorMessages;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrudGenericDTOIn{
    private UUID id;
    @Positive(message = ValidationErrorMessages.POSITIVE)
    private Long quantityToList;
    private boolean descOrder;
}
