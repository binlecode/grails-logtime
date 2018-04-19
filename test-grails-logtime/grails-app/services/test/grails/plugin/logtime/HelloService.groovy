package test.grails.plugin.logtime

import grails.plugin.logtime.LogExecutionTime
import grails.transaction.Transactional
import groovy.util.logging.Slf4j

import java.util.concurrent.TimeUnit

@Slf4j
@Transactional
class HelloService {

    @LogExecutionTime(beforeMessage = 'custom message before service!',
            afterMessage = 'custom message after service!',
            level = 'info')
    String buildGreeting() {
        log.info "sleep for 1 s"
        TimeUnit.MILLISECONDS.sleep(1000)

        throw new RuntimeException('this is the error thrown from helloService.buildGreeting()')

        return 'hello greeting from helloService'

    }
}
