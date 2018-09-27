package com.parabird.uvds.dataLake.publishing;

import com.parabird.uvds.dataLake.publishing.openImagesPublishing.service.AsynOpenImagesPublishing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.parabird.uvds")
public class LakePublishingApp {

    public static void main(String[] args) {
        SpringApplication.run(LakePublishingApp.class, args);
    }
}
