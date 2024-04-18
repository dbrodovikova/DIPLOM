package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Библиотека - управление читателями",
                description = "Управление читателями",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class ReaderMicroService {
    public static void main(String[] args) {
        SpringApplication.run(ReaderMicroService.class, args);
    }
}