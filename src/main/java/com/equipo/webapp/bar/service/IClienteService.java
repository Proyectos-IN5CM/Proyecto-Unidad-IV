package com.equipo.webapp.bar.service;

import java.util.List;

import com.equipo.webapp.bar.model.Cliente;

public interface IClienteService {

    public List<Cliente> listarClientes();

    public Cliente guardarCliente(Cliente cliente);

    public Cliente buscarClientePorDPI(Long dpi);

    public void eliminarCliente(Cliente cliente);
}
