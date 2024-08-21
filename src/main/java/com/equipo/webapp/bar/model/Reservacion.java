package com.equipo.webapp.bar.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Reservaciones")
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaReservacion;
    private String lugarReservacion;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private Boolean estado;
    @ManyToMany
    @JoinTable(name = "detalle_reservaciones_clientes",
    joinColumns = @JoinColumn(name = "reservaciones_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "clientes_dpi", referencedColumnName = "dpi"))
    private List<Cliente> clientes;

}
