package com.flightontime.bff.controller;

import com.flightontime.bff.dto.FlightRequestDTO;
import com.flightontime.bff.dto.PredictionResponseDTO;
import com.flightontime.bff.dto.PredictionWithFeaturesDTO;
import com.flightontime.bff.service.PredictionClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/predict")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionClientService predictionClientService;

    @Operation(summary = "Predecir puntualidad del vuelo", description = "Devuelve una predicción sobre si el vuelo será puntual basada en los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Predicción exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PredictionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PredictionResponseDTO> predict(@Valid @RequestBody FlightRequestDTO dto) {
        return ResponseEntity.ok(predictionClientService.predict(dto));
    }

    @Operation(summary = "Predecir puntualidad con datos climáticos")
    @PostMapping("/detailed")
    public ResponseEntity<PredictionWithFeaturesDTO> predictDetailed(
            @Valid @RequestBody FlightRequestDTO dto
    ) {
        return ResponseEntity.ok(predictionClientService.predictWithWeather(dto));
    }
}
