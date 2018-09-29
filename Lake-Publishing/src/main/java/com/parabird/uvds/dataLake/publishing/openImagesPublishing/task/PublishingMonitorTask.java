package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.publishing.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class PublishingMonitorTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(PublishingMonitorTask.class);

    private ITask task;

    public PublishingMonitorTask(ITask task) {
        this.task = task;
    }

    @Override
    public void run() {
        logger.info(task.getClass().getSimpleName() + " completed [" + task.getCount().toString() + "] works");
        logger.info("**************");
    }
}
