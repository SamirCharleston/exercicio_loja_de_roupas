package com.crudBoilerplate.app.repositories;

import com.crudBoilerplate.app.entities.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendaRepository extends JpaRepository<VendaEntity, UUID> {
}
