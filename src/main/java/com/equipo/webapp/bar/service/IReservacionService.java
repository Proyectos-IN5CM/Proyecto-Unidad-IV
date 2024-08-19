package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Reservacion;

public interface IReservacionService {

    public List<Reservacion> listarReservaciones();

    public Reservacion guardarReservacion(Reservacion Reservacion);

    public Reservacion buscarReservacionPorId(Long id);

    public void eliminarReservacion(Reservacion Reservacion);
}
