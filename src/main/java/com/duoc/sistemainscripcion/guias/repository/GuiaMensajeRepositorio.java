package com.duoc.sistemainscripcion.guias.repository;

import com.duoc.sistemainscripcion.guias.model.GuiaDespachoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaMensajeRepositorio extends JpaRepository<GuiaDespachoMensaje, Long> {
}