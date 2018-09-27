package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

@Component
@Scope("prototype")
public class SourceFileDirETask extends MonitorableTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SourceFileDirETask.class);

    private IMQOperator<File> fileQueue;

    private String folderPath;

    public SourceFileDirETask() {}

    public IMQOperator<File> getFileQueue() {
        return fileQueue;
    }

    public void setFileQueue(IMQOperator<File> fileQueue) {
        this.fileQueue = fileQueue;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void initialize(IMQOperator<File> fileQueue, String folderPath) {
        setFileQueue(fileQueue);
        setFolderPath(folderPath);
    }

    @Override
    public String call() {
        DirectoryStream<Path> dirStream = null;

        setCount(0L);
        try {
            dirStream = Files.newDirectoryStream(Paths.get(getFolderPath()));
            for (Path path : dirStream) {
                try {
                    getFileQueue().sink(path.toFile());
                    addCount();
                } catch (TimeoutException e) {
                    logger.error("Unexpected wait time for sinking : " + path.toAbsolutePath() +
                            " ! Either time out for message queue is too short or sink method starved");
                }
            }
        } catch (IOException e) {
            logger.error("Can not access to source media folder : " + getFolderPath() + " !");
        } finally {
            getFileQueue().end();
        }

        return "Extracting source medias in folder compeleted! Finish [" + getCount().toString() + "] extracting";
    }
}
