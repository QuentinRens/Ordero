package com.rensq.ordero.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rensq.ordero.domain", "com.rensq.ordero.service", "com.rensq.ordero.api"})
public class OrderoRunner {
    public static void main(String[] args) {
        SpringApplication.run(OrderoRunner.class, args);
    }
}
