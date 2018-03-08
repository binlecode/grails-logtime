package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature

import java.lang.annotation.Annotation

@Slf4j
@Aspect
class LogExecutionTimeAspect {

    static enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
    }

    @Around("@annotation(grails.plugin.logtime.LogExecutionTime)")
    def logExecutionTime(ProceedingJoinPoint jointPoint) throws Throwable {
        // get jointPoint method annotation
        MethodSignature signature = (MethodSignature) jointPoint.getSignature()
        Annotation annotation = signature.getMethod().getAnnotation(LogExecutionTime)
//        String methodName = signature.getMethod().getName()
//        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes()
//        Annotation[][] annotations = jointPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations()

        def level = annotation.level()
        log.debug "log with level: ${level}"

        //todo: add to logging with more info such thread, MDC, etc
        log(level, "BEFORE CALL: ${jointPoint.getSignature()}")

        long start = System.nanoTime()
        try {
            return jointPoint.proceed()
        } finally {     // no catch clause, so that no target method's exception is blocked
                        // but post-call time tracking must finished
            long executionTime = System.nanoTime() - start
            log(level, "AFTER CALL: ${jointPoint.getSignature()}, EXECUTION_TIME: ${executionTime} ns")
        }
    }

    protected static void log(String level, String format) {
        Logger logger = getLogger()
        switch (level?.toUpperCase()) {
            case LogLevel.TRACE.toString():
                logger.trace(format)
                break
            case LogLevel.DEBUG.toString():
                logger.debug(format)
                break
            case LogLevel.INFO.toString():
                logger.info(format)
                break
            case LogLevel.WARN.toString():
                logger.warn(format)
                break
            case LogLevel.ERROR.toString():
                logger.error(format)
                break
            default:
                // do nothing
                break
        }
    }

    protected static Logger getLogger() {
        //todo: refactor this
        log
    }

}
