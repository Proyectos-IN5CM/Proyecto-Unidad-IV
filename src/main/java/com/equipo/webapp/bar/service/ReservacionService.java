package com.equipo.webapp.bar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo.webapp.bar.model.Reservacion;
import com.equipo.webapp.bar.repository.ReservacionRepository;


@Service
public class ReservacionService implements IReservacionService {

    @Autowired
    ReservacionRepository reservacionRepository;
   
    @Override
    public List<Reservacion> listarReservaciones() {
        return reservacionRepository.findAll();
       
    }

    @Override
    public Reservacion guardarReservacion(Reservacion reservacion) {
        return reservacionRepository.save(reservacion);
    }

    @Override
    public Reservacion buscarReservacionPorId(Long id) {
        return reservacionRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarReservacion(Reservacion reservacion) {
        reservacionRepository.delete(reservacion);

    }



 

}
