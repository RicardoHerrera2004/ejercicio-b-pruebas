package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // <--- ¡Esta es la etiqueta mágica que faltaba!
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
