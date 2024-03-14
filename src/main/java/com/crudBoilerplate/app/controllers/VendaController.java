package com.crudBoilerplate.app.controllers;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericController;
import com.crudBoilerplate.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.VendaDtoSaida;
import com.crudBoilerplate.app.entities.VendaEntity;
import com.crudBoilerplate.app.repositories.VendaRepository;
import com.crudBoilerplate.app.service.VendaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venda")
public class VendaController extends CrudGenericController<
        VendaEntity,
        VendaService,
        VendaRepository,
        VendaDtoEntrada,
        VendaDtoSaida> {
}
