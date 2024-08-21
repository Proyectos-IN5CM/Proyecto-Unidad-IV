package com.equipo.webapp.bar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equipo.webapp.bar.model.Cliente;
import com.equipo.webapp.bar.service.ClienteService;

@Controller
@RestController
@RequestMapping("")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/cliente")
    public ResponseEntity<Cliente> buscarClientePorDPI(@RequestParam Long dpi){
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, String>> agregarCliente(@RequestBody Cliente cliente){
        Map<String, String> response = new HashMap<>();
        try {

            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                response.put("message", "Error!");
                response.put("error", "El nombre no puede estar vacío.");
                return ResponseEntity.badRequest().body(response);
            }  
            if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
                response.put("message", "Error!");
                response.put("error", "El teléfono no puede estar vacío.");
                return ResponseEntity.badRequest().body(response);
            }

            clienteService.guardarCliente(cliente);
            response.put("message", "Cliente creado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error!");
            response.put("err", "Hubo un error al crear el cliente!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cliente")
    public ResponseEntity<Map<String, String>> editarCliente(@RequestParam Long dpi, @RequestBody Cliente clienteNuevo){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);
            cliente.setNombre(clienteNuevo.getNombre());
            cliente.setApellido(clienteNuevo.getApellido());
            cliente.setTelefono(clienteNuevo.getTelefono());
            clienteService.guardarCliente(cliente);
            response.put("message", "El cliente se ha modificado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error!");
            response.put("err", "Hubo un error al intentar modificar el cliente!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, String>> eliminarCliente(@RequestParam Long dpi){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorDPI(dpi);
            clienteService.eliminarCliente(cliente);
            response.put("message", "El cliente se ha eliminado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error!");
            response.put("err", "Hubo un error al intentar eliminar el cliente!");
            return ResponseEntity.badRequest().body(response);
        }
    }
}