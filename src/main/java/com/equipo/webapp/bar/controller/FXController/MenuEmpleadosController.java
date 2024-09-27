package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Empleado;
import com.equipo.webapp.bar.service.EmpleadoService;
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
public class MenuEmpleadosController implements Initializable {
    @Setter
    private Main stage;
    
    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfBuscarEmpleado;
   
    @FXML
    TableView tblEmpleados;
    
    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono;
    
    @FXML
    Button btnBack, btnGuardar, btnEliminar, btnVaciar, btnBuscar;
   
    @Autowired
    EmpleadoService empleadoService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos(); 
    }
   
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if (tfId.getText().isBlank()) {
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        }else if (event.getSource() == btnEliminar) {
            eliminarEmplado();
        }else if (event.getSource() == btnVaciar) {
            vaciarTextField();
        }else if (event.getSource() == btnBuscar) {
            buscarEmpleado();
        }else if (event.getSource() == btnBack) {
            stage.menuPrincipalView();
        }
    }

    public void cargarDatos(){
        tblEmpleados.getItems().clear();
        tblEmpleados.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado,Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
    }
     
    public void cargarTextField(){
        Empleado empleado =  (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            tfId.setText(Long.toString(empleado.getId()));
            tfNombre.setText(empleado.getNombre());
            tfApellido.setText(empleado.getApellido());
            tfTelefono.setText(empleado.getTelefono());
        }
    }

    
    public void vaciarTextField(){
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfBuscarEmpleado.clear();
    }

    public ObservableList<Empleado> listarEmpleados(){
        return FXCollections.observableArrayList(empleadoService.listarEmpleados());
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado() {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
            empleado.setNombre(tfNombre.getText());
            empleado.setApellido(tfApellido.getText());
            empleado.setTelefono(tfTelefono.getText());
            empleadoService.guardarEmpleado(empleado);
            cargarDatos();
        
    }
    public void eliminarEmplado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

    public void buscarEmpleado() {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscarEmpleado.getText()));
            ObservableList<Empleado> busqueda = FXCollections.observableArrayList(empleado);
            tblEmpleados.setItems(busqueda);
    }
    

}

