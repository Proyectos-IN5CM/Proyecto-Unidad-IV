package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Producto;

public interface IProductoService {

    public List<Producto> listarProductos();

    public Producto guardarProducto(Producto producto);

    public Producto buscarProductoPorId(Long id);

    public void eliminarProducto(Producto producto);
}
