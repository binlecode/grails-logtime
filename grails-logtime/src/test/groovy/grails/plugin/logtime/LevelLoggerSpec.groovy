package grails.plugin.logtime

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import spock.lang.Specification

class LevelLoggerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test log with level"() {
        Logger logger = org.slf4j.LoggerFactory.getLogger(this.class)

        expect:
        LevelLogger.log(logger, 'error', 'dummy') == null
        LevelLogger.log(logger, 'warn', 'dummy') == null
        LevelLogger.log(logger, 'info', 'dummy') == null




    }
}
