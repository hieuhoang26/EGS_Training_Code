package com.hhh.recipe_mn.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


//    @Around("@annotation(LogExecution)")
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethod(){

    }
    @Around("controllerMethod() && execution(* com.hhh.recipe_mn.controller.AuthController.*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("-->  START {}.{}", className, methodName);

        try {
            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;
            log.info("--> END {}.{} | time={}ms", className, methodName, duration);

            return result;
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("--> ERROR {}.{} | time={}ms | message={}",
                    className, methodName, duration, ex.getMessage());

            throw ex;
        }
    }
    @Before("@annotation(LogExecution)")
    public void logBefore(JoinPoint joinPoint) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        log.info("--> BEFORE {}.{} | args={}", className, methodName, args);
    }

    @After("@annotation(LogExecution)")
    public void logAfter(JoinPoint joinPoint) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("<-- AFTER {}.{}", className, methodName);
    }
}
