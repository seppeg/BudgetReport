package com.cegeka.project.infrastructure.debounce;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@AllArgsConstructor
@Aspect
@Log4j2
public class DebounceAspect {

    private final Debouncer debouncer;

    @Around(value = "@annotation(com.cegeka.project.infrastructure.debounce.Debounce)")
    public void around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String debounceKey = signature.getDeclaringTypeName() + "." + signature.getName();
        long delay = signature.getMethod().getAnnotation(Debounce.class).delay();
        debouncer.debounce(debounceKey, () -> {
            try {
                joinPoint.proceed();
            }catch (Throwable e){
                log.error(() -> "Exception thrown by debounced method: "+debounceKey, e);
            }
        }, delay);
    }

}
