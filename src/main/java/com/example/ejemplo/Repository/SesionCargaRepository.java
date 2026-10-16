package com.example.ejemplo.Repository;

import com.example.ejemplo.Entity.SesionCargaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionCargaRepository extends JpaRepository<SesionCargaEntity, Long> {

    interface EnergiaPorEstacion {
        Long getEstacion();

        Double getTotal();
    }

    @Query(value = "SELECT id_station AS estacion, SUM(energy_delivered_kwh) AS total " +
            "FROM tm_sesion_carga " +
            "GROUP BY id_station ORDER BY id_station", nativeQuery = true)
    List<EnergiaPorEstacion> energia_por_estacion();
}
