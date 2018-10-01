package com.parabird.uvds.dataLake.publishing;

import com.parabird.uvds.dataLake.publishing.openImagesPublishing.service.AsynOpenImagesPublishing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LakePublishingStartUpRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(LakePublishingStartUpRunner.class);

    @Autowired
    ApplicationContext appContext;

    @Autowired
    AsynOpenImagesPublishing publishing;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());

        if (!args.containsOption("medias")
            || !args.containsOption("metadata")) {
            logger.error("You need to specify medias's folders and metadata paths");
            return;
        }

        publishing.setSourceMediaFolder(args.getOptionValues("medias"));

        publishing.setSourceMetadata(args.getOptionValues("metadata"));

        publishing.mqInitialize(1000, 60L, 1000, 60L, 1000, 60L);

        publishing.asynPublish();

        publishing.waitAsynPublishFinish();

        SpringApplication.exit(appContext, () -> 0);

    }

}
