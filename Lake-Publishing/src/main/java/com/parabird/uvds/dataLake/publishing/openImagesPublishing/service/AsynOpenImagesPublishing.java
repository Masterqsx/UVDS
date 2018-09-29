package com.parabird.uvds.dataLake.publishing.openImagesPublishing.service;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.mqOperator.IMQOperator;
import com.parabird.uvds.dataLake.publishing.mqOperator.InMemoryBlockingMQOperator;
import com.parabird.uvds.dataLake.publishing.openImagesPublishing.task.*;
import com.parabird.uvds.dataLake.publishing.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class AsynOpenImagesPublishing {

    private Logger logger = LoggerFactory.getLogger(AsynOpenImagesPublishing.class);

    @Autowired
    private AsyncListenableTaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AsynTasksCallback callback;

    private IMQOperator<File> fileQueue = new InMemoryBlockingMQOperator<File>(
            new LinkedBlockingQueue<>(Integer.MAX_VALUE)
            , 1L);

    private IMQOperator<DiskSourceFile> sourceFileQueue = new InMemoryBlockingMQOperator<DiskSourceFile>(
            new LinkedBlockingQueue<>(Integer.MAX_VALUE)
            , 1L);

    private IMQOperator<ImageMedia> imageMediaQueue = new InMemoryBlockingMQOperator<ImageMedia>(
            new LinkedBlockingQueue<>(Integer.MAX_VALUE)
            , 1L);

    private List<String> sourceMediaFolder;

    private List<String> sourceMetadata;

    private List<ListenableFuture> runningFutures;

    private List<ITask> runningTasks;

    public List<String> getSourceMediaFolder() {
        return sourceMediaFolder;
    }

    public void setSourceMediaFolder(List<String> sourceMediaFolder) {
        this.sourceMediaFolder = sourceMediaFolder;
    }

    public List<String> getSourceMetadata() {
        return sourceMetadata;
    }

    public void setSourceMetadata(List<String> sourceMetadata) {
        this.sourceMetadata = sourceMetadata;
    }

    public AsynOpenImagesPublishing() {}

    public void mqInitialize(int fileQueueCapacity, Long fileQueueTimeout
                            , int sourceFileQueueCapacity, Long sourceFileQueueTimeout
                            , int imageMediaQueueCapacity, Long imageMediaQueueTimeout) {
        fileQueue = new InMemoryBlockingMQOperator<File>(
            new LinkedBlockingQueue<>(fileQueueCapacity)
            , fileQueueTimeout);
        sourceFileQueue = new InMemoryBlockingMQOperator<DiskSourceFile>(
            new LinkedBlockingQueue<>(sourceFileQueueCapacity)
            , sourceFileQueueTimeout);
        imageMediaQueue = new InMemoryBlockingMQOperator<ImageMedia>(
            new LinkedBlockingQueue<>(imageMediaQueueCapacity)
            , imageMediaQueueTimeout);

    }

    public void waitAsynPublishFinish() {
        for (ListenableFuture f : runningFutures) {
            try {
                f.get();
            } catch (InterruptedException e) {
                logger.error(e.toString());
            } catch (ExecutionException e) {
                logger.error(e.toString());
            }
        }
    }

    public void asynPublish() {

        runningFutures = new ArrayList<>();

        runningTasks = new ArrayList<>();

        for (String folder : getSourceMediaFolder()) {
            SourceFileDirETask dirETask = applicationContext.getBean(SourceFileDirETask.class);
            dirETask.initialize(fileQueue, folder);
            ListenableFuture<String> dirERes = taskExecutor.submitListenable(dirETask);
            dirERes.addCallback(callback);
            runningFutures.add(dirERes);
            runningTasks.add(dirETask);
        }

        SourceFileETask eTask = applicationContext.getBean(SourceFileETask.class);
        eTask.initialize(fileQueue, sourceFileQueue);
        ListenableFuture<String> eRes = taskExecutor.submitListenable(eTask);
        eRes.addCallback(callback);
        runningFutures.add(eRes);
        runningTasks.add(eTask);

        SourceFileTTask tTask = applicationContext.getBean(SourceFileTTask.class);
        tTask.initialize(sourceFileQueue, imageMediaQueue);
        ListenableFuture<String> tRes = taskExecutor.submitListenable(tTask);
        tRes.addCallback(callback);
        runningFutures.add(tRes);
        runningTasks.add(tTask);


        /*for (String metaPath : getSourceMetadata()) {
            SourceMetaDataETTask metaDataETTask = applicationContext.getBean(SourceMetaDataETTask.class);
            metaDataETTask.initialize(imageMediaQueue, metaPath);
            ListenableFuture<String> metaDataETRes = taskExecutor.submitListenable(metaDataETTask);
            metaDataETRes.addCallback(callback);
            runningFutures.add(metaDataETRes);
            runningTasks.add(metaDataETTask);
        }*/

        //SimpleLoaderTask loaderTask = applicationContext.getBean(SimpleLoaderTask.class);
        BatchJdbcLoaderTask loaderTask = applicationContext.getBean(BatchJdbcLoaderTask.class);
        loaderTask.initialize(imageMediaQueue);
        ListenableFuture<String> loaderRes = taskExecutor.submitListenable(loaderTask);
        loaderRes.addCallback(callback);
        runningFutures.add(loaderRes);
        runningTasks.add(loaderTask);
    }

    /** Since tasks are designed for asyn setup, this is the method for test only**/
    public void synPublishForTestOnly() {
        mqInitialize(Integer.MAX_VALUE, 1L
                    , Integer.MAX_VALUE, 1L
                    , Integer.MAX_VALUE, 1L);

        SourceFileDirETask dirETask = applicationContext.getBean(SourceFileDirETask.class);
        SourceFileETask eTask = applicationContext.getBean(SourceFileETask.class);
        SourceFileTTask tTask = applicationContext.getBean(SourceFileTTask.class);
        SourceMetaDataETTask metaDataETTask = applicationContext.getBean(SourceMetaDataETTask.class);
        SimpleLoaderTask loaderTask = applicationContext.getBean(SimpleLoaderTask.class);

        for (String folder : getSourceMediaFolder()) {
            dirETask.initialize(fileQueue, folder);
            logger.info(dirETask.call());
        }

        eTask.initialize(fileQueue, sourceFileQueue);
        logger.info(eTask.call());

        tTask.initialize(sourceFileQueue, imageMediaQueue);
        logger.info(tTask.call());

        for (String metaPath : getSourceMetadata()) {
            metaDataETTask.initialize(imageMediaQueue, metaPath);
            logger.info(metaDataETTask.call());
        }

        loaderTask.initialize(imageMediaQueue);
        logger.info(loaderTask.call());
    }

}
