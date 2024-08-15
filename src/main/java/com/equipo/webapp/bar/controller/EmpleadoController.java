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

import com.equipo.webapp.bar.model.Empleado;
import com.equipo.webapp.bar.service.EmpleadoService;

@Controller
@RestController
@RequestMapping("")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    
    @GetMapping("/empleados")
    public List<Empleado> listarEmpleados(){
       return empleadoService.listarEmpleado();
    }

    @GetMapping("/empleado")
       public ResponseEntity<Empleado>buscarEmpleadoPorId(@RequestParam Long id){
       try {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
        return ResponseEntity.ok(empleado);
       } catch (Exception e) {
        return ResponseEntity.badRequest().body(null);   
       
       }
    } 

    @PostMapping("/empleado")
    public ResponseEntity<Map<String, String>>agregarEmpleado(@RequestBody Empleado empleado){
        Map<String,String>response = new HashMap<>();
        try {
            empleadoService.guardarEmpleado(empleado);
            response.put("message", "Empleado creado con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
           response.put("message", "error");
           response.put("error", "Hubo un error al crear el empleado");
           return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/empleado")
    public ResponseEntity<Map<String, String>> editarEmpleado(@RequestParam Long id, @RequestBody Empleado empleadoNuevo){
        Map<String, String> response = new HashMap<>();
        
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            empleadoService.guardarEmpleado(empleado);
            response.put("message", "El Empleado se ha editado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al intentar editar el empleado!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("message", "Empleado borrado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "No se pudo borrar el empleado!");
            return ResponseEntity.badRequest().body(response);
        }
    }


}
