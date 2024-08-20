package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Venta;

public interface IVentaService {
    public List<Venta> listarVentas();

    public Venta guardarVenta(Venta venta);

    public Venta buscarVentaPorId(Long id);

    public void eliminarVenta(Venta venta);
}
