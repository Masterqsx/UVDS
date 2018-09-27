package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.publishing.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

public class PublishingMonitorTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(PublishingMonitorTask.class);

    private List<ITask> tasks;


    public PublishingMonitorTask(List<ITask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        for (ITask task : tasks) {
            logger.info(task.getClass().getSimpleName() + " have completed [" + task.getCount().toString() + "] iterations");
        }
    }
}
