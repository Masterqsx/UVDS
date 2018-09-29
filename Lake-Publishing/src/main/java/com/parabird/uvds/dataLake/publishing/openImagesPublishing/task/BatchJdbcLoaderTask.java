package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.service.BatchJdbcLoader;
import com.parabird.uvds.dataLake.onboarding.db.service.SimpleLoader;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class BatchJdbcLoaderTask extends MonitorableTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(BatchJdbcLoaderTask.class);

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
    BatchJdbcLoader loader;


    @Override
    public String call() throws Exception {

        Timer timer = new Timer(true);

        timer.schedule(new PublishingMonitorTask(this), 10000, 30000);

        ImageMedia media = null;

        setCount(0L);

        List<ImageMedia> batch = new ArrayList<>();

        while (true) {
            try {
                media = getImageMediaQueue().load();
            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for loading! " +
                        "Either time out for message queue is too short or load method starved");
            }

            if (media == null) {
                if (!batch.isEmpty()) {
                    loader.saveImageMediaByUid(batch);
                    addCount((long) batch.size());
                    batch.clear();
                }
                break;
            }

            batch.add(media);
            if (batch.size() == 50) {
                loader.saveImageMediaByUid(batch);
                addCount((long) batch.size());
                batch.clear();
            }
        }

        timer.cancel();

        return "Loading to Onboarding Schema completed! Finish [" + getCount().toString() + "] loading";
    }
}
