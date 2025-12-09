package org.example.spring_core.service;

public class ConfigService {
    public void startUp() {
        System.out.println(" [Custom] init-method ...");
    }
    public void destroyEnd() {
        System.out.println("[Custom] destroy-method ....");
    }
}
