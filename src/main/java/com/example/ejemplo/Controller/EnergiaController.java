package com.example.ejemplo.Controller;

import com.example.ejemplo.Dto.EnergiaEstacionDTO;
import com.example.ejemplo.Service.SesionCargaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/energy")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Controlador de energía", description = "HUB02: total de energía entregada por estación (solo OPERATOR)")
public class EnergiaController {

    @Autowired
    private SesionCargaService service;

    @GetMapping
    @Operation(summary = "Total de energía entregada (kWh) por estación")
    public List<EnergiaEstacionDTO> energia_por_estacion() {
        return service.energia_por_estacion();
    }
}
