package com.example.ejemplo.Service.Impl;

import com.example.ejemplo.Dto.EnergiaEstacionDTO;
import com.example.ejemplo.Repository.SesionCargaRepository;
import com.example.ejemplo.Service.SesionCargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SesionCargaServiceImpl implements SesionCargaService {

    @Autowired
    private SesionCargaRepository sesionCargaRepository;

    @Override
    public List<EnergiaEstacionDTO> energia_por_estacion() {
        List<EnergiaEstacionDTO> listarDto = new ArrayList<>();
        for (SesionCargaRepository.EnergiaPorEstacion fila : sesionCargaRepository.energia_por_estacion()) {
            listarDto.add(new EnergiaEstacionDTO(fila.getEstacion(), fila.getTotal()));
        }
        return listarDto;
    }
}
