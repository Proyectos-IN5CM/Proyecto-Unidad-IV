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

import com.equipo.webapp.bar.model.Producto;
import com.equipo.webapp.bar.service.ProductoService;

@Controller
@RestController
@RequestMapping("")
@CrossOrigin(value = "http://127.0.0.1:5500")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> listarProductos(){
        return productoService.listarProductos();
    }

    @GetMapping("/producto")
    public ResponseEntity<Producto> buscarProductoPorId(@RequestParam Long id){
        try{
            Producto producto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/producto")
    public ResponseEntity<Map<String, String>> agregarProducto(@RequestBody Producto producto){
        Map<String, String> response = new HashMap<>();
        
        try { //Bien
            productoService.guardarProducto(producto);
            response.put("message", "Producto creado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) { //Mal
            response.put("message", "Error!");
            response.put("err", "Hubo un error al crear el producto!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/producto")
    public ResponseEntity<Map<String, String>> editarProducto(@RequestParam Long id, @RequestBody Producto productoNuevo){
        Map<String, String> response = new HashMap<>();
        
        try {
            Producto producto = productoService.buscarProductoPorId(id);
            producto.setNombreProducto(productoNuevo.getNombreProducto());
            producto.setTipoProducto(productoNuevo.getTipoProducto());
            producto.setDescripcion(productoNuevo.getDescripcion());
            producto.setPrecio(productoNuevo.getPrecio());
            productoService.guardarProducto(producto);
            response.put("message", "El producto se ha modificado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al intentar modificar el producto!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/producto")
    public ResponseEntity<Map<String, String>> eliminarProducto(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        
        try {
            Producto producto = productoService.buscarProductoPorId(id);
            productoService.eliminarProducto(producto);
            response.put("message", "El producto se ha eliminado con éxito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "El producto no se ha eliminado con éxito!");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
