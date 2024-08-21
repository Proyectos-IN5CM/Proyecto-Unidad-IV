package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Empleado;

public interface IEmpleadoService {

    public List<Empleado>listarEmpleados();

    public Empleado buscarEmpleadoPorId(Long id);
    
    public Empleado guardarEmpleado (Empleado empleado);
    
    public void eliminarEmpleado (Empleado empleado);
    
}
