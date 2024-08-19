package com.equipo.webapp.bar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipo.webapp.bar.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}