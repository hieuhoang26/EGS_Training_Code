package org.example.spring_core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private  ApplicationContext ctx;

    public void loadResources() {
        try {
            Resource cp = ctx.getResource("classpath:data/user.txt");
            Resource file = ctx.getResource("file:/tmp/info.txt");
            Resource url = ctx.getResource("https://example.com");

            System.out.println("CP exists: " + cp.exists());
            System.out.println("File exists: " + file.exists());
            System.out.println("URL readable: " + url.isReadable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
