package com.cubead.ca.common.kafka;

/**
 * Created by xiaoao on 22/4/15.
 */
public class CAKafkaProducer {
//    private final static Logger logger = Logger.getLogger(CAKafkaProducer.class);
////    private Producer producer;
//    private String topic;
//    private KafkaProducer kafkaProducer;
////    final Charset charset = Charset.forName("utf-8");
//    
//    public CAKafkaProducer(Properties config, String topic){
//        if(kafkaProducer == null){
//            kafkaProducer = new KafkaProducer(config);
//        }
////        if(producer == null){
////            ProducerConfig props = new ProducerConfig(config);
////            producer = new Producer(props);
////        }
//        this.topic = topic;
//    }
//    
//    public void send(String msg){
//        kafkaProducer.send(new ProducerRecord(topic, msg));
////        kafkaProducer.send(new ProducerRecord(topic, msg.getBytes(charset)));
////        final KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, msg);
////        try {
////            kafkaProducer.send(new ProducerRecord(topic, msg.getBytes(charset))).get();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        } catch (ExecutionException e) {
////            e.printStackTrace();
////        }
////        producer.send(data);
////        producer.close();
//    }
}
