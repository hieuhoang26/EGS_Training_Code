package org.example.spring_core.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class LifeCycle implements InitializingBean, DisposableBean, SmartLifecycle, BeanNameAware, ApplicationContextAware {

    private boolean isRunning = false;
    private String beanName;
    private ApplicationContext applicationContext;

    // --- 1. CONSTRUCTOR ---
    public LifeCycle() {
        System.out.println("[Constructor] Bean created");
    }


    // --- 2. AWARE INTERFACES ---
    // Được gọi ngay sau khi Constructor và trước bất kỳ init nào.
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("[Aware] BeanNameAware:  Bean: " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("[Aware] ApplicationContextAware: AppContext: " + applicationContext.hashCode());
    }

    // --- 3. INITIALIZATION CALLBACKS ---

    @PostConstruct
    public void postConstructMethod() {
        System.out.println("[PostConstruct] @PostConstruct......");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[InitializingBean] afterPropertiesSet() .....");
    }

    // init-method

    // --- 4. START/STOP LIFECYCLE (SmartLifecycle) ---

    // Phase 0: Khởi động sớm
    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        return true; // Tự động khởi động khi Context được refresh
    }

    @Override
    public void start() {
        this.isRunning = true;
        System.out.println("[Lifecycle] SmartLifecycle start background process....");
    }

    @Override
    public void stop() {
        this.isRunning = false;
        System.out.println("[Lifecycle] SmartLifecycle stop background process.....");
    }

    @Override
    public void stop(Runnable callback) {
        // Dừng bất đồng bộ, gọi callback khi hoàn thành
        stop();
        callback.run();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    // --- 5. DESTRUCTION CALLBACKS ---

    @PreDestroy
    public void preDestroyMethod() {
        System.out.println("[PreDestroy] @PreDestroy......");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[DisposableBean] destroy()......");
    }


    // destroy-method
}

