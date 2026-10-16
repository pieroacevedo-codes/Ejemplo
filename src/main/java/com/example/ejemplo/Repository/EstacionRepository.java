package com.example.ejemplo.Repository;

import com.example.ejemplo.Entity.EstacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository<EstacionEntity, Long> {
}
