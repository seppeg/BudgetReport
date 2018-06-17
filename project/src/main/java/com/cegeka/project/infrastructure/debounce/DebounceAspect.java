package com.cegeka.project.infrastructure.debounce;

import brave.Span;
import brave.Tracer;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.sleuth.util.SpanNameUtil;

@Aspect
@Log4j2
public class DebounceAspect {

    private static final String CLASS_KEY = "class";
    private static final String METHOD_KEY = "method";

    private final Debouncer debouncer;
    private final Tracer tracer;

    public DebounceAspect(Debouncer debouncer, Tracer tracer) {
        this.debouncer = debouncer;
        this.tracer = tracer;
    }

    @Around(value = "@annotation(com.cegeka.project.infrastructure.debounce.Debounce)")
    public void around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String debounceKey = signature.getDeclaringTypeName() + "." + signature.getName();
        Debounce annotation = signature.getMethod().getAnnotation(Debounce.class);
        debouncer.debounce(debounceKey, () -> {
            try {
                traceDebounced(joinPoint);
            }catch (Throwable e){
                log.error(() -> "Exception thrown by debounced method: "+debounceKey, e);
            }
        }, annotation.delay(), annotation.interrupt());
    }

    private void traceDebounced(final ProceedingJoinPoint pjp) throws Throwable {
        String spanName = SpanNameUtil.toLowerHyphen(pjp.getSignature().getName());
        Span span = startOrContinueRenamedSpan(spanName);
        try(Tracer.SpanInScope ws = this.tracer.withSpanInScope(span)) {
            span.tag(CLASS_KEY, pjp.getTarget().getClass().getSimpleName());
            span.tag(METHOD_KEY, pjp.getSignature().getName());
            pjp.proceed();
        } finally {
            span.finish();
        }
    }

    private Span startOrContinueRenamedSpan(String spanName) {
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan != null) {
            return currentSpan.name(spanName);
        }
        return this.tracer.nextSpan().name(spanName);
    }

}
