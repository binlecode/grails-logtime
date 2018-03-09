package test.grails.plugin.logtime

import groovy.transform.CompileStatic
import org.slf4j.MDC

@CompileStatic
class RequestAuditInterceptor {
    int order = HIGHEST_PRECEDENCE + 50 // make sure this is the first filter to hit

    public RequestAuditInterceptor() {
        match controller: '*', action: '*'  // intercept all requests
    }

    /**
     * @see {@link ch.qos.logback.classic.helpers.MDCInsertingServletFilter} for web audit
     */
    boolean before() {
//        MDC.put('reqMethod', request.method)
//        MDC.put('reqAgent', request.getHeader('User-Agent'))
//        MDC.put('reqAddr', request.remoteAddr)
//        MDC.put('reqClnt', request.getHeader('X-FORWARDED-FOR'))  // client's real ip if not null
        MDC.put('reqInfo', [
                method: request.method,
                remoteHost: request.remoteHost,
                userAgent: request.getHeader('User-Agent'),
                clientAddr: request.getHeader('X-FORWARDED-FOR') ?: request.remoteAddr
        ].toString())

        log.trace "HTTP request - method: ${request.method}, controller: ${controllerName}, action: ${actionName}, params: ${params}"
        true   // pass over to target controller
    }

    boolean after() { true }

    void afterView() {
        // clean request info from MDC to avoid cross-thread contamination
        MDC.remove('reqInfo')
    }
}
