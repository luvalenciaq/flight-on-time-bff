package com.flightontime.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CoreErrorTranslator {

    public ResponseEntity<ErrorResponse> translate(
            CoreErrorResponse coreError,
            HttpStatusCode status
    ) {

        if (status == HttpStatus.NOT_FOUND) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "VALIDATION_ERROR",
                            coreError.message(),
                            null
                    ));
        }

        if (status.is4xxClientError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "CORE_VALIDATION_ERROR",
                            coreError.message(),
                            null
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "CORE_SERVICE_ERROR",
                        "Error interno del servicio de predicción",
                        null
                ));
    }
}
