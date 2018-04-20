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
    String logLevel

    @Around("@annotation(grails.plugin.logtime.LogExecutionTime)")
    def logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // get joinPoint method annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Annotation annotation = signature.getMethod().getAnnotation(LogExecutionTime)
//        String methodName = signature.getMethod().getName()
//        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes()
//        Annotation[][] annotations = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations()

        def level = annotation.level() ?: logLevel

        // log before method execution
        log(level, buildMessage('before', joinPoint, annotation.beforeMessage() ?: annotation.message()))

        long start = System.nanoTime()
        try {
            return joinPoint.proceed()
        } finally {     // no catch clause, so that no target method's exception is blocked
                        // but post-call time tracking must finished
            def afterMessage = annotation.afterMessage() ?: annotation.message()
            long executionTime = System.nanoTime() - start
            def execTimeStr = "$executionTime ns"
            if (annotation.timeUnit().equalsIgnoreCase('ms')) {
                executionTime = (Long)(executionTime / 1000000L)
                execTimeStr = "$executionTime ms"
            }
            // log after method execution or exception
            log(level, buildMessage('after', joinPoint, afterMessage, execTimeStr))
        }
    }

    Logger getLogger() {
        return (Logger)log
    }

    void log(String level, String format) {
        LevelLogger.log(this.logger, level, format)
    }

    String buildMessage(String event, JoinPoint joinPoint, String message = null, String execTimeStr = null) {
        "_event=${event}, _method=${joinPoint.getSignature()}${execTimeStr ? ', _runtime=' + execTimeStr : ''}${message ? ', _message=' + message : ''}"
    }
}
