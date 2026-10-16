package com.example.ejemplo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @NotBlank(message = "El username es obligatorio")
    private String username_usuario;
    @NotBlank(message = "La contraseña es obligatoria")
    private String clave_usuario;
}
