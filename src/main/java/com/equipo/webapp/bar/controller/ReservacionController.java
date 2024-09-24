package com.equipo.webapp.bar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equipo.webapp.bar.model.Reservacion;
import com.equipo.webapp.bar.service.ReservacionService;

@Controller
@RestController
@RequestMapping("")
@CrossOrigin(value = "http://127.0.0.1:5500")
public class ReservacionController {

    @Autowired
    ReservacionService reservacionService;

    @GetMapping("/reservaciones")
    public ResponseEntity<List<Reservacion>> listarReservaciones(){
        try {
            return ResponseEntity.ok(reservacionService.listarReservaciones());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/reservacion")
    public ResponseEntity<Reservacion> buscarReservaPorId(@RequestParam Long id){
        try{
            Reservacion reservacion = reservacionService.buscarReservacionPorId(id);
            return ResponseEntity.ok(reservacion);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/reservacion")
    public ResponseEntity<Map<String, String>> agregarReservacion(@RequestBody Reservacion reservacion){
        Map<String, String> response = new HashMap<>();
        
        try { 
            reservacionService.guardarReservacion(reservacion);
            response.put("message", "Reservacion creado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) { //Mal
            response.put("message", "Error!");
            response.put("err", "Hubo un error al crear el reservacion!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/reservacion")
    public ResponseEntity<Map<String, String>> editarReservacion(@RequestParam Long id, @RequestBody Reservacion reservacionNuevo){
        Map<String, String> response = new HashMap<>();
        
        try {
            Reservacion reservacion = reservacionService.buscarReservacionPorId(id);
            reservacion.setFechaReservacion(reservacionNuevo.getFechaReservacion());
            reservacion.setLugarReservacion(reservacionNuevo.getLugarReservacion());
            reservacion.setDescripcion(reservacionNuevo.getDescripcion());
            reservacion.setEstado(reservacionNuevo.getEstado());
            reservacion.setClientes(reservacionNuevo.getClientes());
            reservacionService.guardarReservacion(reservacion);
            response.put("message", "La reservacion se ha modificado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al intentar modificar la reservacion!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/reservacion")
    public ResponseEntity<Map<String, String>> eliminarReservacion(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        
        try {
            Reservacion reservacion = reservacionService.buscarReservacionPorId(id);
            reservacionService.eliminarReservacion(reservacion);
            response.put("message", "la reservacion se ha eliminado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "la reservacion no se ha eliminado con éxito!");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
