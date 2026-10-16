package com.example.ejemplo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tm_sesion_carga")
public class SesionCargaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session")
    private Long id_session;

    @Column(name = "session_date")
    private LocalDate session_date;

    @Column(name = "energy_delivered_kwh")
    private Double energy_delivered_kwh;

    @Column(name = "duration_minutes")
    private Integer duration_minutes;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "vehicle_plate")
    private String vehicle_plate;

    @ManyToOne
    @JoinColumn(name = "id_station", referencedColumnName = "id_station")
    private EstacionEntity estacion;
}
