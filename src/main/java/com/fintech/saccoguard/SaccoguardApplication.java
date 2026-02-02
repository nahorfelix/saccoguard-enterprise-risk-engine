package com.fintech.saccoguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // This allows the "Auto-Auditor" to run every X seconds
public class SaccoguardApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaccoguardApplication.class, args);
    }
}