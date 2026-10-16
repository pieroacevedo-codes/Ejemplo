package com.example.ejemplo.Repository;

import com.example.ejemplo.Entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    @Query("SELECT r FROM RolEntity r WHERE r.nombre_rol = :nombre")
    Optional<RolEntity> buscar_por_nombre(@Param("nombre") String nombre);
}
