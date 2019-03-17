package com.nju;

import java.util.Iterator;


import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaSource implements SourceFunction<ZZKQM>{
    private KafkaConsumerTool consumer = null;

    @Override
    public void cancel() {

    }

    @Override
    public void run(SourceContext<ZZKQM> sourceContext) {
        if(this.consumer == null){
            this.consumer = new KafkaConsumerTool("kafka-zookeeper:2181","ZZKQM","test");
        }
        while(true){
            ConsumerRecords<String,String> records = this.consumer.getRecords();

            Iterator it = records.iterator();
            while(it.hasNext()){
                ConsumerRecord<String,String> ne = (ConsumerRecord<String,String>) it.next();
                if(ne.key().length() > 10){
                    ZZKQM line = new ZZKQM();
                    sourceContext.collect(line);

                }
            }
        }
    }
}
