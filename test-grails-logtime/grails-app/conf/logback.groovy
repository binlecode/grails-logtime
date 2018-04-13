import grails.util.BuildSettings
import grails.util.Environment

import java.nio.charset.Charset

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')
        // Mapped Diagnostic Context (MDC) manages contextual information on a per thread basis
        // note that a child thread does not automatically inherit a copy of MDC of its parent
        // see: https://logback.qos.ch/manual/mdc.html
//        pattern = "%d [%t] %-5level LG:%logger{50} MSG:[%msg] MDC:[tsnId:%X{transactionId:-XX} actId:%X{actorId:-XX} reqInfo:%X{reqInfo:-XX}]%n"
        pattern = "%d [%t] %highlight(%-5level) %logger{50} MSG:[%msg] MDC:[%mdc] %n"  // allow print-out of whole MDC content
//        pattern = "%d [%t] %highlight(%-5level) %magenta(LG):%logger{50} %magenta(MSG:[) %msg %magenta(]) %magenta(MDC:[) %mdc %magenta(]) %n %X{key1}"  // allow print-out of whole MDC content
    }
}

root(ERROR, ['STDOUT'])

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}


def loggerList = ['STDOUT']

logger('grails.app.controllers', DEBUG, loggerList, false)
logger('grails.app.services', DEBUG, loggerList, false)
logger('grails.app.jobs', DEBUG, loggerList, false)
logger('grails.app.domain', DEBUG, loggerList, false)
logger('grails.app.taglibs', DEBUG, loggerList, false)
logger('grails.app.init', DEBUG, loggerList, false)

logger('org.apache.http.headers', INFO, loggerList, false)
//    logger('org.apache.http.wire', DEBUG, loggerList, false)

logger('grails.app.controllers.test.grails.plugin.logtime.RequestAuditInterceptor', TRACE, loggerList, false)
logger('grails.plugin.logtime', DEBUG, loggerList, false)
logger('test.grails.plugin.logtime', DEBUG, loggerList, false)

