package com.crudBoilerplate.app.service;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericService;
import com.crudBoilerplate.app.dtos.dtoEntrada.ClienteDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.ClienteDtoSaida;
import com.crudBoilerplate.app.entities.ClienteEntity;
import com.crudBoilerplate.app.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class ClienteService extends CrudGenericService<
        ClienteEntity,
        ClienteRepository,
        ClienteDtoEntrada,
        ClienteDtoSaida> {
    @Override
    public String register(ClienteDtoEntrada objectIn) throws Exception {
        return genericRegister(objectIn, ClienteEntity.class);
    }

    @Override
    public List<ClienteDtoSaida> find(Long quantity, String order) throws Exception {
        return genericFind(quantity, order, ClienteDtoSaida.class);
    }

    @Override
    public ClienteDtoSaida findById(UUID id) throws Exception {
        return genericFindById(id, ClienteDtoSaida.class);
    }

    @Override
    public String update(ClienteDtoEntrada objectIn) throws Exception {
        return genericRegister(objectIn, ClienteEntity.class);
    }

    @Override
    public String delete(UUID uuid) throws Exception {
        return genericDelete(uuid);
    }

    @Override
    public UUID findByAttribute(ClienteDtoEntrada objectIn) {
        return null;
    }

    @Override
    public ClienteEntity handleRelatedEntities(ClienteEntity entityToPersist) {
        return entityToPersist;
    }

    @Override
    public ClienteEntity applyBusinessRules(ClienteEntity entityToPersist) {
        return entityToPersist;
    }
}
