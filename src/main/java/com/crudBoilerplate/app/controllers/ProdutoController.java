package com.crudBoilerplate.app.controllers;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericController;
import com.crudBoilerplate.app.dtos.dtoEntrada.ProdutoDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.ProdutoDtoSaida;
import com.crudBoilerplate.app.entities.ProdutoEntity;
import com.crudBoilerplate.app.repositories.ProdutoRepository;
import com.crudBoilerplate.app.service.ProdutoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController extends CrudGenericController<
        ProdutoEntity,
        ProdutoService,
        ProdutoRepository,
        ProdutoDtoEntrada,
        ProdutoDtoSaida> {
}
