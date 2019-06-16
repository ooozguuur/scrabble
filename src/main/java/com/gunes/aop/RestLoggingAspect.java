package com.gunes.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RestLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestLoggingAspect.class);

    @Pointcut("within(com.gunes.controller..*)")
    public void loggingPoincut() {
    }

    @AfterThrowing(pointcut = "loggingPoincut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                     joinPoint.getSignature().getName(), e.getCause(), e);
    }

    @Around("loggingPoincut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                     joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            LOGGER.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                         joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                         joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}