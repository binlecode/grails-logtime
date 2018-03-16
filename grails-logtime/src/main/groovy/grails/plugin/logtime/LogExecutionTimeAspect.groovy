package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature

import java.lang.annotation.Annotation

@CompileStatic
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

        //todo: add to log with more info such thread, MDC, etc
        log(level, buildMessage('BEFORE', joinPoint, annotation.beforeMessage() ?: annotation.message()))

        long start = System.nanoTime()
        try {
            return joinPoint.proceed()
        } finally {     // no catch clause, so that no target method's exception is blocked
                        // but post-call time tracking must finished
            def afterMessage = annotation.afterMessage() ?: annotation.message()
            long executionTime = System.nanoTime() - start
            if (annotation.timeUnit().equalsIgnoreCase('ms')) {
                executionTime = (Long)(executionTime / 1000000L)
                log(level, buildAfterMessage('AFTER', joinPoint, "${executionTime} ms", afterMessage))
            } else {
                log(level, buildAfterMessage('AFTER', joinPoint, "${executionTime} ns", afterMessage))
            }
        }
    }

    Logger getLogger() {
        return (Logger)log
    }

    void log(String level, String format) {
        LevelLogger.log(this.logger, level, format)
    }

    String buildMessage(String event, JoinPoint joinPoint, String message) {
        "${event}:${joinPoint.getSignature()} -> ${message ?: ''}"
    }

    String buildAfterMessage(String event, JoinPoint joinPoint, String execTimeStr, String message) {
        "${event}:${joinPoint.getSignature()}, EXEC_TIME:${execTimeStr} -> ${message ?: ''}"
    }

}
