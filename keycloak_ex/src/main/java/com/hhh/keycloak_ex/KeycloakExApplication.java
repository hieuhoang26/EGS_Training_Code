package com.hhh.keycloak_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hhh.keycloak_ex.repository")
public class KeycloakExApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakExApplication.class, args);
    }

}
