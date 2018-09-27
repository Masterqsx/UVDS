package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import com.parabird.uvds.dataLake.publishing.openImagesPublishing.transformer.OpenImagesTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class SourceFileTTask extends MonitorableTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SourceFileTTask.class);

    private IMQOperator<DiskSourceFile> sourceFileQueue;

    private IMQOperator<ImageMedia> imageMediaQueue;

    public IMQOperator<DiskSourceFile> getSourceFileQueue() {
        return sourceFileQueue;
    }

    public void setSourceFileQueue(IMQOperator<DiskSourceFile> sourceFileQueue) {
        this.sourceFileQueue = sourceFileQueue;
    }

    public IMQOperator<ImageMedia> getImageMediaQueue() {
        return imageMediaQueue;
    }

    public void setImageMediaQueue(IMQOperator<ImageMedia> imageMediaQueue) {
        this.imageMediaQueue = imageMediaQueue;
    }

    public void initialize(IMQOperator<DiskSourceFile> sourceFileQueue, IMQOperator<ImageMedia> imageMediaQueue) {
        setSourceFileQueue(sourceFileQueue);
        setImageMediaQueue(imageMediaQueue);
    }

    @Override
    public String call() {
        DiskSourceFile sourceFile = null;

        setCount(0L);

        while (true) {
            try {

                sourceFile = getSourceFileQueue().load();

            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for loading! " +
                        "Either time out for message queue is too short or load method starved");
            }

            if (sourceFile == null) break;
            try {
                getImageMediaQueue().sink(OpenImagesTransformer.transformSourceImageMediaOpenImages(sourceFile));
                addCount();
            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for sinking Media Record : " + sourceFile.getFileAbsolutePath() +
                        " ! Either time out for message queue is too short or sink method starved");
            }
        }
        getImageMediaQueue().end();

        return "Transforming from source medias to media entity in Onboarding Schema completed ! Finish ["
            + getCount().toString() + "] transforming";
    }
}
