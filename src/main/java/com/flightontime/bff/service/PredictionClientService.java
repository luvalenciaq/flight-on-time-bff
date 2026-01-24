package com.flightontime.bff.service;

import com.flightontime.bff.config.CoreServiceProperties;
import com.flightontime.bff.dto.FlightRequestDTO;
import com.flightontime.bff.dto.FlightResponseDTO;
import com.flightontime.bff.dto.PredictionResponseDTO;
import com.flightontime.bff.dto.PredictionWithFeaturesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    // Predicción CON clima
    public PredictionWithFeaturesDTO predictWithWeather(FlightRequestDTO dto) {
        // Construir URL con query parameter
        String url = props.getUrl() + "/internal/predict/detailed";
        ResponseEntity<String> rawResponse = restTemplate.postForEntity(url, dto, String.class);
        System.out.println(rawResponse.getBody());

        PredictionWithFeaturesDTO raw = restTemplate.postForObject(
                url,
                dto,
                PredictionWithFeaturesDTO.class
        );

        if (raw == null) {
            return null;
        }

        double rounded = BigDecimal
                .valueOf(raw.probabilidad())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new PredictionWithFeaturesDTO(
                raw.prevision(),
                rounded,
                raw.clima()
        );
    }
    public List<FlightResponseDTO> getAllFlights() {

        String url = props.getUrl() + "/internal/flights";

        ResponseEntity<FlightResponseDTO[]> response =
                restTemplate.getForEntity(url, FlightResponseDTO[].class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return List.of();
        }

        return List.of(response.getBody());
    }

}
