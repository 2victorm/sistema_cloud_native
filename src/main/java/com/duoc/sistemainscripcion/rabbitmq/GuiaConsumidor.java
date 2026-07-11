package com.duoc.sistemainscripcion.rabbitmq;

import com.duoc.sistemainscripcion.guias.model.GuiaDespacho;
import com.duoc.sistemainscripcion.guias.model.GuiaDespachoMensaje;
import com.duoc.sistemainscripcion.guias.repository.GuiaMensajeRepositorio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiaConsumidor {

    @Autowired
    private GuiaMensajeRepositorio guiaMensajeRepositorio;

    @RabbitListener(queues = RabbitMQConfig.COLA_GUIAS)
    public void recibirGuia(GuiaDespacho guia) {
        try {
            if ("ERROR".equals(guia.getNumeroGuia())) {
                throw new RuntimeException("Error simulado para prueba de cola-errores");
            }

            GuiaDespachoMensaje mensaje = new GuiaDespachoMensaje();
            mensaje.setNumeroGuia(guia.getNumeroGuia());
            mensaje.setTransportista(guia.getTransportista());
            mensaje.setOrigen(guia.getOrigen());
            mensaje.setDestino(guia.getDestino());
            mensaje.setDestinatario(guia.getDestinatario());
            mensaje.setEstado(guia.getEstado());
            mensaje.setFecha(guia.getFecha());

            guiaMensajeRepositorio.save(mensaje);
            System.out.println("Guía guardada en Oracle desde cola: " + guia.getNumeroGuia());
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de la cola: " + e.getMessage());
            throw e;
        }
    }
}