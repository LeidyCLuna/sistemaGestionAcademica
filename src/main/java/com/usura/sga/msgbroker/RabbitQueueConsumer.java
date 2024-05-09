package com.usura.sga.msgbroker;

import com.rabbitmq.client.*;
import com.usura.sga.controller.EstudianteController;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IEstudianteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteController.class);

    @Autowired
    IEstudianteService estudianteService;


    @RabbitListener(queues = "${rabbitmq.routing.sga.queue}", containerFactory = "listenerRabbit")
    public void receive(@Payload EstudianteDto estudianteDto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            String message = "Consultando solicitudes registro de estudiante";
            estudianteService.crearEstudiante(estudianteDto);
            // Confirmar la recepción del mensaje
            channel.basicAck(tag, false);
        } catch (Exception e) {
            LOGGER.error("Error al procesar el mensaje de la cola", e);
        }

    }
}



