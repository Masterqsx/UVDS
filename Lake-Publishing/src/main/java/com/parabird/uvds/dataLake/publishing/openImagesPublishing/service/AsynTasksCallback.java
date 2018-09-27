package com.parabird.uvds.dataLake.publishing.openImagesPublishing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class AsynTasksCallback implements ListenableFutureCallback<String> {

    private Logger logger = LoggerFactory.getLogger(AsynTasksCallback.class);

    public AsynTasksCallback() {}

    @Override
    public void onFailure(Throwable ex) {
        logger.error("Task meets unexpected exception! " + ex.toString());
    }

    @Override
    public void onSuccess(String result) {
        logger.info(result);
    }
}
