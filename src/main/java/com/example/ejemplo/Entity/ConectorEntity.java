package com.example.ejemplo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_conector")
public class ConectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_connector")
    private Long id_connector;

    @Column(name = "connector_type")
    private String connector_type;

    @Column(name = "current_type")
    private String current_type;

    @Column(name = "max_voltage")
    private Integer max_voltage;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_station", referencedColumnName = "id_station")
    private EstacionEntity estacion;
}
