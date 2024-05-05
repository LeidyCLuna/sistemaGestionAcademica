package com.usura.sga.configuracion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Message;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBrokerEvent implements Serializable {
    private Message message;
    private int replyCode;
    private String replyText;
    private  String Exchange;
    private String routingKey;


}
