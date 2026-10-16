-- =====================================================
-- PRACTICA - Script PostgreSQL (pgAdmin)
-- 1) Crear la base (ejecutar solo esta linea, conectado a "postgres"):
--      CREATE DATABASE "BDParcialEjemplo";
-- 2) Conectarse a BDParcialEjemplo y ejecutar el resto.
-- Reemplaza 00000000 por tu codigo de alumno (sin la "U").
-- =====================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS tm_auditoria;
DROP TABLE IF EXISTS tm_sesion_carga;
DROP TABLE IF EXISTS tm_conector;
DROP TABLE IF EXISTS tm_estacion;
DROP TABLE IF EXISTS tm_usuario;
DROP TABLE IF EXISTS tm_rol;

CREATE TABLE tm_rol (
    id_rol     BIGSERIAL PRIMARY KEY,
    nombre_rol VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tm_usuario (
    id_usuario       BIGSERIAL PRIMARY KEY,
    id_rol           BIGINT NOT NULL,
    username_usuario VARCHAR(100) NOT NULL UNIQUE,
    clave_usuario    VARCHAR(100) NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES tm_rol (id_rol)
);

CREATE TABLE tm_estacion (
    id_station        BIGSERIAL PRIMARY KEY,
    name              VARCHAR(150) NOT NULL,
    district          VARCHAR(100) NOT NULL,
    power_kw          DOUBLE PRECISION NOT NULL,
    installation_date DATE NOT NULL,
    operator          VARCHAR(100) NOT NULL,
    status            VARCHAR(50) NOT NULL
);

CREATE TABLE tm_conector (
    id_connector   BIGSERIAL PRIMARY KEY,
    connector_type VARCHAR(50) NOT NULL,
    current_type   VARCHAR(10) NOT NULL,
    max_voltage    INTEGER NOT NULL,
    manufacturer   VARCHAR(100) NOT NULL,
    quantity       INTEGER NOT NULL,
    id_station     BIGINT NOT NULL,
    CONSTRAINT fk_conector_estacion FOREIGN KEY (id_station) REFERENCES tm_estacion (id_station)
);

CREATE TABLE tm_sesion_carga (
    id_session           BIGSERIAL PRIMARY KEY,
    session_date         DATE NOT NULL,
    energy_delivered_kwh DOUBLE PRECISION NOT NULL,
    duration_minutes     INTEGER NOT NULL,
    cost                 DOUBLE PRECISION NOT NULL,
    vehicle_plate        VARCHAR(20) NOT NULL,
    id_station           BIGINT NOT NULL,
    CONSTRAINT fk_sesion_estacion FOREIGN KEY (id_station) REFERENCES tm_estacion (id_station)
);

-- id_creacion es NULL cuando el registro se crea sin token
CREATE TABLE tm_auditoria (
    id_auditoria      BIGSERIAL PRIMARY KEY,
    id_creacion       BIGINT NULL,
    id_edicion        BIGINT NULL,
    id_eliminacion    BIGINT NULL,
    id_registro       BIGINT NOT NULL,
    fecha_creacion    TIMESTAMP NOT NULL,
    fecha_edicion     TIMESTAMP NULL,
    fecha_eliminacion TIMESTAMP NULL,
    tabla_afectada    VARCHAR(50) NOT NULL,
    estado_auditoria  INTEGER NOT NULL,
    CONSTRAINT fk_aud_creacion    FOREIGN KEY (id_creacion)    REFERENCES tm_usuario (id_usuario),
    CONSTRAINT fk_aud_edicion     FOREIGN KEY (id_edicion)     REFERENCES tm_usuario (id_usuario),
    CONSTRAINT fk_aud_eliminacion FOREIGN KEY (id_eliminacion) REFERENCES tm_usuario (id_usuario),
    CONSTRAINT uq_aud_registro UNIQUE (tabla_afectada, id_registro)
);

-- Rol y usuario: el password es el codigo de alumno, encriptado con BCrypt
INSERT INTO tm_rol (nombre_rol) VALUES ('OPERATOR');

INSERT INTO tm_usuario (id_rol, username_usuario, clave_usuario)
VALUES ((SELECT id_rol FROM tm_rol WHERE nombre_rol = 'OPERATOR'), 'operator', crypt('00000000', gen_salt('bf', 10)));

-- Datos de prueba
INSERT INTO tm_estacion (name, district, power_kw, installation_date, operator, status) VALUES
('Estación Miraflores Centro',     'Miraflores',        150.0, '2025-03-10', 'EnerPeru',    'OPERATIVA'),
('Estación San Isidro Financiero', 'San Isidro',        120.0, '2025-06-22', 'EnerPeru',    'OPERATIVA'),
('Estación Surco Mall',            'Santiago de Surco',  60.0, '2025-09-05', 'VoltCity',    'OPERATIVA'),
('Estación Callao Puerto',         'Callao',             90.0, '2026-01-18', 'VoltCity',    'MANTENIMIENTO'),
('Estación Cercado de Lima',       'Lima',               50.0, '2026-04-02', 'GreenCharge', 'OPERATIVA');

INSERT INTO tm_conector (connector_type, current_type, max_voltage, manufacturer, quantity, id_station) VALUES
('CCS2',    'DC', 920, 'ABB',       4, 1),
('CHAdeMO', 'DC', 500, 'Delta',     2, 2),
('Tipo 2',  'AC', 400, 'Schneider', 6, 3),
('CCS2',    'DC', 920, 'Siemens',   3, 4),
('Tipo 2',  'AC', 400, 'Schneider', 4, 5);

INSERT INTO tm_sesion_carga (session_date, energy_delivered_kwh, duration_minutes, cost, vehicle_plate, id_station) VALUES
('2026-10-10', 42.5, 35, 38.25, 'ABC-123', 1),
('2026-10-11', 55.0, 48, 49.50, 'DEF-456', 1),
('2026-10-11', 30.2, 28, 27.18, 'GHI-789', 2),
('2026-10-12', 18.0, 60, 14.40, 'JKL-012', 3),
('2026-10-12', 24.8, 75, 19.84, 'MNO-345', 3),
('2026-10-13', 65.7, 52, 59.13, 'PQR-678', 2),
('2026-10-14', 20.0, 40, 16.00, 'STU-901', 5),
('2026-10-14', 38.4, 33, 34.56, 'VWX-234', 1);

-- Auditoria de los datos de prueba: los creo el usuario operator
INSERT INTO tm_auditoria (id_creacion, id_registro, fecha_creacion, tabla_afectada, estado_auditoria)
SELECT (SELECT id_usuario FROM tm_usuario WHERE username_usuario = 'operator'), id_station, NOW(), 'tm_estacion', 1 FROM tm_estacion
UNION ALL
SELECT (SELECT id_usuario FROM tm_usuario WHERE username_usuario = 'operator'), id_connector, NOW(), 'tm_conector', 1 FROM tm_conector
UNION ALL
SELECT (SELECT id_usuario FROM tm_usuario WHERE username_usuario = 'operator'), id_session, NOW(), 'tm_sesion_carga', 1 FROM tm_sesion_carga;
