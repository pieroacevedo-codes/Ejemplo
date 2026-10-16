package com.example.ejemplo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespuestaDTO {
    private String token;
    private String nombre_rol;
}
