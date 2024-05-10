package com.usura.sga.service.impl;


import com.rabbitmq.client.Channel;
import com.usura.sga.Exception.FinalizacionProcesoExcepcion;
import com.usura.sga.Exception.RelanzarProcesoExcepcion;
import com.usura.sga.msgbroker.ProductorMsgBroke;
import com.usura.sga.msgbroker.event.RespuestaBroker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import com.usura.sga.Exception.SolicitudEstudianteException;
import com.usura.sga.configuracion.RabbitChannelService;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IConsumidorService;
import com.usura.sga.service.IEstudianteService;
import com.usura.sga.util.Constantes;
import com.usura.sga.util.Utilidades;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.vavr.control.Try;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;


@Service
@RequiredArgsConstructor
public class CosumidorServiceImpl implements IConsumidorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CosumidorServiceImpl.class);

    private static final int N0 = 0;
    long retryCount = 1;
    private static long MAX_RETRIES_COUNT = 3;
    private final RabbitChannelService rabbitChannelService;
    private final IEstudianteService iEstudianteService;
    private final ProductorMsgBroke productorMsgBrokerARL;

    @Override
    public void procesarEnvioEstudiante(Message mensaje, Channel channel, Map<String, Object> headers, long tag) throws IOException {
        String body = new String(mensaje.getBody(), StandardCharsets.UTF_8);
        Long cantidadReintentos = 0L;
        EstudianteDto solicitud = null;
        try {
            cantidadReintentos = obtenerCantidadReintentos(headers);
            solicitud = obtenerSolicitudMensaje(body);
            iEstudianteService.crearEstudiante(solicitud);


        } catch (FinalizacionProcesoExcepcion e) {
            confirmarNAck(channel, tag);
            LOGGER.error("El proceso tiene error porque la informacion no es valida {}, Error {}", solicitud,
                    ExceptionUtils.getStackTrace(e));

            return;
        } catch (RelanzarProcesoExcepcion e) {


            publicarMensajeConDelay(solicitud, cantidadReintentos);

            //confirmarNAck(channel, tag);
            //channel.basicNack(tag, true, false);
            channel.basicReject(mensaje.getMessageProperties().getDeliveryTag(), false);


            LOGGER.error("Se ha presentado un error no controlado generando el archivo {}, Error {}", solicitud,
                    ExceptionUtils.getStackTrace(e));
            return;
        }
        LOGGER.info("Solicitud registro estudiante terminada correctamente {}", solicitud);
        rabbitChannelService.confirmarAck(channel, tag);

    }

    private EstudianteDto obtenerSolicitudMensaje(String body) {
        Try<EstudianteDto> trySolicitud = Try.of(() -> Utilidades.conversorJsonToObjeto(body, EstudianteDto.class));

        return trySolicitud.recover(throwable -> Match(throwable).of(Case($(), throwInScope -> {

            throw new SolicitudEstudianteException("Error obteniendo la informacion para la solicitud de creacion Estudiante " + throwInScope);
        }))).get();
    }


    private void confirmarNAck(Channel channel, long tag) {
        rabbitChannelService.confirmarNAck(channel, tag);
    }

    private void confirmarAck(Channel channel, long tag) {
        rabbitChannelService.confirmarAck(channel, tag);
    }

    private Long obtenerCantidadReintentos(Map<String, Object> headers) {
        if (headers != null && !headers.isEmpty()) {
            Optional<Entry<String, Object>> reintentos = headers.entrySet().stream()
                    .filter(h -> Constantes.REINTENTOS_HEADER.equals(h.getKey())).findFirst();
            return (Long) reintentos.map(Entry::getValue).orElse(Long.valueOf(N0));
        }

        return Long.valueOf(N0);
    }

    private void publicarMensajeConDelay(EstudianteDto solicitud, Long cantidadReintentos) {
        try {

            Object numMaxReint = Constantes.NUM_REINTENTOS_EXCEPCION_NO_CONTROLADA;
            int numeroMaximoReintentos = Utilidades.objetoNuloVacio(numMaxReint)
                    || Utilidades.strNuloVacioEspacioComoVacio(numMaxReint.toString())
                    ? Constantes.NUM_REINTENTOS_EXCEPCION_NO_CONTROLADA
                    : Integer.valueOf(numMaxReint.toString().trim());

            cantidadReintentos = cantidadReintentos + 1l;
            if (cantidadReintentos.intValue() > numeroMaximoReintentos) {
                LOGGER.info("No se realizan más reintentos #{} para la solicitud {}", (Object) cantidadReintentos, Optional.ofNullable(solicitud));
                return;
            }

            Map<String, Object> headersProductor = new HashMap<>();
            headersProductor.put(Constantes.REINTENTOS_HEADER, cantidadReintentos);
            headersProductor.put(Constantes.X_DELAY_HEADER, Constantes.TIEMPO_DELAY);

            RespuestaBroker respuestaBroker = productorMsgBrokerARL.publicarMensaje(solicitud,
                    Collections.unmodifiableMap(headersProductor));
            LOGGER.info("Respuesta publicando mensaje {} por error no controlado {}", solicitud, respuestaBroker);
        } catch (Exception e) {
            LOGGER.error("Error no controlado publicando el mensaje {} - {}", Optional.ofNullable(solicitud), ExceptionUtils.getStackTrace(e));
        }
    }

}
