package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature

import java.lang.annotation.Annotation
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Slf4j
@Aspect
class LogExecutionTimeAspect {

    @Around("@annotation(grails.plugin.logtime.LogExecutionTime)")
    def logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // get joinPoint method annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Annotation annotation = signature.getMethod().getAnnotation(LogExecutionTime)
//        String methodName = signature.getMethod().getName()
//        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes()
//        Annotation[][] annotations = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations()

        def level = annotation.level()
        log.debug "log with level: ${level}"

        def format = annotation.format()
        log.debug "log with format: $format"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)

        //todo: add to logging with more info such thread, MDC, etc
        log(level, "BEFORE: ${joinPoint.getSignature()}, TIME: ${ZonedDateTime.now().format(formatter)}")

        long start = System.nanoTime()
        try {
            return joinPoint.proceed()
        } finally {     // no catch clause, so that no target method's exception is blocked
                        // but post-call time tracking must finished
            long executionTime = System.nanoTime() - start
            log(level, "AFTER: ${joinPoint.getSignature()}, EXECUTION_TIME: ${executionTime} ns")
        }
    }

    protected static void log(String level, String format) {
        LevelLogger.log(getLogger(), level, format)
    }

    protected static Logger getLogger() {
        //todo: refactor this
        log
    }

}
