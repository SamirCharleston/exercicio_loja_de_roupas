package com.crudBoilerplate.app.coreClasses.userAutentication;

import com.sismanut.sismanut.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
@Repository
public interface UserDefaultRepository extends JpaRepository<UsuarioEntity, UUID> {
    @Query(value = "SELECT u.password FROM UsuarioEntity u WHERE login = :login")
    String getPasswordByLogin(@RequestParam("login") String login);
    @Query(value = "SELECT u.nome FROM UsuarioEntity u WHERE login = :login")
    String getUserNameByLogin(@RequestParam("login") String login);
}
