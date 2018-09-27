package com.parabird.uvds.dataLake.publishing.mqOperator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public interface IMQOperator<T> {
    boolean isActive();

    void setActive(boolean active);

    void initialize(BlockingQueue<T> queue, Long timeout);

    void end();

    public void sink(T o) throws TimeoutException;
    public T load() throws TimeoutException;
}
