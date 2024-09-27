package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Proveedor;
import com.equipo.webapp.bar.service.ProveedorService;
import com.equipo.webapp.bar.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class MenuProveedoresController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfTrabajo, tfBuscarProveedor;

    @FXML
    Button btnGuardar, btnEliminar, btnVaciar, btnBuscar, btnBack;

    @FXML
    TableView tblProveedores;

    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono, colTrabajo;

    @Autowired
    ProveedorService proveedorService;

    // se ejecuta cada vez que inicio
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if (tfId.getText().isBlank()) {
                agregarProveedor();
            }else{
                editarProveedor();
            }
        }else if (event.getSource() == btnEliminar) {
            eliminarProveedor();
        }else if (event.getSource() == btnVaciar) {
            vaciarTextField();
        }else if (event.getSource() == btnBuscar) {
            buscarProveedor();
        }else if (event.getSource() == btnBack) {
            stage.menuPrincipalView();
        }
    }

    // llena el tableView
    public void cargarDatos(){
        tblProveedores.getItems().clear();
        tblProveedores.setItems(listarProveedores());
        colId.setCellValueFactory(new PropertyValueFactory<Proveedor, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("telefono"));
        colTrabajo.setCellValueFactory(new PropertyValueFactory<Proveedor, Double>("marcaTrabajo"));
    }

    public void cargarTextField(){
        Proveedor proveedor =  (Proveedor)tblProveedores.getSelectionModel().getSelectedItem();
        if (proveedor != null) {
            tfId.setText(Long.toString(proveedor.getId()));
            tfNombre.setText(proveedor.getNombre());
            tfApellido.setText(proveedor.getApellido());
            tfTelefono.setText(proveedor.getTelefono());
            tfTrabajo.setText(proveedor.getMarcaTrabajo());
        }
    }

    public void vaciarTextField(){
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfTrabajo.clear();
        tfBuscarProveedor.clear();
    }

    public ObservableList<Proveedor> listarProveedores(){
        return FXCollections.observableArrayList(proveedorService.listarProveedores());
    }

    public void agregarProveedor(){
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(tfNombre.getText());
        proveedor.setApellido(tfApellido.getText());
        proveedor.setTelefono(tfTelefono.getText());
        proveedor.setMarcaTrabajo(tfTrabajo.getText());
        proveedorService.guardarProveedor(proveedor);
        cargarDatos();
    }

    public void editarProveedor(){
        Proveedor proveedor = proveedorService.buscarProveedorPorId(Long.parseLong(tfId.getText()));
        proveedor.setNombre(tfNombre.getText());
        proveedor.setApellido(tfApellido.getText());
        proveedor.setTelefono(tfTelefono.getText());
        proveedor.setMarcaTrabajo(tfTrabajo.getText());
        proveedorService.guardarProveedor(proveedor);
        cargarDatos();
    }

    public void eliminarProveedor(){
        Proveedor proveedor = proveedorService.buscarProveedorPorId(Long.parseLong(tfId.getText()));
        proveedorService.eliminarProveedor(proveedor);
        cargarDatos();
    }

    public void buscarProveedor() {
        Proveedor proveedor = proveedorService.buscarProveedorPorId(Long.parseLong(tfBuscarProveedor.getText()));
            ObservableList<Proveedor> busqueda = FXCollections.observableArrayList(proveedor);
            tblProveedores.setItems(busqueda);
    }
}
