package com.flightontime.bff.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String codigo,
                            String mensaje,
                            Map<String, String> errores) {
}
