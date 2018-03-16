package grails.plugin.logtime

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {

    String value() default ''

    String level() default 'debug'

    String message() default ''

    String beforeMessage() default ''

    String afterMessage() default ''

    String timeUnit() default 'ms'  // 'ns' or 'ms'

//    String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    //todo: add more optional fields

}
