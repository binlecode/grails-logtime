package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature

import java.lang.annotation.Annotation

@CompileStatic
@Slf4j
@Aspect
class LogBeforeExecuteAspect {

    @Before("@annotation(grails.plugin.logtime.LogBeforeExecute)")
    void logExecutionTime(JoinPoint joinPoint) throws Throwable {
        // get jointPoint method annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Annotation annotation = signature.getMethod().getAnnotation(LogBeforeExecute)

        def level = annotation.level()

        // define an anonymous class from the given attribute closure definition,
        // and create an instance from this anonymous class
        // it is almost like defining an anonymous class with inline body
        Class runClass = annotation.run()
        if (runClass != Void) {
            Closure run = (Closure) runClass.newInstance(null, null)
            run.call(this, annotation)
        }

        log(level, buildMessage('before', joinPoint))
    }

    Logger getLogger() {
        return (Logger) log
    }

    void log(String level, String format) {
        LevelLogger.log(this.logger, level, format)
    }

    String buildMessage(String event, JoinPoint joinPoint, String message = null) {
        "_event=$event, _method=${joinPoint.getSignature()}${message ? ', _message=' + message : ''}"
    }
}
