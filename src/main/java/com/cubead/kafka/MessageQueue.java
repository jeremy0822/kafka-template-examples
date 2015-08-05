package com.cubead.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageQueue {
    private final static int QUEUE_SIZE = 100000;
    private final static BlockingQueue<String> queue = new LinkedBlockingDeque<String>(QUEUE_SIZE);

    public static BlockingQueue<String> getQueue(){
        return queue;
    }

    public static void put(String msg){
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            System.err.println("put ca log to queue Error: " + e.getMessage());
        }
    }
}
