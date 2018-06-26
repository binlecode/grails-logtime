# grails-logtime
Grails plugin to provide annotation based method run time logging.

[![Build Status](https://travis-ci.org/binlecode/grails-logtime.svg?branch=dev-g31)](https://travis-ci.org/binlecode/grails-logtime)
 
## INTRODUCTION 

Grails-logtime is a Grails 3 plugin that provides a set of convenient annotation for methods time tracking.
Those annotations are applied to methods and picked by associated aspect pointcuts for flexible logging control.  

This repository contains source code of Grails-logtime plugin, and a sample Grails application.

## INSTALL

In host Grails application's build.gradle file:

- add jcenter plugin url

```groovy
repositories {
    // other repos
    jcenter { url "https://dl.bintray.com/ikalizpet/plugins" }
}
```

- and add plugin dependency
```groovy
plugins {
    compile ':grails-logtime:$version'
}
```

## PREREQUISITES

Hosting Grails application version 3+.


## CONFIGURATION

In host Grails application ```grails-app/conf/logback.groovy```

```groovy
// add log entry for logtime loggers such as below:
logger('grails.plugin.logtime', DEBUG, ['STDOUT'], false)
```

Optionally, you can set global default log level for logtime loggers. In host Grails application ```grails-app/conf/application.yml```

```yaml
logtime:
    level: debug  # default to debug       
```

## CHANGELOG

#### 1.3
* support custom log messages
* support custom pre-execute run closure

#### 1.0
* support default log level setting

#### v0.9
* support before and executeTime annotation
* support slf4j logging with logback settings
* support MDC key-value pairs

## CONTRIBUTORS

Bin Le (bin.le.code@gmail.com)


## LICENSE

Apache License Version 2.0. (http://www.apache.org/licenses/)