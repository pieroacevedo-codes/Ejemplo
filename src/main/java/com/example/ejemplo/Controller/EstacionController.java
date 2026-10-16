package com.example.ejemplo.Controller;

import com.example.ejemplo.Dto.EstacionDTO;
import com.example.ejemplo.Service.EstacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estaciones")
@Tag(name = "Controlador de estaciones", description = "HUB01: listar una estación por id (sin token)")
public class EstacionController {

    @Autowired
    private EstacionService service;

    @GetMapping("/{id}")
    @Operation(summary = "Listar una estación por su id")
    public EstacionDTO buscar_por_id(@PathVariable("id") Long id) {
        return service.buscar_por_id(id);
    }
}
