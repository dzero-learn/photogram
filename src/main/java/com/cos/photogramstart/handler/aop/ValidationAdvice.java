package com.cos.photogramstart.handler.aop;

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
public class ValidationAdvice {
    @Around("execution(* com.cos.photogramstart.web.api.*ApiController.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                Map<String,String> errorMap = new HashMap<String,String>();

                if(bindingResult.hasErrors()) {
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed(); // ex.profile 함수가 실행 됨.
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint)  throws Throwable {
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // ex. profile 함수보다 먼저 실행
        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg : args) {
            // System.out.println(arg); 무한참조 에러발생->domain:User toString 직접 작성
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

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

        return proceedingJoinPoint.proceed();
    }
}
