package com.flightontime.bff.controller;

import com.flightontime.bff.dto.FlightRequestDTO;
import com.flightontime.bff.dto.PredictionResponseDTO;
import com.flightontime.bff.service.PredictionClientService;
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

    @PostMapping
    public ResponseEntity<PredictionResponseDTO> predict(@Valid @RequestBody FlightRequestDTO dto) {
        return ResponseEntity.ok(predictionClientService.predict(dto));
    }
}
