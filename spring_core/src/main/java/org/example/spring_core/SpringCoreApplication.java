package org.example.spring_core;

import org.example.spring_core.config.AppConfig;
import org.example.spring_core.config.UserIdGenerator;
import org.example.spring_core.service.LifeCycle;
import org.example.spring_core.service.ResourceService;
import org.example.spring_core.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringCoreApplication.class, args);
        ConfigurableApplicationContext context =
                SpringApplication.run(SpringCoreApplication.class, args);

        boolean beanExistsByName = context.containsBean("userService");
        System.out.println("Bean 'UserService' exists: " + beanExistsByName);

        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getUserInfo(3L));

        AppConfig appConfig = context.getBean(AppConfig.class);
        System.out.println(appConfig.systemMessage());

        // Create each bean is requested
        UserIdGenerator id1 = context.getBean(UserIdGenerator.class);
        UserIdGenerator id2 = context.getBean(UserIdGenerator.class);

        // Resources
        ResourceService resourceService = context.getBean(ResourceService.class);
        resourceService.loadResources();

        // LifeCycle;
        LifeCycle lifeCycle = context.getBean(LifeCycle.class);
        context.close();
    }

}
