package com.crudBoilerplate.app.controllers;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericController;
import com.crudBoilerplate.app.dtos.dtoEntrada.ClienteDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.ClienteDtoSaida;
import com.crudBoilerplate.app.entities.ClienteEntity;
import com.crudBoilerplate.app.repositories.ClienteRepository;
import com.crudBoilerplate.app.service.ClienteService;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController extends CrudGenericController<
        ClienteEntity,
        ClienteService,
        ClienteRepository,
        ClienteDtoEntrada,
        ClienteDtoSaida> {
}
