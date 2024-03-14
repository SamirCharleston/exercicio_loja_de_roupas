package com.crudBoilerplate.app.controllers;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericController;
import com.crudBoilerplate.app.dtos.dtoEntrada.FuncionarioDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.FuncionarioDtoSaida;
import com.crudBoilerplate.app.entities.FuncionarioEntity;
import com.crudBoilerplate.app.repositories.FuncionarioRepository;
import com.crudBoilerplate.app.service.FuncionarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController extends CrudGenericController<
        FuncionarioEntity,
        FuncionarioService,
        FuncionarioRepository,
        FuncionarioDtoEntrada,
        FuncionarioDtoSaida> {
}
