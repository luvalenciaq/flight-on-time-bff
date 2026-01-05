package com.flightontime.bff.infra;

import com.flightontime.bff.dto.errores.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GestorErrores {

    // 1. Validaciones de @NotBlank, @NotNull, Positive.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors; // Devuelve {"campo": "mensaje"}
    }

    // 2. Errores de formato
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleJsonErrors(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        Throwable cause = ex.getMostSpecificCause();
        String detail = cause.getMessage();

        if (cause instanceof com.fasterxml.jackson.databind.exc.MismatchedInputException mie) {
            String fieldName = mie.getPath().get(0).getFieldName();
            String tipoEsperado = mie.getTargetType().getSimpleName();

            // Mensaje estricto
            error.put(fieldName, "Tipo de dato inválido. Este campo debe ser estrictamente un " + tipoEsperado);
            return error;
        }

        // 1. Error formato campo: distancia
        if (detail.contains("[\"")) {
            String fieldName = detail.substring(detail.lastIndexOf("[\"") + 2, detail.lastIndexOf("\"]"));
            error.put(fieldName, "El valor o formato es incorrecto");
            return error;
        }

        // 2. Error formato campo: Fecha
        if (cause instanceof java.time.format.DateTimeParseException) {
            error.put("fecha_partida", "Formato de fecha inválido (Use: yyyy-MM-ddTHH:mm:ss)");
            return error;
        }

        // 3. Error de Sintaxis en el Json
        error.put("error", "Error de sintaxis en el JSON enviado.");
        return error;
    }

    // error que viene del core, datos desconocidos
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleCoreError(org.springframework.web.client.HttpClientErrorException ex) {
        // Esto sacará: "Error datos: Destino desconocido: JPE"
        String mensajeDelCore = ex.getResponseBodyAsString();
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of("mensaje", mensajeDelCore));
    }

    // 4. error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericError(Exception ex) {
        System.err.println("Error no controlado: " + ex.getClass().getName() + " -> " + ex.getMessage());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDTO("Ocurrió un error inesperado en el servidor"));
    }
}