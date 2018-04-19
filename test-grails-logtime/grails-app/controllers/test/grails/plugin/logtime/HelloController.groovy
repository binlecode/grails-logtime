package test.grails.plugin.logtime

import grails.plugin.logtime.LogBeforeExecute
import grails.plugin.logtime.LogExecutionTime

import java.util.concurrent.TimeUnit

class HelloController {

    HelloService helloService

    // the run closure has it variable which represents the LogBeforeExecutionAspect instance
    @LogBeforeExecute(level = 'info', message = 'my message', run = { aspect, annotation -> println "run closure before: $aspect" })
    def index() {
        log.debug "index called"
        render 'index'
    }

    @LogExecutionTime(level = 'warn')
    def greet() {
        render helloService.buildGreeting()
    }

    @LogExecutionTime(level = 'warn')
    def foo() {
        log.debug "foo called"

        TimeUnit.MICROSECONDS.sleep(2000)

        render 'bar'
    }

    @LogBeforeExecute(run = { aspect, LogBeforeExecute annotation ->
        aspect.log.info "I got annotation level: ${annotation.level()}"
    })
    def foo2() {
        render 'bar 2'
    }


}
