package org.example.spring_core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserIdGenerator {
    @PostConstruct
    public void init(){
        System.out.println("Creating userId " + this);
    }
    public Long generateId() {
        return System.currentTimeMillis();
    }
}
