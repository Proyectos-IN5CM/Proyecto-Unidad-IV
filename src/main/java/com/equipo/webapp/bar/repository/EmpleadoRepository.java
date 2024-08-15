package com.equipo.webapp.bar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipo.webapp.bar.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}