package com.usura.sga.msgbroker.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitColasConfig {

    @Bean("rutaSGA")
    public RabbitBindingRoute rutaSGA(
            @Value("${rabbitmq.routing.sga.exchange}")
            String exchange,
            @Value("${rabbitmq.routing.sga.routingKey}")
            String routingKey){
        return RabbitBindingRoute.builder().exchange(exchange).routingKey(routingKey).build();
    }
}
