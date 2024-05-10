package com.usura.sga.msgbroker.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.core.Message;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorBrokerEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Message message;
    private int replyCode;
    private String replyText;
    private String exchange;
    private String routingKey;

}
