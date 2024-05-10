package com.usura.sga.msgbroker;

import com.rabbitmq.client.*;
import com.usura.sga.controller.EstudianteController;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IConsumidorService;
import com.usura.sga.service.IEstudianteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteController.class);

    @Autowired
    IConsumidorService iConsumidorService;


    @RabbitListener(queues = "${rabbitmq.routing.sga.queue}", containerFactory = "listenerRabbit")
    public void receive(@Payload Message mensaje, Channel channel, @Headers Map<String, Object> headers, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        iConsumidorService.procesarEnvioEstudiante(mensaje, channel, headers, tag);
    }
}



