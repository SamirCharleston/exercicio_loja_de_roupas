package com.crudBoilerplate.app.repositories;

import com.crudBoilerplate.app.entities.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, UUID> {
}
