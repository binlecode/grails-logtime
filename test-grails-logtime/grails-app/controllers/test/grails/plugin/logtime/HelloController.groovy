package test.grails.plugin.logtime

import grails.plugin.logtime.LogBeforeTime
import grails.plugin.logtime.LogExecutionTime

import java.util.concurrent.TimeUnit

class HelloController {

    @LogBeforeTime(level = 'info', format = 'MM-dd-yyyy HH:mm:ss.SSS Z')
    def index() {
        log.debug "index called"
        render 'index'
    }

    @LogBeforeTime()
    def hello() {
        render 'hello'
    }


    @LogExecutionTime(level = 'warn')
    def foo() {
        log.debug "foo called"

        TimeUnit.MICROSECONDS.sleep(2000)

        render 'bar'
    }
}
