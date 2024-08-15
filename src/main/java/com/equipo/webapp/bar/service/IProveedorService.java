package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Proveedor;

public interface IProveedorService {

    public List<Proveedor> listarProveedores();

    public Proveedor guardarProveedor(Proveedor proveedor);

    public Proveedor buscarProveedorPorId(Long id);

    public void eliminarProveedor(Proveedor proveedor);

}
