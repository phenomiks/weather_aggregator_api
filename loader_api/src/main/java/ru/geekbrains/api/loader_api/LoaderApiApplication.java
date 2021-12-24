package ru.geekbrains.api.loader_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:private.properties")
public class LoaderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoaderApiApplication.class, args);
    }

}
