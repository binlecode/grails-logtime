package grails.plugin.logtime

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogBeforeExecute {
    String value() default ''

    String level() default 'debug'

    String message() default ''

    Class run() default Void

//    String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ"


}