package com.example.ejemplo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tm_estacion")
public class EstacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_station")
    private Long id_station;

    @Column(name = "name")
    private String name;

    @Column(name = "district")
    private String district;

    @Column(name = "power_kw")
    private Double power_kw;

    @Column(name = "installation_date")
    private LocalDate installation_date;

    @Column(name = "operator")
    private String operator;

    @Column(name = "status")
    private String status;
}
