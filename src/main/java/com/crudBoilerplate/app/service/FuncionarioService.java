package com.crudBoilerplate.app.service;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericService;
import com.crudBoilerplate.app.dtos.dtoEntrada.FuncionarioDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.FuncionarioDtoSaida;
import com.crudBoilerplate.app.entities.FuncionarioEntity;
import com.crudBoilerplate.app.repositories.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class FuncionarioService extends CrudGenericService<
        FuncionarioEntity,
        FuncionarioRepository,
        FuncionarioDtoEntrada,
        FuncionarioDtoSaida> {
    @Override
    public String register(FuncionarioDtoEntrada objectIn) throws Exception {
        return genericRegister(objectIn, FuncionarioEntity.class);
    }

    @Override
    public List<FuncionarioDtoSaida> find(Long quantity, String order) throws Exception {
        return genericFind(quantity, order, FuncionarioDtoSaida.class);
    }

    @Override
    public FuncionarioDtoSaida findById(UUID id) throws Exception {
        return genericFindById(id, FuncionarioDtoSaida.class);
    }

    @Override
    public String update(FuncionarioDtoEntrada objectIn) throws Exception {
        return genericRegister(objectIn, FuncionarioEntity.class);
    }

    @Override
    public String delete(UUID uuid) throws Exception {
        return genericDelete(uuid);
    }

    @Override
    public UUID findByAttribute(FuncionarioDtoEntrada objectIn) {
        return null;
    }

    @Override
    public FuncionarioEntity handleRelatedEntities(FuncionarioEntity entityToPersist) {
        return entityToPersist;
    }

    @Override
    public FuncionarioEntity applyBusinessRules(FuncionarioEntity entityToPersist) {
        return entityToPersist;
    }
}
