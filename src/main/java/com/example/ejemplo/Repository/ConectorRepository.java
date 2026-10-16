package com.example.ejemplo.Repository;

import com.example.ejemplo.Entity.ConectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConectorRepository extends JpaRepository<ConectorEntity, Long> {
}
