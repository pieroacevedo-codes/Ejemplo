package com.example.ejemplo.Controller;

import com.example.ejemplo.Dto.LoginRespuestaDTO;
import com.example.ejemplo.Dto.UsuarioDTO;
import com.example.ejemplo.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@Tag(name = "Controlador de autenticación", description = "HU01: inicia sesión y devuelve el token")
public class AuthController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Operation(summary = "Iniciar sesión con username y contraseña; devuelve el token JWT")
    public LoginRespuestaDTO login(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return service.login(usuarioDTO);
    }
}
