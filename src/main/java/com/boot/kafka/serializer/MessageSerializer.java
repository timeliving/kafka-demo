package com.boot.kafka.serializer;

import com.boot.kafka.data.Message;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class MessageSerializer implements Serializer<Message> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            byte[] serializedMsg;
            byte[] serializedTime;
            int stringSize;
            int timeSize;
            if (data == null) {
                return null;
            } else {
                if (data.getMsg() != null) {
                    serializedMsg = data.getMsg().getBytes("UTF-8");
                    stringSize = serializedMsg.length;
                } else {
                    serializedMsg = new byte[0];
                    stringSize = 0;
                }
//                if (data.getCreateTime() != null) {
//                    serializedTime = data.getCreateTime().toString().getBytes("UTF-8");
//                    timeSize = serializedTime.length;
//                } else {
//                    serializedTime = new byte[0];
//                    timeSize = 0;
//                }
                ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + stringSize);
                buffer.putLong(data.getId());
                buffer.putInt(stringSize);
//                buffer.putInt(timeSize);
                buffer.put(serializedMsg);
//                buffer.put(serializedTime);
                return buffer.array();
            }
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer to byte[] " + e);
        }
    }

    @Override
    public void close() {

    }
}
