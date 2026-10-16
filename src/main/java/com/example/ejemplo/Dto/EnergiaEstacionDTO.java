package com.example.ejemplo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Los nombres de los campos son los que pide el JSON del enunciado: idStation y total_kwh
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergiaEstacionDTO {
    private Long idStation;
    private Double total_kwh;
}
