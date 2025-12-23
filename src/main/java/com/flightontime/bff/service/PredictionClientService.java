package com.flightontime.bff.service;

import com.flightontime.bff.config.CoreServiceProperties;
import com.flightontime.bff.dto.FlightRequestDTO;
import com.flightontime.bff.dto.PredictionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PredictionClientService {

    private final RestTemplate restTemplate; // Objeto para hacer la llamada HTTP al core-service
    private final CoreServiceProperties props; //Contiene la URL del core-service tomada del application.properties

    // Este metodo envía los datos del vuelo al core-service y devuelve la predicción
    public PredictionResponseDTO predict(FlightRequestDTO dto) {
        // Hace una petición POST al endpoint /internal/predict del core-service
        return restTemplate.postForObject(
                props.getUrl() + "/internal/predict",
                dto,
                PredictionResponseDTO.class
        );
    }
}
