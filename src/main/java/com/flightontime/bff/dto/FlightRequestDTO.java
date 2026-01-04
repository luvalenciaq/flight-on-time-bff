package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record FlightRequestDTO(
        @NotBlank(message = "La aerolínea es obligatoria")
        @Size(min = 2, max = 2, message = "El código de aerolínea debe tener exactamente 2 caracteres (ej: AA, B6)")
        @Pattern(regexp = "^[A-Z0-9]{2}$", message = "El código de aerolínea debe contener solo letras mayúsculas o números")
        String aerolinea,

        @NotBlank(message = "El aeropuerto de origen es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 letras (ej: JFK)")
        @Pattern(regexp = "^[A-Z]{3}$", message = "El código IATA debe contener solo letras mayúsculas")
        String origen,

        @NotBlank(message = "El aeropuerto de destino es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 letras (ej: LAX)")
        @Pattern(regexp = "^[A-Z]{3}$", message = "El código IATA debe contener solo letras mayúsculas")
        String destino,

        @NotNull(message = "La fecha de partida es obligatoria")
        @JsonProperty("fecha_partida")
        LocalDateTime fechaPartida,

        @NotNull(message = "La distancia es obligatoria")
        @Positive(message = "La distancia debe ser un número positivo")
        @JsonProperty("distancia_km")
        Double distanciaKm
) {

        public FlightRequestDTO {
                // Validar que origen y destino no sean iguales
                if (origen != null && destino != null && origen.equals(destino)) {
                        throw new IllegalArgumentException(
                                "El aeropuerto de origen y destino no pueden ser iguales"
                        );
                }
        }
}
