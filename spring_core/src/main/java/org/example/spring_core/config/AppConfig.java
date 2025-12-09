package org.example.spring_core.config;

import org.example.spring_core.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    //SpeL
    @Value("${app.region}")
    public String region;

    @Value("${app.time-zone}")
    public String timeZone;

    @Bean
    public String systemMessage() {
        return region + "-" + timeZone;
    }

    @Bean(initMethod = "startUp", destroyMethod = "destroyEnd")
    public ConfigService myConfigService() {
        return new ConfigService();
    }


}

