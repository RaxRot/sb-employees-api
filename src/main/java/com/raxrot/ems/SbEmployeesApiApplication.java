package com.raxrot.ems;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Management API",
                version = "1.0",
                description = "API for managing employees in the system",
                contact = @Contact(
                        name = "RaxRot",
                        email = "dasistperfektos@email.com",
                        url = "https://github.com/RaxRot"
                )
        )
)

@SpringBootApplication
public class SbEmployeesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbEmployeesApiApplication.class, args);
    }

}
