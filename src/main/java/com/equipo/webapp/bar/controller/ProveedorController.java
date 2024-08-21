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

import com.equipo.webapp.bar.model.Proveedor;
import com.equipo.webapp.bar.service.ProveedorService;

@Controller
@RestController
@RequestMapping("")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public List<Proveedor> listarProveedores(){
        return proveedorService.listarProveedores();
    }

    @GetMapping("/proveedor")
    public ResponseEntity<Proveedor> buscarProveedorPorId(@RequestParam Long id){
        try{
            Proveedor proveedor = proveedorService.buscarProveedorPorId(id);
            return ResponseEntity.ok(proveedor);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/proveedor")
public ResponseEntity<Map<String, String>> agregarProveedor(@RequestBody Proveedor proveedor) {
    Map<String, String> response = new HashMap<>();
    
        try {
            if (proveedorService.verificarTelefonoDuplicado(proveedor)) {
            response.put("message", "Error!");
            response.put("err", "El número de teléfono ya está registrado para otro proveedor!");
            return ResponseEntity.badRequest().body(response);
        }
        
            proveedorService.guardarProveedor(proveedor);
            response.put("message", "Proveedor creado con éxito!");
            return ResponseEntity.ok(response);
        
        } catch (Exception e) {
            response.put("message", "Error!");
            response.put("err", "Hubo un error al crear el proveedor!");
            return ResponseEntity.badRequest().body(response);
    }
}

@PutMapping("/proveedor")
public ResponseEntity<Map<String, String>> editarProveedor(@RequestParam Long id, @RequestBody Proveedor proveedorNuevo) {
    Map<String, String> response = new HashMap<>();
    
        try {
                Proveedor proveedorExistente = proveedorService.buscarProveedorPorId(id);
            if (proveedorExistente == null) {
                response.put("message", "Error!");
                response.put("err", "Proveedor no encontrado!");
                return ResponseEntity.notFound().build();
        }
        
            if (proveedorService.verificarTelefonoDuplicado(proveedorNuevo)) {
                response.put("message", "Error!");
                response.put("err", "El número de teléfono ya está registrado para otro proveedor!");
                return ResponseEntity.badRequest().body(response);
        }
        
            proveedorExistente.setNombre(proveedorNuevo.getNombre());
            proveedorExistente.setApellido(proveedorNuevo.getApellido());
            proveedorExistente.setTelefono(proveedorNuevo.getTelefono());
            proveedorExistente.setMarcaTrabajo(proveedorNuevo.getMarcaTrabajo());
        
            proveedorService.guardarProveedor(proveedorExistente);
        
            response.put("message", "El proveedor se ha modificado con éxito!");
        return ResponseEntity.ok(response);
        
        } catch (Exception e) {
            response.put("message", "Error!");
            response.put("err", "Hubo un error al intentar modificar el proveedor!");
            return ResponseEntity.badRequest().body(response);
    }
}


    @DeleteMapping("/proveedor")
    public ResponseEntity<Map<String, String>> eliminarProveedor(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        
        try {
            Proveedor proveedor = proveedorService.buscarProveedorPorId(id);
            proveedorService.eliminarProveedor(proveedor);
            response.put("message", "El proveedor se ha eliminado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "El proveedor no se ha eliminado con éxito!");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
