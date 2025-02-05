package in.sp.main.aspect;


import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* in.sp.main.controller.*.*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature());
    }

    @After("execution(* in.sp.main.controller.*.*(..))")
    public void logAfterControllerMethods(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature());
    }


    @Before("execution(* in.sp.main.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Service Method Invoked: {}", joinPoint.getSignature());
    }

    @After("execution(* in.sp.main.service.*.*(..))")
    public void logAfterServiceMethods(JoinPoint joinPoint) {
        logger.info("Service Method Completed: {}", joinPoint.getSignature());
    }
}
