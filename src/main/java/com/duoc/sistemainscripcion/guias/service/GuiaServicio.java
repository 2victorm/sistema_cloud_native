package com.duoc.sistemainscripcion.guias.service;

import com.duoc.sistemainscripcion.guias.model.GuiaDespacho;
import com.duoc.sistemainscripcion.guias.repository.GuiaRepositorio;
import com.duoc.sistemainscripcion.rabbitmq.GuiaProductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GuiaServicio {

    @Autowired
    private GuiaRepositorio guiaRepositorio;

    @Autowired
    private GuiaProductor guiaProductor;

    public GuiaDespacho crear(GuiaDespacho guia) {
        guia.setFecha(LocalDate.now());
        guia.setEstado("PENDIENTE");
        GuiaDespacho guardada = guiaRepositorio.save(guia);
        guiaProductor.publicarGuia(guardada);
        return guardada;
    }

    public GuiaDespacho obtener(Long id) {
        return guiaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Guía no encontrada: " + id));
    }

    public GuiaDespacho modificar(Long id, GuiaDespacho datos) {
        GuiaDespacho guia = obtener(id);
        guia.setTransportista(datos.getTransportista());
        guia.setOrigen(datos.getOrigen());
        guia.setDestino(datos.getDestino());
        guia.setDestinatario(datos.getDestinatario());
        guia.setEstado(datos.getEstado());
        return guiaRepositorio.save(guia);
    }

    public void eliminar(Long id) {
        guiaRepositorio.deleteById(id);
    }

    public List<GuiaDespacho> consultarPorTransportistaYFecha(String transportista, LocalDate fecha) {
        return guiaRepositorio.findByTransportistaAndFecha(transportista, fecha);
    }
}