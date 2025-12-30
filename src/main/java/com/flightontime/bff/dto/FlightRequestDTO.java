package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record FlightRequestDTO(
        String aerolinea,
        String origen,
        String destino,
        @JsonProperty("fecha_partida")
        LocalDateTime fechaPartida,
        @JsonProperty("distancia_km")
        double distanciaKm
) {
}
