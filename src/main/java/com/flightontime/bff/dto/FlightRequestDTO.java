package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record FlightRequestDTO(
        @NotBlank(message = "La aerolínea es obligatoria")
        String aerolinea,
        @NotBlank(message = "El origen es obligatorio")
        String origen,
        @NotBlank(message = "El destino es obligatorio")
        String destino,
        @JsonProperty("fecha_partida")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "La fecha de partida es obligatoria")
        LocalDateTime fechaPartida,
        @Positive(message = "La distancia debe ser mayor a 0")
        @JsonProperty("distancia_km")
        Double distanciaKm
) {
}
