package com.flightontime.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de Bean Validation
     * (@NotBlank, @Size, @Pattern, @Positive, etc.)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        // Extraer todos los errores de campos
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Crear mensaje concatenado
        String fullMessage = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                fullMessage,
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja errores durante la deserialización JSON
     * Incluye validaciones del constructor del record (origen = destino)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        // Extraer la causa raíz (desempaqueta excepciones anidadas)
        Throwable cause = ex.getCause();
        if (cause != null) {
            cause = cause.getCause();
        }

        // Si la causa es IllegalArgumentException (validación del constructor)
        if (cause instanceof IllegalArgumentException) {
            ErrorResponse error = new ErrorResponse(
                    "VALIDATION_ERROR",
                    cause.getMessage(),
                    null
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }

        // Si es otro error de deserialización (JSON mal formado, etc.)
        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                "Error al procesar la solicitud: formato JSON inválido",
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja validaciones personalizadas (IllegalArgumentException directa)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja cuando el Core Service no está disponible
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(
            ResourceAccessException ex) {

        ErrorResponse error = new ErrorResponse(
                "SERVICE_UNAVAILABLE",
                "El servicio de predicción no está disponible. Por favor, intenta más tarde.",
                null
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    /**
     * Maneja errores HTTP del Core Service
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(
            HttpClientErrorException ex) {

        ErrorResponse error = new ErrorResponse(
                "CORE_SERVICE_ERROR",
                "Error del servicio de predicción: " + ex.getStatusText(),
                null
        );

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(error);
    }

    /**
     * Maneja cualquier otro error no capturado
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "Ocurrió un error inesperado. Por favor, contacta al soporte.",
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}