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

import com.equipo.webapp.bar.model.Venta;
import com.equipo.webapp.bar.service.VentaService;

@Controller
@RestController
@RequestMapping("")
@CrossOrigin(value = "http://127.0.0.1:5500")
public class VentaController {

    @Autowired
    VentaService ventaService;

    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> listarVentas(){
        try {
            return ResponseEntity.ok(ventaService.listarVentas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/venta")
    public ResponseEntity<Venta> buscarVentaPorId(@RequestParam Long id){
        try {
            Venta venta = ventaService.buscarVentaPorId(id);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/venta")
    public ResponseEntity<Map<String, String>> agregarVenta(@RequestBody Venta venta){
        Map<String, String> response = new HashMap<>();
        try { //Bien
            if (!ventaService.verificarTotal()) {
                ventaService.guardarVenta(venta);
                response.put("message", "Venta creada con éxito!");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Error");
                response.put("err", "El total se encuentra no puede ser negativo!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) { //Mal
            response.put("message", "Error!");
            response.put("err", "Hubo un error al crear la venta!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/venta")
    public ResponseEntity<Map<String, String>> editarVenta(@RequestParam Long id, @RequestBody Venta ventaNueva){
        Map<String, String> response = new HashMap<>();

        Venta venta = ventaService.buscarVentaPorId(id);
        venta.setFechaVenta(ventaNueva.getFechaVenta());
        venta.setTotal(ventaNueva.getTotal());
        venta.setEmpleado(ventaNueva.getEmpleado());
        venta.setCliente(ventaNueva.getCliente());
        venta.setProductos(ventaNueva.getProductos());
        
        try {
            if (!ventaService.verificarTotal()) {
                ventaService.guardarVenta(venta);
                response.put("message", "La venta se ha modificado con éxito!");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Error");
                response.put("err", "El total se encuentra nulo!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al intentar modificar la venta!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/venta")
    public ResponseEntity<Map<String, String>> eliminarVenta(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Venta venta = ventaService.buscarVentaPorId(id);
            ventaService.eliminarVenta(venta);
            response.put("message", "Venta eliminada con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "No se pudo eliminar la venta");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
