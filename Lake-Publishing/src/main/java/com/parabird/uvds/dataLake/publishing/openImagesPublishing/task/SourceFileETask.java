package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.common.enums.MediaType;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.DiskExtractor;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class SourceFileETask extends MonitorableTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SourceFileETask.class);

    private IMQOperator<DiskSourceFile> queue;

    private IMQOperator<File> fileQueue;

    public SourceFileETask() {}

    public IMQOperator<DiskSourceFile> getQueue() {
        return queue;
    }

    public void setQueue(IMQOperator<DiskSourceFile> queue) {
        this.queue = queue;
    }

    public IMQOperator<File> getFileQueue() {
        return fileQueue;
    }

    public void setFileQueue(IMQOperator<File> fileQueue) {
        this.fileQueue = fileQueue;
    }

    public void initialize(IMQOperator<File> fileQueue, IMQOperator<DiskSourceFile> queue) {
        setFileQueue(fileQueue);
        setQueue(queue);
    }

    @Override
    public String call() {
        Timer timer = new Timer(true);

        timer.schedule(new PublishingMonitorTask(this), 10000, 30000);

        File file = null;

        setCount(0L);

        while (true) {
            try {
                file = getFileQueue().load();
            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for loading! " +
                        "Either time out for message queue is too short or load method starved");
            }

            if (file == null) break;
            try {

                getQueue().sink(DiskExtractor.extractSourceMedia(file, MediaType.GENERAL, null));
                addCount();

            } catch (TimeoutException e) {
                logger.error("Unexpected wait time for sinking Media Record : " + file.getAbsolutePath() +
                        " ! Either time out for message queue is too short or sink method starved");
            } catch (IOException e) {
                logger.error("Can not access to source media file : " + file.getAbsolutePath() + " !");
            }
        }
        getQueue().end();

        timer.cancel();

        return "Extracting source medias to transformer completed! Finish [" + getCount().toString() + "] extracting";
    }
}
