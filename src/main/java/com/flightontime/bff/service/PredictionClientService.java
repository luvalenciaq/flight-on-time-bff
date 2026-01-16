package com.flightontime.bff.service;

import com.flightontime.bff.config.CoreServiceProperties;
import com.flightontime.bff.dto.FlightRequestDTO;
import com.flightontime.bff.dto.PredictionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PredictionClientService {

    private final RestTemplate restTemplate; // Objeto para hacer la llamada HTTP al core-service
    private final CoreServiceProperties props; //Contiene la URL del core-service tomada del application.properties

    // Este metodo envía los datos del vuelo al core-service y devuelve la predicción
    public PredictionResponseDTO predict(FlightRequestDTO dto) {
        // Hace una petición POST al endpoint /internal/predict del core-service
        PredictionResponseDTO raw = restTemplate.postForObject(
                props.getUrl() + "/internal/predict",
                dto,
                PredictionResponseDTO.class
        );

        if (raw == null) {
            return null;
        }

        double rounded = BigDecimal
                .valueOf(raw.probabilidad())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        // Se construye explícitamente el DTO de salida para controlar
        // errores fuera del flujo de serialización de la respuesta
        PredictionResponseDTO response =
                new PredictionResponseDTO(raw.prevision(), rounded);

        return response;
    }
}
