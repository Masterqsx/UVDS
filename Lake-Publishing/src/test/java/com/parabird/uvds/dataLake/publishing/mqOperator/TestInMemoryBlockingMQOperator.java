package com.parabird.uvds.dataLake.publishing.mqOperator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

public class TestInMemoryBlockingMQOperator {

    private static Logger logger = LoggerFactory.getLogger(TestInMemoryBlockingMQOperator.class);

    @Autowired
    InMemoryBlockingMQOperator<Integer> operator = new InMemoryBlockingMQOperator<>();

    private Thread producer;

    private Thread consumer;

    @Before
    public void setUp() {
        BlockingQueue<Integer> queue= new LinkedBlockingQueue<Integer>(10);
        operator.initialize(queue, 1L);

        producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        operator.sink(1);
                    } catch (TimeoutException e) {
                        logger.info(e.toString());
                        return;
                    }
                }
                operator.setActive(false);
            }
        });

        consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer res = null;
                int sum = 0;
                while (true) {
                    try {
                        res = operator.load();
                    } catch (TimeoutException e) {
                        logger.info(e.toString());
                        break;
                    }
                    if (res == null) break;
                    sum += res;
                }
                logger.info(Integer.toString(sum));
            }
        });
    }

    @Test
    public void testSinkAndLoad() throws InterruptedException {
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    @After
    public void cleanUp() {
        operator.end();
    }

}
