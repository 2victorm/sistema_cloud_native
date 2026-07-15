package com.duoc.sistemainscripcion.rabbitmq;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.model.InscripcionMensaje;
import com.duoc.sistemainscripcion.cursos.repository.InscripcionMensajeRepositorio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InscripcionConsumidor {

    @Autowired
    private InscripcionMensajeRepositorio inscripcionMensajeRepositorio;

    @RabbitListener(queues = RabbitMQConfig.COLA_GUIAS)
    public void recibirInscripcion(Inscripcion inscripcion) {
        try {
            if ("ERROR".equals(inscripcion.getCodigoInscripcion())) {
                throw new RuntimeException("Error simulado para prueba de cola-errores");
            }

            InscripcionMensaje mensaje = new InscripcionMensaje();
            mensaje.setCodigoInscripcion(inscripcion.getCodigoInscripcion());
            mensaje.setNombreEstudiante(inscripcion.getNombreEstudiante());
            mensaje.setEmailEstudiante(inscripcion.getEmailEstudiante());
            mensaje.setNombreCurso(inscripcion.getNombreCurso());
            mensaje.setInstructor(inscripcion.getInstructor());
            mensaje.setEstado(inscripcion.getEstado());
            mensaje.setFechaInscripcion(inscripcion.getFechaInscripcion());

            inscripcionMensajeRepositorio.save(mensaje);
            System.out.println("Inscripcion guardada desde cola: " + inscripcion.getCodigoInscripcion());
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de la cola: " + e.getMessage());
            throw e;
        }
    }
}
