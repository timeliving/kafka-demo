package com.boot.kafka.deserializer;

import com.boot.kafka.data.Message;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class MessageDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        long id;
        int msgSize;
        String msg;
        try {
            if (data == null) {
                return null;
            }
            if (data.length < 0) {
                throw new SerializationException("Size of data received by InterDeserializer is shorter than excepted");
            }
            ByteBuffer buffer = ByteBuffer.wrap(data);
            id = buffer.getLong();
            msgSize = buffer.getInt();
//            timeSize = buffer.getInt();
            byte[] msgBytes = new byte[msgSize];
//            byte[] timeBytes = new byte[timeSize];
            buffer.get(msgBytes);
//            buffer.get(timeBytes);
            msg = new String(msgBytes, "UTF-8");

            return new Message(id, msg);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer to byte[]" + e);
        }
    }

    @Override
    public void close() {

    }
}
