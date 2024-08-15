package com.equipo.webapp.bar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipo.webapp.bar.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
