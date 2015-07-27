package com.cubead.ca.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cubead.ca.common.file.FileManager;
import com.cubead.ca.common.util.Constant.LogTypeEnum;
import com.cubead.ca.service.IReciveLogs;
import com.cubead.ca.vo.RequestData;
import com.cubead.framework.common.ApplicationProperties;

@Service
public class ReviceLogsService implements IReciveLogs{
	
	private final static Logger logger = Logger.getLogger(ReviceLogsService.class);
	
	private final String filePath = ApplicationProperties.getProproperty("ca.file.path");
//	private final String broker = ApplicationProperties.getProproperty("kafka.brokers");
//	private final String topic = ApplicationProperties.getProproperty("kafka.topic");
//	private final String groupId = ApplicationProperties.getProproperty("kafka.groupId");
//	private final String zookeepers = ApplicationProperties.getProproperty("kafka.zookeepers");
//	private final String timeout = ApplicationProperties.getProproperty("zookeeper.connection.timeout.ms");
//	private final String acks = ApplicationProperties.getProproperty("request.required.acks");
//	private final String async = ApplicationProperties.getProproperty("producer.type");
	
	// private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private FileManager fm = new FileManager(filePath);

	public void writeLog(RequestData request) {
		StringBuilder sb = new StringBuilder(40);
		sb.append(fm.getPath(LogTypeEnum.ONLINE)).append("raw").append("_");
		sb.append(request.getDomain()).append("_").append(request.getHour());
		sb.append(".log");
		try {
			fm.write(sb.toString(), request.toString());
		}
		catch (Exception exp) {
			logger.error(" write data to log failed:"+sb.toString(), exp);
		}
	}

	public void writeKafka(RequestData request) {
//		Properties props = new Properties();
//		props.put("metadata.broker.list", broker);
//		props.put("zookeeper.connect", zookeepers);
//		props.put("group.id", groupId);
//		props.put("zookeeper.connection.timeout.ms", timeout);
//		props.put("request.required.acks", acks);
//		props.put("producer.type", async);
//		props.put("serializer.class", "kafka.serializer.StringEncoder");
//
//		props.put("acks", "1");
////		props.put("retries", 1);
//		props.put("batch.size", 16384);
//		props.put("linger.ms", 1);
//		props.put("buffer.memory", 33554432);
//		props.put("timeout.ms", 5000);
//		props.put("metadata.fetch.timeout.ms", 5000);
//
//		props.put("bootstrap.servers", broker);
//		props.put("client.id", "CAProducer");
//		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//		KafkaProducerFactory factory = KafkaProducerFactory.create(props, topic);
//		try {
//			factory.getProducer().send(request.toString());
//			// factory.getProducer().send(ow.writeValueAsString(request));
//		}
//		catch (Exception exp) {
//			logger.error(" write data to kafka failed:", exp);
//		}
	}
}
