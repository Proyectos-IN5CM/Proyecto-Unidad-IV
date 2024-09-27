package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Venta;
import com.equipo.webapp.bar.model.Producto;
import com.equipo.webapp.bar.service.VentaService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class MenuDetalleVentasController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfBuscar;
    @FXML
    Button btnBuscar, btnBack;
    @FXML
    TableView tblDetalleVentas;
    @FXML
    TableColumn colId, colIdProducto;

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoService productoService;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBuscar) {
            buscarVenta();
        } else if (event.getSource() == btnBack) {
            stage.menuVentasView();
        }
    }

    public void cargarDatos() {
        tblDetalleVentas.getItems().clear();
        tblDetalleVentas.setItems(listarVentas());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("producto.nombre")); // Asumiendo que Producto tiene un atributo nombre
    }

    public ObservableList<Venta> listarVentas() {
        return FXCollections.observableArrayList(ventaService.listarVentas());
    }

    public void buscarVenta() {
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfBuscar.getText()));
        ObservableList<Venta> busqueda = FXCollections.observableArrayList(venta);
        tblDetalleVentas.setItems(busqueda);
    }
}
