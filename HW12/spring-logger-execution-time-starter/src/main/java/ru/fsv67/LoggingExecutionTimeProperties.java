package ru.fsv67;

import lombok.Data;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("logging.time")
public class LoggingExecutionTimeProperties {
    /**
     * Уровень логирования замера времени
     */
    private Level logLevel = Level.INFO;
}
