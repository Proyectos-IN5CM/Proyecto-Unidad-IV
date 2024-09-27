package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.service.ClienteService;
import com.equipo.webapp.bar.service.ReservacionService;
import com.equipo.webapp.bar.model.Reservacion;
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
public class DetalleReservacionController implements Initializable{

    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfDpi, tfBuscarDetalleReservacion, tfBuscar;
    @FXML
    Button btnBuscar, btnBack;
    @FXML
    TableView tblDetalleReservaciones;
    @FXML
    TableColumn colId, colDpi;

    @Autowired
    ReservacionService reservacionService;
    @Autowired
    ClienteService clienteService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnBuscar) {
            buscarVenta();
        }else if (event.getSource() == btnBack) {
            //stage.menuReservacionesView();
        }
    }

    public void cargarDatos() {
        tblDetalleReservaciones.getItems().clear();
        tblDetalleReservaciones.setItems(listarReservaciones());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
    }

    public ObservableList<Reservacion> listarReservaciones(){
        return FXCollections.observableArrayList(reservacionService.listarReservaciones());
    }

    public void buscarVenta() {
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfBuscar.getText()));
        ObservableList<Reservacion> busqueda = FXCollections.observableArrayList(reservacion);
        tblDetalleReservaciones.setItems(busqueda);
    }
}