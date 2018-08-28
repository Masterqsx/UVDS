package com.parabird.data_lake.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class LakeOnboardingApp {
    public static void main(String[] args) {
        SpringApplication.run(LakeOnboardingApp.class, args);
    }
}
