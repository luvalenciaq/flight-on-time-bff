package com.flightontime.bff.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO con la respuesta de la predicción")
public record PredictionResponseDTO(
                @Schema(description = "Previsión del vuelo (ej. 'Puntual', 'Retrasado')", example = "Puntual") String prevision,
                @Schema(description = "Probabilidad de la predicción", example = "0.24") double probabilidad) {
}
