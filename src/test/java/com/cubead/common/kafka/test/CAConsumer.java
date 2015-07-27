package com.cubead.common.kafka.test;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetMetadata;
import org.apache.kafka.common.TopicPartition;

public class CAConsumer implements Consumer<Object, Object> {

	@Override
	public void subscribe(String... topics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(TopicPartition... partitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(String... topics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(TopicPartition... partitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map poll(long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OffsetMetadata commit(boolean sync) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OffsetMetadata commit(Map offsets, boolean sync) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void seek(Map offsets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map position(Collection partitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map committed(Collection partitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map offsetsBeforeTime(long timestamp, Collection partitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map metrics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}


}
