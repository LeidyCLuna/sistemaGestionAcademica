package com.usura.sga.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constantes {

    /* RABBITMQ */
    public static final String X_DELAY_HEADER = "x-delay";
    public static final String REINTENTOS_HEADER = "reintentos-proc";
    public static final Integer TIEMPO_DELAY = 60 * 1000;

    /* PROCESOS LOGS */
    public static final String LOG_CONSUMIDOR_BROKER = "Consumidor Broker";
    public static final int NUM_REINTENTOS_EXCEPCION_NO_CONTROLADA = 2;

    /* TIME OUT */
    public static final int TIME_OUT_SEND_MSG_BROKER = 10;
}
