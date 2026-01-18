package com.flightontime.bff.exception;

public record CoreErrorResponse(int status,
                                String error,
                                String message) {
}
