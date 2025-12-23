package com.flightontime.bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "core.service")
@Getter
@Setter
public class CoreServiceProperties {
    private String url;
}
