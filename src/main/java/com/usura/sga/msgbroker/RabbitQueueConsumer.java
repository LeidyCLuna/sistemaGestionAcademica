package com.usura.sga.msgbroker;

import com.rabbitmq.client.*;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IEstudianteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitQueueConsumer {

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
            //log.error("Error al procesar el mensaje de la cola", e);
        }

    }
}



