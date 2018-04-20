package test.grails.plugin.logtime

import grails.plugin.logtime.LogBeforeExecute
import grails.plugin.logtime.LogExecutionTime

import java.util.concurrent.TimeUnit

class HelloController {

    HelloService helloService

    // the run closure has it variable which represents the LogBeforeExecutionAspect instance
    @LogBeforeExecute()
    def index() {
        log.debug "index called"
        render 'index'
    }

    @LogExecutionTime()
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

    @LogBeforeExecute(level = 'warn', message = 'custom message before action foobar',
            run = { aspect, annotation -> println "run closure before: $aspect" })
    def foobar() {
        render 'foo to bar!'
    }

}
