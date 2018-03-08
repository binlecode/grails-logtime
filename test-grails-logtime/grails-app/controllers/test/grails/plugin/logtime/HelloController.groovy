package test.grails.plugin.logtime

import grails.plugin.logtime.LogExecutionTime

class HelloController {

    def index() {
        log.debug "index called"
        render 'index'
    }

    @LogExecutionTime(level = 'error')
    def foo() {
        log.debug "foo called"

        render 'bar'
    }
}
