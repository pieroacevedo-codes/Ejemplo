# Ejemplo - API de estaciones de carga

API REST en Spring Boot 3.3 con PostgreSQL y autenticación JWT.

## Requisitos
- JDK 21 o superior
- PostgreSQL con la base `BDParcialEjemplo` creada (`CREATE DATABASE "BDParcialEjemplo";`)
- Opcional: ejecutar `DB/script.sql` en esa base. Si no, la app crea las tablas y los datos de prueba al arrancar.

## Variables de entorno
Todas son opcionales: si no las defines se usa el valor por defecto de desarrollo.

| Variable | Descripción | Por defecto |
|---|---|---|
| `DB_URL` | URL JDBC | `jdbc:postgresql://localhost:5432/BDParcialEjemplo` |
| `DB_USER` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | `postgres` |
| `JWT_SECRET` | Clave para firmar los tokens (mínimo 32 caracteres) | clave de desarrollo |
| `JWT_EXPIRATION_MS` | Duración del token en milisegundos | `3600000` |
| `APP_CODIGO_ALUMNO` | Contraseña del usuario `operator` | `00000000` |
| `SERVER_PORT` | Puerto de la API | `8080` |

## Ejecutar con variables de entorno (PowerShell)
```powershell
$env:DB_PASSWORD="mi_contraseña"
$env:JWT_SECRET="una-clave-larga-de-al-menos-32-caracteres"
.\mvnw spring-boot:run
```

## Ejecutar desde IntelliJ
1. Run → Edit Configurations → selecciona `EjemploApplication`.
2. En **Environment variables** escribe, separadas por punto y coma: `DB_PASSWORD=mi_contraseña;JWT_SECRET=una-clave-larga-de-al-menos-32-caracteres`

## Endpoints
| Método | Ruta | Acceso |
|---|---|---|
| POST | `/authenticate` | público (usuario `operator`) |
| GET | `/estaciones/{id}` | público, sin token (HUB01) |
| GET | `/energy` | rol OPERATOR (HUB02, consulta nativa) |
| GET | `/auditoria/listar` | rol OPERATOR |

Swagger: `http://localhost:8080/swagger-ui/index.html`
