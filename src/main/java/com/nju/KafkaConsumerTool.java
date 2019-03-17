package com.nju;

import java.util.*;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class KafkaConsumerTool {
    private String server;
    private String  topic;
    private String group;
    private KafkaConsumer<String,String> consumer = null;

    public KafkaConsumerTool(String server, String topic, String group){
        this.server = server;
        this.group = group;
        this.topic = topic;
    }

    public ConsumerRecords<String,String> getRecords(){
        if (this.consumer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", this.server);
            props.put("group.id", this.group);
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            this.consumer = new KafkaConsumer(props);
            this.consumer.subscribe(Arrays.asList(this.topic));
        }
        return this.consumer.poll(100);
    }

}

