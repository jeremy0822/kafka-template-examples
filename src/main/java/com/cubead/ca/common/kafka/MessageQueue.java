package com.cubead.ca.common.kafka;

import org.apache.log4j.Logger;

//import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by xiaoao on 5/29/15.
 */
public class MessageQueue {
    private final static Logger logger = Logger.getLogger(MessageQueue.class);
    private final static int QUEUE_SIZE = 100000;
    private final static BlockingQueue<String> queue = new LinkedBlockingDeque<String>(QUEUE_SIZE);
//    private final static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(QUEUE_SIZE);

    public static BlockingQueue<String> getQueue(){
        return queue;
    }

    public static void put(String msg){
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            logger.error("put ca log to queue Error: " + e.getMessage());
        }
    }
}
