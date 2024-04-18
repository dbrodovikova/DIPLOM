package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Библиотека - управление выдачами",
                description = "Управление выдачами книг читателям",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class IssuanceMicroService {
    public static void main(String[] args) {
        SpringApplication.run(IssuanceMicroService.class, args);
    }
}