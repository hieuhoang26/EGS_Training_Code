```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true">

    <!-- ========== 1. Define Log File Path ========== -->
    <property name="LOG_PATH" value="logs" />

    <!-- ========== 2. Console Appender ========== -->
    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- ========== 3. Rolling File Appender (Rotate by date & size) ========== -->
    <appender name="RollingFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/app.log</file>

        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- Rotate every day and keep 30 days -->
            <fileNamePattern>${LOG_PATH}/archive/app-%d{yyyy-MM-dd}.%i.gz</fileNamePattern>
            <maxHistory>30</maxHistory>

            <!-- Support file size rotation per day -->
            <timeBasedFileNamingAndTriggeringPolicy
                    class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>

        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- ========== 4. Set Log Levels ========== -->
    <logger name="org.springframework" level="INFO"/>
    <logger name="com.example.demo" level="DEBUG"/>

     ========== 5. Root Logger ==========
    <root level="INFO">
        <appender-ref ref="Console"/>
        <appender-ref ref="RollingFile"/>
    </root>

</configuration>

```