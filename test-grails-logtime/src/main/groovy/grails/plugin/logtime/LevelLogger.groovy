package grails.plugin.logtime

import ch.qos.logback.classic.Logger

class LevelLogger {

    static final enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    static void log(Logger logger, String level, String format) {
        switch (level?.toUpperCase()) {
            case LogLevel.TRACE.toString():
                logger.trace(format)
                break
            case LogLevel.DEBUG.toString():
                logger.debug(format)
                break
            case LogLevel.INFO.toString():
                logger.info(format)
                break
            case LogLevel.WARN.toString():
                logger.warn(format)
                break
            case LogLevel.ERROR.toString():
                logger.error(format)
                break
            default:
                // do nothing
                break
        }
    }

}
