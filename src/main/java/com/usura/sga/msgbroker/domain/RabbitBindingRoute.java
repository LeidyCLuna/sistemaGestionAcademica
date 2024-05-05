package com.usura.sga.msgbroker.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RabbitBindingRoute {
    private String routingKey;
    private String exchange;
}
