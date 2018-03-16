package test.grails.plugin.logtime

import grails.plugin.logtime.LogExecutionTime
import grails.transaction.Transactional

import java.util.concurrent.TimeUnit

@Transactional
class HelloService {

    @LogExecutionTime(beforeMessage = 'before service!', afterMessage = 'after service!', level = 'info')
    String buildGreeting() {
        TimeUnit.MILLISECONDS.sleep(1000)

        throw new RuntimeException('this is the error thrown from helloService.buildGreeting()')

        return 'hello greeting from helloService'

    }
}
