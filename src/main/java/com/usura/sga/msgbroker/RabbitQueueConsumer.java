package com.usura.sga.msgbroker;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitQueueConsumer {
/*
    @Autowired
    IPreguntaService preguntaService;


    @RabbitListener(queues = "${rabbitmq.routing.sga.queue}", containerFactory = "listenerRabbit")
    // Cambiar DTO ajustar al dto consumidor
    public void receive(@Payload ListaPreguntasDto listaPreguntasDto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            String message = "";
            preguntaService.savepregunta(listaPreguntasDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }*/


}
