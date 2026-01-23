package com.flightontime.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de predicción con datos climáticos")
public record PredictionWithFeaturesDTO(
        @Schema(description = "Previsión del vuelo", example = "Puntual")
        String prevision,

        @Schema(description = "Probabilidad", example = "0.85")
        double probabilidad,

        @Schema(description = "Datos climáticos")
        WeatherFeaturesDTO clima
) {}
