package com.doctrine.apotres;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApotresBackendApplication {

    public static void main(String[] args) {
       
        SpringApplication.run(ApotresBackendApplication.class, args);

        System.out.println("✅ Backend Doctrine des Apôtres démarré sur http://localhost:8080");
    }
}
