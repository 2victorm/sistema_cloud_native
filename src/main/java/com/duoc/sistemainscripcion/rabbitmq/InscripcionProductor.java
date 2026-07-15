package com.duoc.sistemainscripcion.rabbitmq;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InscripcionProductor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publicarInscripcion(Inscripcion inscripcion) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_GUIAS, inscripcion);
            System.out.println("Inscripcion publicada en cola-inscripciones: " + inscripcion.getCodigoInscripcion());
        } catch (Exception e) {
            System.err.println("Error al publicar inscripcion, enviando a cola-errores: " + e.getMessage());
            rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_ERRORES, inscripcion);
        }
    }
}
