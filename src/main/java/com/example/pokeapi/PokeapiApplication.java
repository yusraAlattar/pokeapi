package com.example.pokeapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Pokemon Api",
        version = "1.0",
        description = "Pokemon service using Java Spring Boot",
        contact = @Contact(
                url = "https://github.com/yusraAlattar",
                name = "@Yus")
))
public class PokeapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokeapiApplication.class, args);
    }

}
