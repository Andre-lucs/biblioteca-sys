package com.andrelucs.bibliotecasys.repositories;

import com.andrelucs.bibliotecasys.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailAndSenha(String email, String senha);
    @Transactional
    @Modifying
    @Query("UPDATE Admin a SET a.nivel = :nivel WHERE a.email = :email AND a.senha = :senha")
    int updateAdminLevel(@Param("email") String email, @Param("senha") String senha, @Param("nivel") int nivel);

}
