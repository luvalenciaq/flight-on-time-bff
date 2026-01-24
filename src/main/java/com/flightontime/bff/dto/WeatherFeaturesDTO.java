package com.flightontime.bff.dto;

public record WeatherFeaturesDTO(
        double tempRangeF,
        double dewpointRangeF,
        int hasPrecip,
        int hasSnow,
        int highWind
) {}

