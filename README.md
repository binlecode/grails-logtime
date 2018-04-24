# grails-logtime
Grails plugin to provide annotation based method run time logging.

[![Build Status](https://travis-ci.org/binlecode/grails-logtime.svg?branch=dev-g31)](https://travis-ci.org/binlecode/grails-logtime)
 
## INTRODUCTION 

Grails-logtime is a Grails 3 plugin that provides a set of convenient annotation for methods time tracking.
Those annotations are applied to methods and picked by associated aspect pointcuts for flexible logging control.  

This repository contains source code of Grails-logtime plugin, and a sample Grails application.

## INSTALL

In host Grails application's build.gradle file:

1. add jcenter plugin url

```groovy
repositories {
    // other repos
    jcenter { url "https://dl.bintray.com/ikalizpet/plugins" }
```
2. and add plugin dependency
```groovy
plugins {
    compile ':grails-logtime:$version'
}
```

## PREREQUISITES

Hosting Grails application version 3+.


## CONFIGURATION


In host Grails application grails-app/conf/application.yml

```yaml
logtime:
    level: debug  # default to debug
        
```

## CHANGELOG

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