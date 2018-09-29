package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.common.enums.MediaType;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.DiskExtractor;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import com.parabird.uvds.dataLake.publishing.openImagesPublishing.transformer.OpenImagesTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class SourceMetaDataETTask extends MonitorableTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SourceMetaDataETTask.class);

    private String metadataPath;

    private IMQOperator<ImageMedia> imageMediaQueue;

    public String getMetadataPath() {
        return metadataPath;
    }

    public void setMetadataPath(String metadataPath) {
        this.metadataPath = metadataPath;
    }

    public IMQOperator<ImageMedia> getImageMediaQueue() {
        return imageMediaQueue;
    }

    public void setImageMediaQueue(IMQOperator<ImageMedia> imageMediaQueue) {
        this.imageMediaQueue = imageMediaQueue;
    }

    public void initialize(IMQOperator<ImageMedia> imageMediaQueue, String metadataPath) {
        setImageMediaQueue(imageMediaQueue);
        setMetadataPath(metadataPath);
    }

    @Override
    public String call() {
        Timer timer = new Timer(true);

        timer.schedule(new PublishingMonitorTask(this), 10000, 30000);

        List<ImageMedia> medias = null;

        setCount(0L);
        try {
            medias = OpenImagesTransformer.transformSourceMetaDataOpenImages(
                    DiskExtractor.extractSourceMetaData(getMetadataPath(), MediaType.TEXT_CSV)
            );
        } catch (IOException e) {
            logger.error("Can not access to source media meta data : " + getMetadataPath() + " !");
        }
        for (ImageMedia media : medias) {
            try {
                getImageMediaQueue().sink(media);
                addCount();
            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for sinking : " + media.getFilePath() +
                        " ! Either time out for message queue is too short or sink method starved");
            }
        }

        getImageMediaQueue().end();

        timer.cancel();

        return "Extracting source meta data to Onboarding Schema completed! Finish [" + getCount().toString() + "] extracting";
    }
}
