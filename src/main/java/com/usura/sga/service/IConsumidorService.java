package com.usura.sga.service;

import com.rabbitmq.client.Channel;
import com.usura.sga.dto.EstudianteDto;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Map;


public interface IConsumidorService {
    public void procesarEnvioEstudiante(Message mensaje, Channel channel, Map<String, Object> headers, long tag) throws IOException;

}
