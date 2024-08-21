package com.equipo.webapp.bar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo.webapp.bar.model.Cliente;
import com.equipo.webapp.bar.repository.ClienteRepository;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarClientePorDPI(Long dpi) {
        return clienteRepository.findById(dpi).orElse(null);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

     @Override
    public Boolean verificarDpiDuplicado(Cliente cliente) {
        Boolean flag = Boolean.FALSE;
        List<Cliente> clientes = listarClientes();

        for (Cliente e : clientes) {
            if (e.getDpi() == cliente.getDpi() && !e.equals(cliente)) {
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }


}