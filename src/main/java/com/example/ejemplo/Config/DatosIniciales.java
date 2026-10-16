package com.example.ejemplo.Config;

import com.example.ejemplo.Entity.ConectorEntity;
import com.example.ejemplo.Entity.EstacionEntity;
import com.example.ejemplo.Entity.RolEntity;
import com.example.ejemplo.Entity.SesionCargaEntity;
import com.example.ejemplo.Entity.UsuarioEntity;
import com.example.ejemplo.Repository.ConectorRepository;
import com.example.ejemplo.Repository.EstacionRepository;
import com.example.ejemplo.Repository.RolRepository;
import com.example.ejemplo.Repository.SesionCargaRepository;
import com.example.ejemplo.Repository.UsuarioRepository;
import com.example.ejemplo.Service.AuditoriaService;
import com.example.ejemplo.Service.Impl.AuditoriaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Si el script no cargó el rol, el usuario y los datos de prueba, se crean al arrancar.
 * El password del usuario operator es el código de alumno (guardado encriptado).
 */
@Slf4j
@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private ConectorRepository conectorRepository;

    @Autowired
    private SesionCargaRepository sesionCargaRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.codigo-alumno}")
    private String codigo_alumno;

    @Override
    public void run(String... args) {
        crear_usuario("operator", "OPERATOR");
        if (estacionRepository.count() == 0) {
            crear_datos_de_prueba();
        }
    }

    private void crear_usuario(String username, String nombre_rol) {
        RolEntity rol = rolRepository.buscar_por_nombre(nombre_rol).orElseGet(() -> {
            RolEntity nuevo = new RolEntity();
            nuevo.setNombre_rol(nombre_rol);
            return rolRepository.save(nuevo);
        });
        if (usuarioRepository.buscar_por_username(username).isEmpty()) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setUsername_usuario(username);
            usuario.setClave_usuario(passwordEncoder.encode(codigo_alumno));
            usuario.setRol(rol);
            usuarioRepository.save(usuario);
            log.info("Usuario creado: {} ({})", username, nombre_rol);
        }
    }

    private void crear_datos_de_prueba() {
        EstacionEntity miraflores = estacion("Estación Miraflores Centro", "Miraflores", 150.0, LocalDate.of(2025, 3, 10), "EnerPeru", "OPERATIVA");
        EstacionEntity sanIsidro = estacion("Estación San Isidro Financiero", "San Isidro", 120.0, LocalDate.of(2025, 6, 22), "EnerPeru", "OPERATIVA");
        EstacionEntity surco = estacion("Estación Surco Mall", "Santiago de Surco", 60.0, LocalDate.of(2025, 9, 5), "VoltCity", "OPERATIVA");
        EstacionEntity callao = estacion("Estación Callao Puerto", "Callao", 90.0, LocalDate.of(2026, 1, 18), "VoltCity", "MANTENIMIENTO");
        EstacionEntity lima = estacion("Estación Cercado de Lima", "Lima", 50.0, LocalDate.of(2026, 4, 2), "GreenCharge", "OPERATIVA");

        conector("CCS2", "DC", 920, "ABB", 4, miraflores);
        conector("CHAdeMO", "DC", 500, "Delta", 2, sanIsidro);
        conector("Tipo 2", "AC", 400, "Schneider", 6, surco);
        conector("CCS2", "DC", 920, "Siemens", 3, callao);
        conector("Tipo 2", "AC", 400, "Schneider", 4, lima);

        sesion(LocalDate.of(2026, 10, 10), 42.5, 35, 38.25, "ABC-123", miraflores);
        sesion(LocalDate.of(2026, 10, 11), 55.0, 48, 49.50, "DEF-456", miraflores);
        sesion(LocalDate.of(2026, 10, 11), 30.2, 28, 27.18, "GHI-789", sanIsidro);
        sesion(LocalDate.of(2026, 10, 12), 18.0, 60, 14.40, "JKL-012", surco);
        sesion(LocalDate.of(2026, 10, 12), 24.8, 75, 19.84, "MNO-345", surco);
        sesion(LocalDate.of(2026, 10, 13), 65.7, 52, 59.13, "PQR-678", sanIsidro);
        sesion(LocalDate.of(2026, 10, 14), 20.0, 40, 16.00, "STU-901", lima);
        sesion(LocalDate.of(2026, 10, 14), 38.4, 33, 34.56, "VWX-234", miraflores);
        log.info("Datos de prueba de estaciones de carga creados");
    }

    private EstacionEntity estacion(String name, String district, Double power_kw, LocalDate fecha, String operator, String status) {
        EstacionEntity estacion = new EstacionEntity();
        estacion.setName(name);
        estacion.setDistrict(district);
        estacion.setPower_kw(power_kw);
        estacion.setInstallation_date(fecha);
        estacion.setOperator(operator);
        estacion.setStatus(status);
        estacion = estacionRepository.save(estacion);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_ESTACION, estacion.getId_station(), id_operator());
        return estacion;
    }

    private Long id_operator() {
        return usuarioRepository.buscar_por_username("operator").orElseThrow().getId_usuario();
    }

    private void conector(String tipo, String corriente, Integer voltaje, String fabricante, Integer cantidad, EstacionEntity estacion) {
        ConectorEntity conector = new ConectorEntity();
        conector.setConnector_type(tipo);
        conector.setCurrent_type(corriente);
        conector.setMax_voltage(voltaje);
        conector.setManufacturer(fabricante);
        conector.setQuantity(cantidad);
        conector.setEstacion(estacion);
        conector = conectorRepository.save(conector);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_CONECTOR, conector.getId_connector(), id_operator());
    }

    private void sesion(LocalDate fecha, Double kwh, Integer minutos, Double costo, String placa, EstacionEntity estacion) {
        SesionCargaEntity sesion = new SesionCargaEntity();
        sesion.setSession_date(fecha);
        sesion.setEnergy_delivered_kwh(kwh);
        sesion.setDuration_minutes(minutos);
        sesion.setCost(costo);
        sesion.setVehicle_plate(placa);
        sesion.setEstacion(estacion);
        sesion = sesionCargaRepository.save(sesion);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_SESION_CARGA, sesion.getId_session(), id_operator());
    }
}
