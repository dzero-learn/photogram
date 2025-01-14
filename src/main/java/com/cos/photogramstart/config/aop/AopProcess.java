package com.cos.photogramstart.config.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class AopProcess {
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object webProcessing(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objects = joinPoint.getArgs();

        for(Object object : objects) {
            if(object instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) object;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<String, String>();

                    for(FieldError e : bindingResult.getFieldErrors()) {
                        errorMap.put(e.getField(), e.getDefaultMessage());
                    }

                    // 오류를 던진다
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return joinPoint.proceed();
    }

    @Around("execution(* com.cos.photogramstart.web.api.*ApiController.*(..))")
    public Object webApiProcessing(ProceedingJoinPoint joinPoint)  throws Throwable {
        Object[] objects = joinPoint.getArgs();

        for(Object object : objects) {
            if(object instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) object;

                Map<String,String> errorMap = new HashMap<String,String>();

                if(bindingResult.hasErrors()) {
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return joinPoint.proceed();
    }
}
