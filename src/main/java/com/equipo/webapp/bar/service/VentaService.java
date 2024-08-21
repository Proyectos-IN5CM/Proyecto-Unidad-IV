package com.equipo.webapp.bar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo.webapp.bar.model.Empleado;
import com.equipo.webapp.bar.model.Venta;
import com.equipo.webapp.bar.repository.VentaRepository;

@Service
public class VentaService implements IVentaService{

    @Autowired
    VentaRepository ventaRepository;

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarVenta(Venta venta) {
        ventaRepository.delete(venta);
    }

    @Override
    public Boolean verificarTotal() {
        Boolean flag = Boolean.FALSE;
        List<Venta> ventas = listarVentas();

        for (Venta v : ventas) {
            if (v.getTotal() <= 0) {
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }
}