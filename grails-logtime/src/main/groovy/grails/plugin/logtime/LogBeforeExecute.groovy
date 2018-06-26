package grails.plugin.logtime

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogBeforeExecute {

    String value() default ''

    /**
     * @return custom log level
     */
    String level() default ''

    /**
     * @return custom log message before target method execution
     */
    String message() default ''

    /**
     * @return custom in-line closure to run before method execution
     */
    Class run() default Void

}