package com.cubead.ca.common.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

/**
 * Created by xiaoao on 5/29/15.
 */
public class CaCallback implements Callback {
	private final static Logger logger = Logger.getLogger(CaCallback.class);
	private long startTime;
	private String message;

	public CaCallback(long startTime, String message) {
		this.startTime = startTime;
		this.message = message;
	}

	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			logger.info("message sent to partition(" + metadata.partition() + "),offset(" + metadata.offset() + "),length(" + message.length() + "),cost"
					+ elapsedTime + " ms.");
		} else {
			logger.error(exception.getMessage());
		}
	}
}
