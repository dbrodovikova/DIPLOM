package ru.fsv67;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingExecutionTimeProperties.class)
public class SpringLoggerExecutionTimeStarter {
    @Bean
    LoggingExecutionTime loggingExecutionTime(LoggingExecutionTimeProperties properties) {
        return new LoggingExecutionTime(properties);
    }
}