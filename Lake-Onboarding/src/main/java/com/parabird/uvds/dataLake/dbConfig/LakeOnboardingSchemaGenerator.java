package com.parabird.uvds.dataLake.dbConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:schema-generator.properties")
@ConfigurationProperties(prefix = "schema-generator")
@ComponentScan("com.parabird.uvds.dataLake")
public class LakeOnboardingSchemaGenerator {
    public static void main(String[] args) {
        SpringApplication.run(LakeOnboardingSchemaGenerator.class, args).close();
    }
}
