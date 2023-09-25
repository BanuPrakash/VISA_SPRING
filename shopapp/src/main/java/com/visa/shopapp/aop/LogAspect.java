package com.visa.shopapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.visa.shopapp.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        log.info(jp.getSignature() + " called");
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            log.info("Argument : " + arg);
        }
    }

    @After("execution(* com.visa.shopapp.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        log.info("*********");
    }

    @Around("execution(* com.visa.shopapp.service.*.*(..))")
    public Object profile(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
            Object ret = jp.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Total time " + (endTime - startTime) + " ms");
        return ret;
    }

    @AfterThrowing(value= "execution(* com.visa.shopapp.api.*.*(..))", throwing = "ex")
    public void logException(JoinPoint jp, Exception ex) {
        log.info(ex.getMessage());
    }
}