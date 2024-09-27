package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Producto;
import com.equipo.webapp.bar.service.ProductoService;
import com.equipo.webapp.bar.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class MenuProductosController implements Initializable{

    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfNombre, tfTipo, tfPrecio, tfBuscarProducto;

    @FXML
    TextArea taDescripcion;

    @FXML
    Button btnGuardar, btnEliminar, btnVaciar, btnBuscar, btnBack;

    @FXML
    TableView tblProductos;

    @FXML
    TableColumn colId, colNombre, colTipo, colDescripcion, colPrecio;

    @Autowired
    ProductoService productoService;

    // se ejecuta cada vez que inicio
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if (tfId.getText().isBlank()) {
                agregarProducto();
            }else{
                editarProducto();
            }
            }else if (event.getSource() == btnEliminar) {
                eliminarProducto();
            }else if (event.getSource() == btnVaciar) {
                vaciarTextField();
            }else if (event.getSource() == btnBuscar) {
                buscarProducto();
            }else if (event.getSource() == btnBack) {
                stage.menuPrincipalView();
            }
    }

    // llena el tableView
    public void cargarDatos(){
        tblProductos.getItems().clear();
        tblProductos.setItems(listarProductos());
        colId.setCellValueFactory(new PropertyValueFactory<Producto, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<Producto, String>("tipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
    }

    public void cargarTextField(){
        Producto producto =  (Producto)tblProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            tfId.setText(Long.toString(producto.getId()));
            tfNombre.setText(producto.getNombreProducto());
            tfTipo.setText(producto.getTipoProducto());
            taDescripcion.setText(producto.getDescripcion());
            tfPrecio.setText(Double.toString(producto.getPrecio()));
        }
    }

    public void vaciarTextField(){
        tfId.clear();
        tfNombre.clear();
        tfTipo.clear();
        taDescripcion.clear();
        tfPrecio.clear();
        tfBuscarProducto.clear();
    }

    public ObservableList<Producto> listarProductos(){
        return FXCollections.observableArrayList(productoService.listarProductos());
    }

    public void agregarProducto(){
        Producto producto = new Producto();
        producto.setNombreProducto(tfNombre.getText());
        producto.setTipoProducto(tfTipo.getText());
        producto.setDescripcion(taDescripcion.getText());
        producto.setPrecio(Double.parseDouble(tfPrecio.getText()));
        productoService.guardarProducto(producto);
        cargarDatos();
    }

    public void editarProducto(){
        Producto producto = productoService.buscarProductoPorId(Long.parseLong(tfId.getText()));
        producto.setNombreProducto(tfNombre.getText());
        producto.setTipoProducto(tfTipo.getText());
        producto.setDescripcion(taDescripcion.getText());
        producto.setPrecio(Double.parseDouble(tfPrecio.getText()));
        productoService.guardarProducto(producto);
        cargarDatos();
    }

    public void eliminarProducto(){
        Producto producto = productoService.buscarProductoPorId(Long.parseLong(tfId.getText()));
        productoService.eliminarProducto(producto);
        cargarDatos();
    }

    public void buscarProducto() {
        Producto producto = productoService.buscarProductoPorId(Long.parseLong(tfBuscarProducto.getText()));
            ObservableList<Producto> busqueda = FXCollections.observableArrayList(producto);
            tblProductos.setItems(busqueda);
    }
    
}
