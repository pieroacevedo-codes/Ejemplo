package com.example.ejemplo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionDTO {
    private Long id_station;
    private String name;
    private String district;
    private Double power_kw;
    private LocalDate installation_date;
    private String operator;
    private String status;
}
