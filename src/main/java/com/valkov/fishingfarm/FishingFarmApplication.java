package com.valkov.fishingfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FishingFarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(FishingFarmApplication.class, args);
    }
}
