package com.usura.sga.configuracion;

import java.io.IOException;

import com.usura.sga.msgbroker.event.BrokerEvento;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.usura.sga.util.Constantes;

import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitChannelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitChannelService.class);

	public void confirmarNAck(Channel channel, long tag) {
		boolean multiple = false;
		boolean requeue = false;
		try {
			channel.basicNack(tag, multiple, requeue);
		} catch (IOException errorGeneral) {
			LOGGER.error(Constantes.LOG_CONSUMIDOR_BROKER, "Error procesando el mensaje {}",
					ExceptionUtils.getStackTrace(errorGeneral));
		}
	}

	public void confirmarAck(Channel channel, long tag) {
		boolean multiple = false;
		try {
			channel.basicAck(tag, multiple);
		} catch (IOException errorGeneral) {
			LOGGER.error(Constantes.LOG_CONSUMIDOR_BROKER, "Error confirmando el mensaje con tag: {}, Error: {}",
					Long.toString(tag), ExceptionUtils.getStackTrace(errorGeneral));
		}
	}

	public void rechazarMensaje(Channel channel, long tag) {
		boolean multiple = false;
		try {
			channel.basicReject(tag, multiple);
		} catch (IOException errorGeneral) {
			LOGGER.error(Constantes.LOG_CONSUMIDOR_BROKER, "Error confirmando el mensaje con tag: {}, Error: {}",
					Long.toString(tag), ExceptionUtils.getStackTrace(errorGeneral));
		}
	}

	@EventListener({ BrokerEvento.class })
	public void onAplicacionEventosBroker(BrokerEvento evento) {
		LOGGER.error(Constantes.LOG_CONSUMIDOR_BROKER, "Error recibiendo evento del broker {}", evento);
	}
}
