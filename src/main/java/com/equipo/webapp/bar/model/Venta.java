package com.equipo.webapp.bar.model;

import java.sql.Date;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> carlospriego-2020544

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaVenta;
    private Double total;
<<<<<<< HEAD
    @ManyToOne
    private Empleado empleado;
    @ManyToOne
    private Cliente cliente;
    @ManyToMany
    @JoinTable(name = "detalle_ventas_productos",
    joinColumns = @JoinColumn(name = "ventas_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private List<Producto> productos;

=======
>>>>>>> carlospriego-2020544
}
