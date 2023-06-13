package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j // <- Imports 'Logger' methods
public class LoggingAspect {

//    Logger logger = LoggerFactory.getLogger(LoggingAspect.class); <- @Slf4j

    /* Retrieving current user's userName from KeyCloak */
    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();

        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();

    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void ProjectAndTaskPointCut(){}

    @Before("ProjectAndTaskPointCut()")
    public void beforeProjectAndTaskAdvice(JoinPoint joinPoint){
        log.info("Before -> Method: {}, User: {} "
        ,joinPoint.getSignature().toShortString()
        ,getUserName());
    }

    @AfterReturning(pointcut = "ProjectAndTaskPointCut()", returning = "result")
    public void afterProjectAndTaskAdvice(JoinPoint joinPoint, Object result){
        log.info("After -> Method: {}, User: {}, Result: {} "
                ,joinPoint.getSignature().toShortString()
                ,getUserName()
                ,result.toString());
    }


}
