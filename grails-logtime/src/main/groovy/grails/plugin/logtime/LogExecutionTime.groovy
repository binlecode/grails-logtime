package grails.plugin.logtime

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {

    String value() default ''

    /**
     * @return custom log level
     */
    String level() default ''

    /**
     * @return custom general log message if custom before/after message is not set
     */
    String message() default ''

    /**
     * @return custom log message before target method execution
     */
    String beforeMessage() default ''

    /**
     * @return custom log message after target method execution (or error out)
     */
    String afterMessage() default ''

    /**
     * @return unit for the execution time, default to 'ms' (millisecond)
     */
    String timeUnit() default 'ms'  // 'ns' or 'ms'

    //todo: add more optional fields

}
