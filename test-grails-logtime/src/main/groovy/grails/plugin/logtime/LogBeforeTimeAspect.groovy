package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature

import java.lang.annotation.Annotation
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Slf4j
@Aspect
class LogBeforeTimeAspect {

    @Before("@annotation(grails.plugin.logtime.LogBeforeTime)")
    void logExecutionTime(JoinPoint joinPoint) throws Throwable {
        // get jointPoint method annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Annotation annotation = signature.getMethod().getAnnotation(LogBeforeTime)

        def level = annotation.level()
        log.debug "log with level: ${level}"

        def format = annotation.format()
        log.debug "log with format: $format"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)

        //todo: add to logging with more info such thread, MDC, etc
        log(level, "BEFORE: ${joinPoint.getSignature()}, TIME: ${ZonedDateTime.now().format(formatter)}")
    }

    protected static void log(String level, String format) {
        LevelLogger.log(getLogger(), level, format)
    }

    protected static Logger getLogger() {
        //todo: refactor this
        log
    }

}
