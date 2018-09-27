package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.service.SimpleLoader;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class SimpleLoaderTask extends MonitorableTask implements Callable<String>{

    private static Logger logger = LoggerFactory.getLogger(SimpleLoader.class);

    private static final String SOURCE_NAME = "OpenImagesV4";

    private static final String SOURCE_DESC = "Open Images V4 at https://storage.googleapis.com/openimages/web/download.html";


    private IMQOperator<ImageMedia> imageMediaQueue;

    public IMQOperator<ImageMedia> getImageMediaQueue() {
        return imageMediaQueue;
    }

    public void setImageMediaQueue(IMQOperator<ImageMedia> imageMediaQueue) {
        this.imageMediaQueue = imageMediaQueue;
    }

    public void initialize(IMQOperator<ImageMedia> imageMediaQueue) {
        setImageMediaQueue(imageMediaQueue);
    }


    @Autowired
    SimpleLoader loader;

    @Override
    public String call() {
        ImageMedia media = null;

        setCount(0L);

        while (true) {
            try {
                media = getImageMediaQueue().load();
            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for loading! " +
                        "Either time out for message queue is too short or load method starved");
            }

            if (media == null) break;
            loader.saveImageMediaBySource(media);
            addCount();
        }

        return "Loading to Onboarding Schema completed! Finish [" + getCount().toString() + "] loading";
    }
}
