package com.cubead.ca.common.kafka;

/**
 * Created by xiaoao on 5/29/15.
 */
public class ProducerPool {

//    private static final Logger log = Logger.getLogger(ProducerPool.class);
//    private Map<String, IProducer> producers =
//            new HashMap<String, IProducer>();
//
//    public ProducerPool(String topic,ProducerConfig appConfig, ZkClient zkClient) {
//        this(topic, appConfig, zkClient, null);
//    }
//
//    public ProducerPool(String topic,ProducerConfig appConfig, ZkClient zkClient,
//                        Properties producerConfigOverrides) {
//        this(topic,appConfig, getBootstrapBrokers(zkClient), producerConfigOverrides);
//    }
//
//    public ProducerPool(String topic,ProducerConfig appConfig, String bootstrapBrokers,
//                        Properties producerConfigOverrides) {
//
//        Properties originalUserProps = appConfig.getOriginalProperties();
//
//        // Note careful ordering: built-in values we look up automatically first, then configs
//        // specified by user with initial KafkaRestConfig, and finally explicit overrides passed to
//        // this method (only used for tests)
//        Map<String, Object> binaryProps = new HashMap<String, Object>();
//        binaryProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapBrokers);
//        for (String propName : originalUserProps.stringPropertyNames()) {
//            binaryProps.put(propName, originalUserProps.getProperty(propName));
//        }
//        if (producerConfigOverrides != null) {
//            for (String propName : producerConfigOverrides.stringPropertyNames()) {
//                binaryProps.put(propName, producerConfigOverrides.getProperty(propName));
//            }
//        }
//        ByteArraySerializer keySerializer = new ByteArraySerializer();
//        keySerializer.configure(binaryProps, true);
//        ByteArraySerializer valueSerializer = new ByteArraySerializer();
//        keySerializer.configure(binaryProps, false);
//        KafkaProducer<byte[], byte[]> byteArrayProducer
//                = new KafkaProducer<byte[], byte[]>(binaryProps, keySerializer, valueSerializer);
//        producers.put(
//                topic,
//                new BinaryRestProducer(byteArrayProducer, keySerializer, valueSerializer));
//
//        Map<String, Object> avroProps = new HashMap<String, Object>();
//        avroProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapBrokers);
//        avroProps.put("schema.registry.url",
//                appConfig.getString(KafkaRestConfig.SCHEMA_REGISTRY_URL_CONFIG));
//        for (String propName : originalUserProps.stringPropertyNames()) {
//            avroProps.put(propName, originalUserProps.getProperty(propName));
//        }
//        if (producerConfigOverrides != null) {
//            for (String propName : producerConfigOverrides.stringPropertyNames()) {
//                avroProps.put(propName, producerConfigOverrides.getProperty(propName));
//            }
//        }
//        final KafkaAvroSerializer avroKeySerializer = new KafkaAvroSerializer();
//        avroKeySerializer.configure(avroProps, true);
//        final KafkaAvroSerializer avroValueSerializer = new KafkaAvroSerializer();
//        avroValueSerializer.configure(avroProps, false);
//        KafkaProducer<Object, Object> avroProducer
//                = new KafkaProducer<Object, Object>(avroProps, avroKeySerializer, avroValueSerializer);
//        producers.put(
//                EmbeddedFormat.AVRO,
//                new AvroRestProducer(avroProducer, avroKeySerializer, avroValueSerializer));
//    }
//
//    private static String getBootstrapBrokers(ZkClient zkClient) {
//        Seq<Broker> brokerSeq = ZkUtils.getAllBrokersInCluster(zkClient);
//        List<Broker> brokers = JavaConversions.seqAsJavaList(brokerSeq);
//        String bootstrapBrokers = "";
//        for (int i = 0; i < brokers.size(); i++) {
//            bootstrapBrokers += brokers.get(i).connectionString();
//            if (i != (brokers.size() - 1)) {
//                bootstrapBrokers += ",";
//            }
//        }
//        return bootstrapBrokers;
//    }
//
//    public <K, V> void produce(String topic, Integer partition,
//                               EmbeddedFormat recordFormat,
//                               SchemaHolder schemaHolder,
//                               Collection<? extends ProduceRecord<K, V>> records,
//                               ProduceRequestCallback callback) {
//        ProduceTask task = new ProduceTask(schemaHolder, records.size(), callback);
//        log.trace("Starting produce task " + task.toString());
//        RestProducer restProducer = producers.get(recordFormat);
//        restProducer.produce(task, topic, partition, records);
//    }
//
//    public void shutdown() {
//        for (RestProducer restProducer : producers.values()) {
//            restProducer.close();
//        }
//    }
//
//    public interface ProduceRequestCallback {
//
//        /**
//         * Invoked when all messages have either been recorded or received an error
//         *
//         * @param results list of responses, in the same order as the request. Each entry can be either
//         *                a RecordAndMetadata for successful responses or an exception
//         */
//        public void onCompletion(Integer keySchemaId, Integer valueSchemaId,
//                                 List<RecordMetadataOrException> results);
//    }
}