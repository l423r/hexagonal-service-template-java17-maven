package ru.nota.hexagonal.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.nota.hexagonal.template")
public class HexagonalTemplateServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HexagonalTemplateServiceApplication.class, args);
    }
}