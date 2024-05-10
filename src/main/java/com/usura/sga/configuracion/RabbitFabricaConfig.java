package com.usura.sga.configuracion;


import com.usura.sga.msgbroker.domain.RabbitConexionProps;
import com.usura.sga.msgbroker.event.BrokerEvento;
import com.usura.sga.msgbroker.event.ErrorBrokerEvento;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Data
@Configuration
@EnableRabbit
public class RabbitFabricaConfig {

    private final ApplicationEventPublisher publisher;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitFabricaConfig.class);

    private static final String CONSUMER_NAME = "nombreconsumidor";

    @Bean("conexionRabbit")
    public ConnectionFactory conexionRabbitDiscodia(@Qualifier("rabbitConexionProps") RabbitConexionProps rabbitConexionProps) {
        return fabricaConexionRabbit(rabbitConexionProps);
    }

    @Bean("productorRabbit")
    public RabbitTemplate productorRabbitDiscordia(@Qualifier("conexionRabbit") ConnectionFactory connectionFactory) {
        return fabricaProductorRabbit(connectionFactory, jsonMessageConverter());
    }

    @Bean("listenerRabbit")
    public SimpleRabbitListenerContainerFactory listenerRabbitDiscodia(@Qualifier("conexionRabbit") ConnectionFactory connectionFactory) {
        return hospSyncFactory(connectionFactory, jsonMessageConverter());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public ConnectionFactory fabricaConexionRabbit(RabbitConexionProps rabbitConexionProps) {
        com.rabbitmq.client.ConnectionFactory fabrica = new com.rabbitmq.client.ConnectionFactory();
        fabrica.setHost(rabbitConexionProps.getHost());
        fabrica.setPort(rabbitConexionProps.getPort());
        fabrica.setUsername(rabbitConexionProps.getUsername());
        fabrica.setPassword(rabbitConexionProps.getPassword());
        fabrica.setVirtualHost(rabbitConexionProps.getVirtualHost());
        fabrica.setAutomaticRecoveryEnabled(false);
        fabrica.setConnectionTimeout(10000);

        try {
            if (rabbitConexionProps.isSslenabled()) fabrica.useSslProtocol();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            LOG.error("{}: Error creando la conexion con rabbit sslProtocol, Error: {}", rabbitConexionProps.getBrokerTag(), e.getMessage());
        }
        CachingConnectionFactory fabricaConexionRabbit = new CachingConnectionFactory(fabrica);
        fabricaConexionRabbit.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        fabricaConexionRabbit.setPublisherReturns(true);


        return fabricaConexionRabbit;
    }

    public RabbitTemplate fabricaProductorRabbit(final ConnectionFactory connectionFactory, AbstractJackson2MessageConverter messageConverter) {
        final RabbitTemplate fabricaProductor = new RabbitTemplate(connectionFactory);

        //formato del mensaje que se publicara (JSON,XML,etc)
        fabricaProductor.setMessageConverter(messageConverter);

        //confirmar si el mesaje fue recibido correctamente
        fabricaProductor.setMandatory(true);

        // Callback para identificar errores
        fabricaProductor.setReturnsCallback(returnedMessage -> {
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();

            ErrorBrokerEvento errorEvent = new ErrorBrokerEvento(message, replyCode, replyText, exchange, routingKey);
            publisher.publishEvent(new BrokerEvento(errorEvent));
        });
        return fabricaProductor;

    }

    public SimpleRabbitListenerContainerFactory hospSyncFactory(ConnectionFactory connectionFactory, AbstractJackson2MessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(messageConverter);
        factory.setConnectionFactory(connectionFactory);

        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        factory.setPrefetchCount(1);

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);

        factory.setMissingQueuesFatal(false);
        factory.setConsumerTagStrategy(
                q -> CONSUMER_NAME.concat(".").concat(System.getProperty("user.name")));

        return factory;


    }

    @Bean
    public SimpleRabbitListenerContainerFactory fabricaConsumidorRabbit(ConnectionFactory connectionFactory,
                                                                        AbstractJackson2MessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory fabricaConsumidor = new SimpleRabbitListenerContainerFactory();
        fabricaConsumidor.setConnectionFactory(connectionFactory);

        // Formato del mensaje que se consumira (JSON, XML, etc)
        fabricaConsumidor.setMessageConverter(messageConverter);

        // Modo para confirmar la recepcion de los mensajes (AUTO sera la librera la que
        // controlara la logica)
        fabricaConsumidor.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        // Desencolar mensajes uno a uno
        fabricaConsumidor.setPrefetchCount(1);

        // Consumidores simultaneos
        fabricaConsumidor.setConcurrentConsumers(1);
        fabricaConsumidor.setMaxConcurrentConsumers(1);

        // No eliminar el listenner cuando se presente un fallo o problema de conexion
        fabricaConsumidor.setMissingQueuesFatal(false);

        // Nombre consumidor
        /*fabricaConsumidor.setConsumerTagStrategy(
                q -> RabbitConexionProps.getConsumerName().concat(".").concat(System.getProperty("user.name")));
*/
        return fabricaConsumidor;
    }


}
