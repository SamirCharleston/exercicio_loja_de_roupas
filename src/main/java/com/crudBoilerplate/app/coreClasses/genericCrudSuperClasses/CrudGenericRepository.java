package com.crudBoilerplate.app.coreClasses.genericCrudSuperClasses;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface CrudGenericRepository<T extends CrudGenericEntity> extends JpaRepository<T, UUID> {
}
