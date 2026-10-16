package com.example.ejemplo.Service;

import com.example.ejemplo.Dto.AuditoriaDTO;

import java.util.List;

public interface AuditoriaService {

    // id_usuario es siempre el id de tm_usuario de quien realiza la acción
    void registrar_alta(String tabla, Long id_registro, Long id_usuario);

    void registrar_edicion(String tabla, Long id_registro, Long id_usuario);

    void registrar_eliminacion(String tabla, Long id_registro, Long id_usuario);

    List<AuditoriaDTO> listar();
}
