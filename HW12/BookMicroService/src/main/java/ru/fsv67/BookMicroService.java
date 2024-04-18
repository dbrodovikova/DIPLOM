package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Библиотека - управление книгами",
                description = "Управление книгами",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class BookMicroService {
    public static void main(String[] args) {
        SpringApplication.run(BookMicroService.class, args);
    }
}