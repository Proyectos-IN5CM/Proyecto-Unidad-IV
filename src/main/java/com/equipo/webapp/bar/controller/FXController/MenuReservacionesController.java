package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Reservacion;
import com.equipo.webapp.bar.service.ReservacionService;
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
public class MenuReservacionesController implements Initializable{
    
    @Setter
    private Main stage;

    @FXML
    TextField tfId, tfFecha, tfLugar, tfEstado, tfBuscarReservacion;

    @FXML
    TextArea taDescripcion;

    @FXML
    TableView tblReservaciones;

    @FXML
    TableColumn colId, colFecha, colLugar, colDescripcion, colEstado;

    @FXML 
    Button btnGuardar, btnEliminar, btnBack, btnVaciar, btnDetalleReservaciones, btnBuscar;

    @Autowired
    ReservacionService reservacionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarReservacion();
            }else{
                editarReservacion();
            }
        }else if (event.getSource() == btnEliminar) {
            eliminarReservacion();
        }else if (event.getSource() == btnVaciar) {
            limpiarTextField();
        }else if (event.getSource() == btnBuscar) {
            buscarReservacion();
        }else if (event.getSource() == btnBack) {
            stage.menuPrincipalView();
        }else if (event.getSource() == btnDetalleReservaciones) {
            stage.menuDetalleReservacionesView();
        }
    }

    public void cargarDatos(){
        tblReservaciones.getItems().clear();
        tblReservaciones.setItems(listarReservaciones());
        colId.setCellValueFactory(new PropertyValueFactory<Reservacion, Long>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Reservacion, Date>("fechaReservacion"));
        colLugar.setCellValueFactory(new PropertyValueFactory<Reservacion, String>("lugarReservacion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Reservacion, String>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Reservacion, Boolean>("estado"));
    }

    public void cargarTextField(){
        Reservacion reservacion = (Reservacion)tblReservaciones.getSelectionModel().getSelectedItem();
        if (reservacion != null) {
            tfId.setText(Long.toString(reservacion.getId()));
            tfFecha.setText(reservacion.getFechaReservacion().toString());
            tfLugar.setText(reservacion.getLugarReservacion());
            taDescripcion.setText(reservacion.getDescripcion());
            tfEstado.setText(Boolean.toString(reservacion.getEstado()));
        }
    }

    public void limpiarTextField(){
        tfId.clear();
        tfFecha.clear();
        tfLugar.clear();
        taDescripcion.clear();
        tfEstado.clear();
        tfBuscarReservacion.clear();
    }

    public ObservableList<Reservacion>listarReservaciones(){
        return FXCollections.observableArrayList(reservacionService.listarReservaciones());
    }

    public void agregarReservacion(){
        Reservacion reservacion = new Reservacion();
        reservacion.setDescripcion(taDescripcion.getText());
        reservacion.setEstado(Boolean.parseBoolean(tfEstado.getText()));
        reservacion.setFechaReservacion(Date.valueOf(tfFecha.getText()));
        reservacion.setLugarReservacion(tfLugar.getText());
        reservacionService.guardarReservacion(reservacion);
        cargarDatos();
    }

    public void editarReservacion(){
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfId.getText()));
        reservacion.setDescripcion(taDescripcion.getText());
        reservacion.setEstado(Boolean.parseBoolean(tfEstado.getText()));
        reservacion.setFechaReservacion(Date.valueOf(tfFecha.getText()));
        reservacion.setLugarReservacion(tfLugar.getText());
        reservacionService.guardarReservacion(reservacion);
        cargarDatos();
    }

    public void eliminarReservacion(){
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfId.getText()));
        reservacionService.eliminarReservacion(reservacion);
        cargarDatos();
    }

    public void buscarReservacion() {
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfBuscarReservacion.getText()));
            ObservableList<Reservacion> busqueda = FXCollections.observableArrayList(reservacion);
            tblReservaciones.setItems(busqueda);
    }
}
