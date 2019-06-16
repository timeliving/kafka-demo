package com.boot.kafka.producer;

import com.boot.kafka.data.Message;
import com.boot.kafka.serializer.MessageSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerSimple2 implements Runnable {



    private final KafkaProducer<String, Message> messageProducer;
    private final String topic;


    public KafkaProducerSimple2(String topicName, String serialized) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", serialized);
        this.messageProducer = new KafkaProducer<String, Message>(props);
        this.topic = topicName;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for(;;) {
                Message message = new Message();
                String messageStr="你好，这是第"+messageNo+"条数据";
                message.setMsg(messageStr);
                message.setId(System.currentTimeMillis());
                messageProducer.send(new ProducerRecord<String, Message>(topic, "message", message));
                //生产了100条就打印
                if(messageNo%100==0){
                    System.out.println("发送的信息:" + messageStr);
                }
                //生产1000条就退出
                if(messageNo%1000==0){
                    System.out.println("成功发送了"+messageNo+"条");
                    break;
                }
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            messageProducer.close();
        }
    }

    public static void main(String args[]) {
        KafkaProducerSimple2 test1 = new KafkaProducerSimple2("KAFKA_TEST", MessageSerializer.class.getName());
        Thread thread1 = new Thread(test1);
        thread1.start();
    }
}
