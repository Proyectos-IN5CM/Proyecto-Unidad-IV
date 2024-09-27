package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Venta;
import com.equipo.webapp.bar.model.Empleado;
import com.equipo.webapp.bar.model.Producto;
import com.equipo.webapp.bar.model.Cliente;
import com.equipo.webapp.bar.service.VentaService;
import com.equipo.webapp.bar.service.EmpleadoService;
import com.equipo.webapp.bar.service.ClienteService;
import com.equipo.webapp.bar.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class MenuVentasController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfTotal, tfFecha, tfBuscarVenta;
    @FXML
    Button btnGuardar, btnEliminar, btnVaciar, btnBuscar, btnBack, btnDetalleVentas;
    @FXML
    TableView tblVentas;
    @FXML
    TableColumn colId, colFecha, colTotal, colEmpleado, colCliente;
    @FXML
    ComboBox cmbEmpleado, cmbCliente;

    @Autowired
     VentaService ventaService;
    @Autowired
     EmpleadoService empleadoService;
    @Autowired
     ClienteService clienteService;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cargarComboBoxes();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarVenta();
            } else {
                editarVenta();
            }
        } else if (event.getSource() == btnEliminar) {
            eliminarVenta();
        } else if (event.getSource() == btnVaciar) {
            vaciarTextField();
        } else if (event.getSource() == btnBuscar) {
            buscarVenta();
        } else if (event.getSource() == btnDetalleVentas) {
            stage.menuDetalleVentasView();
        } else if (event.getSource() == btnBack) {
            stage.menuPrincipalView();
        }
    }

    public void cargarDatos() {
        tblVentas.getItems().clear();
        tblVentas.setItems(listarVentas());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    }

    public void cargarComboBoxes() {
        ObservableList<Empleado> empleados = FXCollections.observableArrayList(empleadoService.listarEmpleados());
        cmbEmpleado.setItems(empleados);

        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteService.listarClientes());
        cmbCliente.setItems(clientes);
    }

    public void cargarTextField(){
        Venta venta =  (Venta)tblVentas.getSelectionModel().getSelectedItem();
        if (venta != null) {
            tfId.setText(Long.toString(venta.getId()));
            tfFecha.setText(venta.getFechaVenta().toString());
            tfTotal.setText(Double.toString(venta.getTotal()));
            cmbCliente.getSelectionModel().select(venta.getCliente());
            cmbEmpleado.getSelectionModel().select(venta.getEmpleado());
        }
    }

    public void vaciarTextField() {
        tfId.clear();
        tfFecha.clear();
        tfTotal.clear();
        tfBuscarVenta.clear();
        cmbEmpleado.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
    }

    public ObservableList<Venta> listarVentas() {
        return FXCollections.observableArrayList(ventaService.listarVentas());
    }

    public void agregarVenta() {
        Venta venta = new Venta();
        venta.setFechaVenta(Date.valueOf(tfFecha.getText()));
        venta.setTotal(Double.parseDouble(tfTotal.getText()));

        Empleado empleado = (Empleado) cmbEmpleado.getSelectionModel().getSelectedItem();
        venta.setEmpleado(empleado);

        Cliente cliente = (Cliente) cmbCliente.getSelectionModel().getSelectedItem();
        venta.setCliente(cliente);

        ventaService.guardarVenta(venta);
        cargarDatos();
    }

    public void editarVenta() {
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfId.getText()));
        venta.setFechaVenta(Date.valueOf(tfFecha.getText()));
        venta.setTotal(Double.parseDouble(tfTotal.getText()));

        Empleado empleado = (Empleado) cmbEmpleado.getSelectionModel().getSelectedItem();
        venta.setEmpleado(empleado);

        Cliente cliente = (Cliente) cmbCliente.getSelectionModel().getSelectedItem();
        venta.setCliente(cliente);

        ventaService.guardarVenta(venta);
        cargarDatos();
    }

    public void eliminarVenta() {
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfId.getText()));
        ventaService.eliminarVenta(venta);
        cargarDatos();
    }

    public void buscarVenta() {
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfBuscarVenta.getText()));
        ObservableList<Venta> busqueda = FXCollections.observableArrayList(venta);
        tblVentas.setItems(busqueda);
    }
}
