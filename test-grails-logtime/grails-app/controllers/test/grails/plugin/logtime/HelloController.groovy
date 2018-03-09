package test.grails.plugin.logtime

import grails.plugin.logtime.LogBeforeTime
import grails.plugin.logtime.LogExecutionTime

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


    @LogExecutionTime(level = 'error')
    def foo() {
        log.debug "foo called"

        render 'bar'
    }
}
