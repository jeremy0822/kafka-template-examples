package com.cubead.ca.common.kafka;

import org.springframework.stereotype.Repository;

/**
 * Created by xiaoao on 5/29/15.
 */

@Repository
public class ProducerConfig {
    /*private final static Logger logger = Logger.getLogger(ProducerConfig.class);

    private final String filePath = ApplicationProperties.getProproperty("ca.file.path");
    private final String broker = ApplicationProperties.getProproperty("kafka.brokers");
    private final String topic = ApplicationProperties.getProproperty("kafka.topic");
    private final String groupId = ApplicationProperties.getProproperty("kafka.groupId");
    private final String zookeepers = ApplicationProperties.getProproperty("kafka.zookeepers");
    private final String timeout = ApplicationProperties.getProproperty("zookeeper.connection.timeout.ms");
    private final String acks = ApplicationProperties.getProproperty("request.required.acks");
    private final String async = ApplicationProperties.getProproperty("producer.type");


    public Properties getProducerProperties(){
        Properties props = new Properties();
        props.put("metadata.broker.list", broker);
        props.put("zookeeper.connect", zookeepers);
        props.put("group.id", groupId);
        props.put("zookeeper.connection.timeout.ms", timeout);
        props.put("request.required.acks", acks);
        props.put("producer.type", async);

//        props.put("acks", "1");
//		props.put("retries", 1);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
//        props.put("timeout.ms", 5000);
//        props.put("metadata.fetch.timeout.ms", 5000);

        props.put("bootstrap.servers", broker);

        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("client.id", "CAProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }*/
}
