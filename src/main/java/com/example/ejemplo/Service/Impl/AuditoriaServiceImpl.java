package com.example.ejemplo.Service.Impl;

import com.example.ejemplo.Dto.AuditoriaDTO;
import com.example.ejemplo.Entity.AuditoriaEntity;
import com.example.ejemplo.Entity.UsuarioEntity;
import com.example.ejemplo.Repository.AuditoriaRepository;
import com.example.ejemplo.Repository.UsuarioRepository;
import com.example.ejemplo.Service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra en tm_auditoria una fila por cada registro de las tablas del sistema:
 * quién lo creó (id_creacion) y cuándo, y luego quién lo editó o eliminó y cuándo.
 * La base permite una sola fila por (tabla_afectada, id_registro).
 */
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    public static final String TABLA_ESTACION = "tm_estacion";
    public static final String TABLA_CONECTOR = "tm_conector";
    public static final String TABLA_SESION_CARGA = "tm_sesion_carga";

    private static final int ESTADO_ACTIVO = 1;

    @Autowired
    private AuditoriaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void registrar_alta(String tabla, Long id_registro, Long id_usuario) {
        if (repository.buscar_registro(tabla, id_registro).isPresent()) {
            return;
        }
        crear_fila(tabla, id_registro, id_usuario);
    }

    @Override
    @Transactional
    public void registrar_edicion(String tabla, Long id_registro, Long id_usuario) {
        AuditoriaEntity auditoria = repository.buscar_registro(tabla, id_registro)
                .orElseGet(() -> crear_fila(tabla, id_registro, id_usuario));

        auditoria.setEdicion(usuario(id_usuario));
        auditoria.setFecha_edicion(LocalDateTime.now());
        repository.save(auditoria);
    }

    @Override
    @Transactional
    public void registrar_eliminacion(String tabla, Long id_registro, Long id_usuario) {
        AuditoriaEntity auditoria = repository.buscar_registro(tabla, id_registro)
                .orElseGet(() -> crear_fila(tabla, id_registro, id_usuario));

        auditoria.setEliminacion(usuario(id_usuario));
        auditoria.setFecha_eliminacion(LocalDateTime.now());
        repository.save(auditoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> listar() {
        List<AuditoriaDTO> listarDto = new ArrayList<>();

        for (AuditoriaEntity auditoriaEntity : repository.listar_todo()) {

            AuditoriaDTO auditoriaDto = new AuditoriaDTO();

            auditoriaDto.setId_auditoria(auditoriaEntity.getId_auditoria());
            auditoriaDto.setId_registro(auditoriaEntity.getId_registro());
            auditoriaDto.setTabla_afectada(auditoriaEntity.getTabla_afectada());
            auditoriaDto.setFecha_creacion(auditoriaEntity.getFecha_creacion());
            auditoriaDto.setFecha_edicion(auditoriaEntity.getFecha_edicion());
            auditoriaDto.setFecha_eliminacion(auditoriaEntity.getFecha_eliminacion());
            auditoriaDto.setEstado_auditoria(auditoriaEntity.getEstado_auditoria());
            if (auditoriaEntity.getCreacion() != null) {
                auditoriaDto.setId_creacion(auditoriaEntity.getCreacion().getId_usuario());
            }
            if (auditoriaEntity.getEdicion() != null) {
                auditoriaDto.setId_edicion(auditoriaEntity.getEdicion().getId_usuario());
            }
            if (auditoriaEntity.getEliminacion() != null) {
                auditoriaDto.setId_eliminacion(auditoriaEntity.getEliminacion().getId_usuario());
            }

            listarDto.add(auditoriaDto);
        }

        return listarDto;
    }

    // Sin token (ej. POST /zonas) no hay usuario: la acción queda registrada sin autor
    private UsuarioEntity usuario(Long id_usuario) {
        return id_usuario == null ? null : usuarioRepository.getReferenceById(id_usuario);
    }

    private AuditoriaEntity crear_fila(String tabla, Long id_registro, Long id_usuario) {
        AuditoriaEntity auditoria = new AuditoriaEntity();
        auditoria.setCreacion(usuario(id_usuario));
        auditoria.setId_registro(id_registro);
        auditoria.setTabla_afectada(tabla);
        auditoria.setFecha_creacion(LocalDateTime.now());
        auditoria.setEstado_auditoria(ESTADO_ACTIVO);
        return repository.save(auditoria);
    }
}
