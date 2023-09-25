package com.visa.shopapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {

    @Around("@annotation(ValidateInput)")
    public Object validateInput(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();
        ValidateInput validateInput =
                (ValidateInput) pjp.getSignature().getDeclaringType()
                        .getDeclaredMethod("getProductById",int.class)
                        .getAnnotation(ValidateInput.class);

        if(args.length > 0 && args[0] instanceof Integer data) {
            if(data < validateInput.min()) {
                throw new IllegalArgumentException("value should be greater than zero");
            }
        }
        return pjp.proceed();
    }
}
