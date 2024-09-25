package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Cliente;
import com.equipo.webapp.bar.service.ClienteService;
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
public class MenuClientesController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    TextField tfDpi, tfNombre, tfApellido, tfTelefono, tfBuscarCliente;

    @FXML
    TableView tblClientes;

    @FXML
    TableColumn colDpi, colNombre, colApellido, colTelefono;

    @FXML
    Button btnBack, btnGuardar, btnEliminar, btnVaciar, btnBuscar;

    @Autowired
    ClienteService clienteService;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if (tfDpi.getText().isBlank()){
                agregarCliente();
            }else{
                editarCliente();
            }
        }else if (event.getSource() == btnEliminar){
            eliminarCliente();
        }else if (event.getSource() == btnVaciar){
            vaciarTextField();
        }else if (event.getSource() == btnBuscar){
            buscarCliente();
        }else if (event.getSource() == btnBack){
            stage.menuPrincipalView();
        }
    }

    public void cargarDatos(){
        tblClientes.getItems().clear();
        tblClientes.setItems(listarClientes());
        colDpi.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
    }

    public void cargarTextField(){
        Cliente cliente = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            tfDpi.setText(Long.toString(cliente.getDpi()));
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellido());
            tfTelefono.setText(cliente.getTelefono());
        }
    }

    public void vaciarTextField(){
        tfDpi.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfBuscarCliente.clear();
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableArrayList(clienteService.listarClientes());
    }

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorDPI(Long.parseLong(tfDpi.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorDPI(Long.parseLong(tfDpi.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }

    public void buscarCliente(){
        Cliente cliente = clienteService.buscarClientePorDPI(Long.parseLong(tfBuscarCliente.getText()));
        ObservableList<Cliente> busqueda = FXCollections.observableArrayList(cliente);
        tblClientes.setItems(busqueda);
    }

}