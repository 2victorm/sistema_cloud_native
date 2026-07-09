package com.duoc.sistemainscripcion.rabbitmq;

import com.duoc.sistemainscripcion.guias.model.GuiaDespacho;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiaProductor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publicarGuia(GuiaDespacho guia) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_GUIAS, guia);
            System.out.println("Guía publicada en cola-guias: " + guia.getNumeroGuia());
        } catch (Exception e) {
            System.err.println("Error al publicar guía, enviando a cola-errores: " + e.getMessage());
            rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_ERRORES, guia);
        }
    }
}