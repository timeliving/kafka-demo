package com.boot.kafka.data;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Message {

    private Long id;

    private String msg;

//    private Date createTime;

    public Message() {
    }

    public Message(Long id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
