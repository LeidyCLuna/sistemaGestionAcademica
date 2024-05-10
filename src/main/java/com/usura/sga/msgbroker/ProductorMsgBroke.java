package com.usura.sga.msgbroker;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.msgbroker.domain.RabbitBindingRoute;
import com.usura.sga.msgbroker.event.RespuestaBroker;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.usura.sga.util.Constantes;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductorMsgBroke {

	@Qualifier("productorRabbit")
	private final RabbitTemplate productorRabbit;

	@Qualifier("rutaSGA")
	private final RabbitBindingRoute rutaSGA;

	public RespuestaBroker publicarMensaje(final EstudianteDto solicitud, final Map<String, Object> headers) {

		try {
			CorrelationData confirmCorrelation = new CorrelationData();
			productorRabbit.convertAndSend(rutaSGA.getExchange(), rutaSGA.getRoutingKey(),
					solicitud, m -> {
						m.getMessageProperties().getHeaders().putAll(headers);
						return m;
					}, confirmCorrelation);

			CorrelationData.Confirm resultado = confirmCorrelation.getFuture().get(Constantes.TIME_OUT_SEND_MSG_BROKER,
					TimeUnit.SECONDS);

			return RespuestaBroker.builder().solicitudExito(resultado.isAck()).mensajeRespuesta(resultado.getReason())
					.build();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return RespuestaBroker.builder().solicitudExito(false)
					.mensajeRespuesta("Se ha interrumpido el proceso, Error: " + e.getMessage()).build();
		} catch (ExecutionException | TimeoutException e) {
			throw new RuntimeException(String.format(
					"Error al publicar el mensaje en el Broker de ARL, Solicitud Registrar estudiante: %s, Error: %s.",
					solicitud, e.getMessage()));
		}
	}
}
