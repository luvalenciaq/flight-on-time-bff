package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Schema(description = "DTO para la solicitud de predicción de vuelo")
public record FlightRequestDTO(
                @Schema(description = "Nombre de la aerolínea", example = "G3") @NotBlank(message = "La aerolinea es obligatoria") String aerolinea,

                @Schema(description = "Aeropuerto de origen", example = "MIA") @NotBlank(message = "El origen es obligatorio") String origen,

                @Schema(description = "Aeropuerto de destino", example = "JFK") @NotBlank(message = "El destino es obligatorio") String destino,

                @JsonProperty("fecha_partida") @NotNull(message = "La fecha de partida es obligatoria") @FutureOrPresent(message = "La fecha de partida debe ser en el presente o futuro") @Schema(description = "Fecha y hora de partida", example = "2026-01-29T21:15:00") LocalDateTime fechaPartida,

                @JsonProperty("distancia_km") @Positive(message = "La distancia debe ser mayor a 0") @Schema(description = "Distancia del vuelo en kilómetros", example = "1100") double distanciaKm) {
}
