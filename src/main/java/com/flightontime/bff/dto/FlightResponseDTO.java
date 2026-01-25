package com.flightontime.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record FlightResponseDTO(

        @JsonProperty("nombre_aerolinea")
        String nombreAerolinea,

        @JsonProperty("nombre_aeropuerto_origen")
        String nombreAeropuertoOrigen,

        @JsonProperty("nombre_aeropuerto_destino")
        String nombreAeropuertoDestino,

        @JsonProperty("fecha_partida")
        LocalDateTime fechaYHoraPartida,

        String prevision
) {}

