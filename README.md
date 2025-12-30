# Flight On Time BFF

## Descripción

**Flight On Time BFF** (Backend for Frontend) es una API REST desarrollada con Spring Boot que actúa como intermediaria entre las interfaces de cliente (Frontend) y los servicios del núcleo (Core Services). Su principal función es gestionar las solicitudes de predicción de puntualidad de vuelos, validando los datos de entrada y orquestando la comunicación con el servicio de predicción.

Este proyecto sigue una arquitectura limpia y modular, facilitando el mantenimiento y la escalabilidad.

## Características

*   **API RESTful**: Exposición de endpoints claros y estandarizados.
*   **Predicción de Puntualidad**: Endpoint específico `/predict` para evaluar la probabilidad de que un vuelo llegue a tiempo.
*   **Validación de Datos**: Validación robusta de entradas utilizando Bean Validation (Jakarta Validation) para asegurar la integridad de los datos antes de procesarlos.
*   **Cliente HTTP Declarativo**: Comunicación eficiente con servicios externos.
*   **Arquitectura Modular**: Separación de responsabilidades en Controladores, Servicios y DTOs.

## Stack Tecnológico

*   **Java**: 17
*   **Framework**: Spring Boot 3.x
*   **Gestor de Dependencias**: Maven
*   **Otras Librerías**:
    *   Lombok (para reducir código repetitivo)
    *   Spring Boot Starter Validation

## Prerrequisitos

Asegúrate de tener instaladas las siguientes herramientas en tu entorno local:

*   [Java Development Kit (JDK) 17](https://adoptium.net/)
*   [Apache Maven](https://maven.apache.org/download.cgi)

## Instalación

1.  **Clonar el repositorio:**

    ```bash
    git clone 
    cd flight-on-time-bff
    ```

2.  **Compilar el proyecto:**

    ```bash
    mvn clean install
    ```

## Configuración

El archivo de configuración principal se encuentra en `src/main/resources/application.properties`.

| Propiedad | Valor por Defecto | Descripción |
| :--- | :--- | :--- |
| `server.port` | `8080` | Puerto donde se ejecuta el servidor BFF. |
| `spring.application.name` | `bff-service` | Nombre de la aplicación. |
| `core.service.url` | `http://localhost:8081` | URL base del servicio Core de predicción. |

Puedes modificar estos valores según tu entorno.

## Ejecución

Para iniciar la aplicación, ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

## Uso del API

### Predicción de Vuelo

Evalúa la probabilidad de puntualidad de un vuelo.

*   **Endpoint:** `POST /predict`
*   **Content-Type:** `application/json`

#### Estructura del Request (JSON)

| Campo | Tipo | Requerido | Descripción |
| :--- | :--- | :--- | :--- |
| `aerolinea` | String | Sí | Nombre de la aerolínea. |
| `origen` | String | Sí | Código o nombre del aeropuerto de origen. |
| `destino` | String | Sí | Código o nombre del aeropuerto de destino. |
| `fecha_partida` | String | Sí | Fecha y hora de partida en formato ISO-8601 (`YYYY-MM-DDTHH:mm:ss`). |
| `distancia_km` | Number | Sí | Distancia del vuelo en kilómetros. |

#### Ejemplo de Solicitud (cURL)

```bash
curl -X POST http://localhost:8080/predict \
     -H "Content-Type: application/json" \
     -d '{
           "aerolinea": "AA",
           "origen": "JFK",
           "destino": "LAX",
           "fecha_partida": "2023-12-25T08:00:00",
           "distancia_km": 3980.5
         }'
```

#### Estructura de Respuesta (JSON)

| Campo | Tipo | Descripción |
| :--- | :--- | :--- |
| `prevision` | String | Resultado de la predicción (ej. "A tiempo", "Retrasado"). |
| `probabilidad` | Number | Valor numérico entre 0 y 1 indicando la confianza de la predicción. |

#### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "prevision": "A tiempo",
  "probabilidad": 0.85
}
```

#### Ejemplo de Respuesta de Error (400 Bad Request)

Si falta algún campo obligatorio o el formato es incorrecto:

```json
{
  "timestamp": "2023-12-30T10:15:30.123+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/predict"
}
```

## Estructura del Proyecto

```
src/main/java/com/flightontime/bff
├── BffApplication.java          # Clase principal de entrada
├── config/                      # Clases de configuración (Beans, Properties)
├── controller/                  # Controladores REST (Endpoints)
├── dto/                         # Data Transfer Objects (Request/Response)
└── service/                     # Lógica de negocio y clientes externos
```
