package com.equipo.webapp.bar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo.webapp.bar.model.Proveedor;
import com.equipo.webapp.bar.repository.ProveedorRepository;

@Service
public class ProveedorService implements IProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor buscarProveedorPorId(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorRepository.delete(proveedor);
    }

    
    public Boolean verificarTelefonoDuplicado(Proveedor proveedor) {
        List<Proveedor> proveedores = listarProveedores();

        for (Proveedor e : proveedores) {
            if (e.getTelefono().equals(proveedor.getTelefono()) && !e.getId().equals(proveedor.getId())) {
                return true;
            }
        }
        return false;
    }
}
