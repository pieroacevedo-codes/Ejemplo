package com.example.ejemplo.Repository;

import com.example.ejemplo.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT u FROM UsuarioEntity u WHERE u.username_usuario = :username")
    Optional<UsuarioEntity> buscar_por_username(@Param("username") String username);
}
