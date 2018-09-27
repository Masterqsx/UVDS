package com.parabird.uvds.dataLake.publishing.mqOperator;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@Scope("prototype")
public class InMemoryBlockingMQOperator<T> implements IMQOperator<T> {

    private BlockingQueue<T> queue;

    /** Time Unit is minutes **/
    private Long timeout = 60L;

    private boolean active = false;

    // TODO replace ReentrantReadWriteLock with StampedLock
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public InMemoryBlockingMQOperator() {}

    public InMemoryBlockingMQOperator(BlockingQueue<T> queue, Long timeout) {
        initialize(queue, timeout);
    }

    public BlockingQueue<T> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean isActive() {
        rwLock.readLock().lock();
        boolean cur = active;
        rwLock.readLock().unlock();

        return cur;
    }

    @Override
    public void setActive(boolean active) {
        rwLock.writeLock().lock();
        this.active = active;
        rwLock.writeLock().unlock();
    }

    @Override
    public void initialize(BlockingQueue<T> queue, Long timeout) {
        setQueue(queue);
        setActive(true);
    }

    @Override
    public void end() {
        setActive(false);
    }

    @Override
    public void sink(T o) throws TimeoutException {
        try {
            if ( !queue.offer(o, timeout, TimeUnit.SECONDS) && isActive())
                throw new TimeoutException("InMemoryBlockingMQOperator Sink Timeout");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T load() throws TimeoutException {
        T res = null;
        try {

            res = queue.poll(timeout, TimeUnit.SECONDS);

            if (res == null && isActive())
                throw new TimeoutException("InMemoryBlockingMQOperator load Timeout");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }
}
