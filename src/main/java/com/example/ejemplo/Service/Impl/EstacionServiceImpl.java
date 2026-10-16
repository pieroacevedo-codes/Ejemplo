package com.example.ejemplo.Service.Impl;

import com.example.ejemplo.Dto.EstacionDTO;
import com.example.ejemplo.Entity.EstacionEntity;
import com.example.ejemplo.Repository.EstacionRepository;
import com.example.ejemplo.Service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstacionServiceImpl implements EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Override
    public EstacionDTO buscar_por_id(Long id_station) {
        EstacionEntity estacion = estacionRepository.findById(id_station)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La estación con id " + id_station + " no existe"));
        EstacionDTO estacionDTO = new EstacionDTO();
        estacionDTO.setId_station(estacion.getId_station());
        estacionDTO.setName(estacion.getName());
        estacionDTO.setDistrict(estacion.getDistrict());
        estacionDTO.setPower_kw(estacion.getPower_kw());
        estacionDTO.setInstallation_date(estacion.getInstallation_date());
        estacionDTO.setOperator(estacion.getOperator());
        estacionDTO.setStatus(estacion.getStatus());
        return estacionDTO;
    }
}
