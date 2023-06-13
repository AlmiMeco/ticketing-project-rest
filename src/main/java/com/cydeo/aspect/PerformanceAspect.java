package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")
    public void executionTime(){}

    @Around("executionTime()")
    public Object aroundExecutionTime(ProceedingJoinPoint proceedingJoinPoint){

        long beforeExecution = System.currentTimeMillis();

        Object result = null;

        log.info("Execution Begins: ");

        try {
            result = proceedingJoinPoint.proceed();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long afterExecution = System.currentTimeMillis();

        log.info("Execution Time In Milliseconds ---> {}ms :: using -> {} Method"
        ,(afterExecution - beforeExecution), proceedingJoinPoint.getSignature().toShortString());

        return result;
    }


}
