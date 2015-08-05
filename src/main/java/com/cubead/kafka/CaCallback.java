package com.cubead.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class CaCallback implements Callback {
	private long startTime;
	private String message;

	public CaCallback(long startTime, String message) {
		this.startTime = startTime;
		this.message = message;
	}

	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println("message sent to partition(" + metadata.partition() + "),offset(" + metadata.offset() + "),length(" + message.length() + "),cost"
					+ elapsedTime + " ms.");
		} else {
			System.err.println(exception.getMessage());
		}
	}
}
