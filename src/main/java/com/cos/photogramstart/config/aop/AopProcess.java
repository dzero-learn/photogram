package com.cos.photogramstart.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopProcess {
    @Around("* com.cos.photogramstart.web.*Controller.*(..)")
    public void webProcessing(ProceedingJoinPoint joinPoint) {

    }

    @Around
    public void webApiProcessing() {

    }
}
