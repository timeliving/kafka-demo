package com.boot.kafka.consumer;

import com.boot.kafka.data.Message;
import com.boot.kafka.deserializer.MessageDeserializer;
import com.boot.kafka.serializer.MessageSerializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerSimple implements Runnable {

    private final KafkaConsumer<String, String> consumer;

    private ConsumerRecords<String, String> msgList;

    private final String topic;

    private static final String GPOUPID = "group1";

    public KafkaConsumerSimple(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GPOUPID);
        props.put("enabled.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));

    }




    @Override
    public void run() {
        int messageNo = 1;
        System.out.println("===============开始消费=============");
        try {
            for (;;) {
                msgList = consumer.poll(1000);
                if (null!=msgList&&msgList.count()>0) {
                    for (ConsumerRecord<String, String> record : msgList) {
                        if (messageNo%100 == 0) {
                            System.out.println(messageNo+"==============receive:key=" + record.key() + ", value=" + record.value()+"====offset="+record.offset());
                        }
                        if (messageNo%10000 == 0) {
                            break;
                        }
                        messageNo++;
                    }
                }else {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }

    public static void main(String args[]) {
        KafkaConsumerSimple test1 = new KafkaConsumerSimple("KAFKA_TEST");
        Thread thread1 = new Thread(test1);
        thread1.start();
    }
}
