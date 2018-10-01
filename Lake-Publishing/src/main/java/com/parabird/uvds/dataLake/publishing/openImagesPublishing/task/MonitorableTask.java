package com.parabird.uvds.dataLake.publishing.openImagesPublishing.task;

import com.parabird.uvds.dataLake.publishing.task.ITask;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MonitorableTask implements ITask {

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private Long count = 0L;

    @Override
    public Long getCount() {
        Long cur = count;

        return cur;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void addCount() {
        this.count++;
    }

    public void addCount(Long add) {
        this.count += add;
    }
}
