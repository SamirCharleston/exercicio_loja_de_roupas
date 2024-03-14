package com.crudBoilerplate.app.service;

import com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses.CrudGenericService;
import com.crudBoilerplate.app.dtos.dtoEntrada.VendaDtoEntrada;
import com.crudBoilerplate.app.dtos.dtoSaida.VendaDtoSaida;
import com.crudBoilerplate.app.entities.VendaEntity;
import com.crudBoilerplate.app.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class VendaService extends CrudGenericService<
        VendaEntity,
        VendaRepository,
        VendaDtoEntrada,
        VendaDtoSaida> {
    @Override
    public String register(VendaDtoEntrada objectIn) throws Exception {
        return genericRegister(objectIn, VendaEntity.class);
    }

    @Override
    public List<VendaDtoSaida> find(Long quantity, String order) throws Exception {
        return genericFind(quantity, order, VendaDtoSaida.class);
    }

    @Override
    public VendaDtoSaida findById(UUID id) throws Exception {
        return genericFindById(id, VendaDtoSaida.class);
    }

    @Override
    public String update(VendaDtoEntrada objectIn) throws Exception {
        return genericUpdate(objectIn, VendaEntity.class);
    }

    @Override
    public String delete(UUID uuid) throws Exception {
        return genericDelete(uuid);
    }

    @Override
    public UUID findByAttribute(VendaDtoEntrada objectIn) {
        return null;
    }

    @Override
    public VendaEntity handleRelatedEntities(VendaEntity entityToPersist) {
        return entityToPersist;
    }

    @Override
    public VendaEntity applyBusinessRules(VendaEntity entityToPersist) {
        return entityToPersist;
    }
}
