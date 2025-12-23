package com.flightontime.bff.dto;

public record PredictionResponseDTO(
        String prevision,
        double probabilidad) {
}
