package ru.fsv67;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Получение настроечных данных из файла настроек
 */
@Data
@ConfigurationProperties("application.reader")
public class ReaderProperties {
    private int maxAllowedBooks = 1;
}
