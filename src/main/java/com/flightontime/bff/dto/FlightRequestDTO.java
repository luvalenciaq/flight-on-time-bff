package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "DTO para la solicitud de predicción de vuelo")
public record FlightRequestDTO(
        @Schema(description = "Nombre de la aerolínea", example = "G3")
        @NotBlank(message = "La aerolínea es obligatoria")
        @Size(min = 2, max = 2, message = "El código de aerolínea debe tener exactamente 2 caracteres (ej: AA, B6)")
        @Pattern(regexp = "^[A-Z0-9]{2}$", message = "El código de aerolínea debe contener solo letras mayúsculas o números")
        String aerolinea,
        
        @Schema(description = "Aeropuerto de origen", example = "MIA")
        @NotBlank(message = "El aeropuerto de origen es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 letras (ej: JFK)")
        @Pattern(regexp = "^[A-Z]{3}$", message = "El código IATA debe contener solo letras mayúsculas")
        String origen,

        @Schema(description = "Aeropuerto de destino", example = "JFK")
        @NotBlank(message = "El aeropuerto de destino es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 letras (ej: LAX)")
        @Pattern(regexp = "^[A-Z]{3}$", message = "El código IATA debe contener solo letras mayúsculas")
        String destino,

        @FutureOrPresent(message = "La fecha de partida debe ser en el presente o futuro")
        @Schema(description = "Fecha y hora de partida", example = "2026-01-29T21:15:00")
        @NotNull(message = "La fecha de partida es obligatoria")
        @JsonProperty("fecha_partida")
        LocalDateTime fechaPartida

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
