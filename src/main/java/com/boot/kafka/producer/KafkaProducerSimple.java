package com.boot.kafka.producer;

import com.boot.kafka.data.Message;
import com.boot.kafka.serializer.MessageSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class KafkaProducerSimple implements Runnable {



    private final KafkaProducer<String, String> producer;
    private final String topic;
    public KafkaProducerSimple(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        this.producer = new KafkaProducer<String, String>(props);
        this.topic = topicName;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for(;;) {
                Message message = new Message();
                String messageStr="��ã����ǵ�"+messageNo+"������";

                producer.send(new ProducerRecord<String, String>(topic, "message", messageStr));
                //������100���ʹ�ӡ
                if(messageNo%100==0){
                    System.out.println("���͵���Ϣ:" + messageStr);
                }
                //����1000�����˳�
                if(messageNo%1000==0){
                    System.out.println("�ɹ�������"+messageNo+"��");
                    break;
                }
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String args[]) {
        KafkaProducerSimple test1 = new KafkaProducerSimple("KAFKA_TEST");
        Thread thread1 = new Thread(test1);
        thread1.start();
    }
}
