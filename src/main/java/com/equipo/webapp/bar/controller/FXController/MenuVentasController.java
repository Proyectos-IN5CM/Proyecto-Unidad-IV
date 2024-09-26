package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Venta;
import com.equipo.webapp.bar.model.Empleado;
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
    TextField tfId, tfTotal, tfFecha, tfBuscarVenta, tfEmpleado, tfCliente;

    @FXML
    Button btnGuardar, btnEliminar, btnVaciar, btnBuscar, btnBack;

    @FXML
    TableView<Venta> tblVentas;

    @FXML
    TableColumn<Venta, Long> colId;

    @FXML
    TableColumn<Venta, String> colFecha;

    @FXML
    TableColumn<Venta, Double> colTotal;

    @FXML
    TableColumn<Venta, String> colEmpleado;

    @FXML
    TableColumn<Venta, String> colCliente;

    @Autowired
    VentaService ventaService;

    @Autowired
    EmpleadoService empleadoService;  

    @Autowired
    ClienteService clienteService;  

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
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
    }

    public void cargarTextField() {
        Venta venta = tblVentas.getSelectionModel().getSelectedItem();
        if (venta != null) {
            tfId.setText(Long.toString(venta.getId()));
            tfFecha.setText(venta.getFechaVenta().toString());
            tfTotal.setText(Double.toString(venta.getTotal()));
            tfEmpleado.setText(Long.toString(venta.getEmpleado().getId()));  
            tfCliente.setText(Long.toString(venta.getCliente().getDpi()));  
        }
    }

    public void vaciarTextField() {
        tfId.clear();
        tfFecha.clear();
        tfTotal.clear();
        tfBuscarVenta.clear();
        tfEmpleado.clear(); 
        tfCliente.clear();
    }

    public ObservableList<Venta> listarVentas() {
        return FXCollections.observableArrayList(ventaService.listarVentas());
    }

    public void agregarVenta() {
        Venta venta = new Venta();
        venta.setFechaVenta(Date.valueOf(tfFecha.getText()));
        venta.setTotal(Double.parseDouble(tfTotal.getText()));

        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfEmpleado.getText()));
        venta.setEmpleado(empleado);

        Cliente cliente = clienteService.buscarClientePorDPI(Long.parseLong(tfCliente.getText()));
        venta.setCliente(cliente);

        ventaService.guardarVenta(venta);
        cargarDatos();
    }

    public void editarVenta() {
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfId.getText()));
        venta.setFechaVenta(Date.valueOf(tfFecha.getText()));
        venta.setTotal(Double.parseDouble(tfTotal.getText()));

        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfEmpleado.getText()));
        venta.setEmpleado(empleado);

        Cliente cliente = clienteService.buscarClientePorDPI(Long.parseLong(tfCliente.getText()));
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
