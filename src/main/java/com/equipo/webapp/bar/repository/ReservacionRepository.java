package com.equipo.webapp.bar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipo.webapp.bar.model.Reservacion;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

}
