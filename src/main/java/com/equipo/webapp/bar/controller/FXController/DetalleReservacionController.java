package com.equipo.webapp.bar.controller.FXController;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;

import com.equipo.webapp.bar.model.Cliente;
import com.equipo.webapp.bar.model.Reservacion;
import com.equipo.webapp.bar.service.ReservacionService;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

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

public class DetalleReservacionController implements Initializable{
    @FXML
    TextField tfId, tfDpi, tfBuscarDetalleReservacion;
    @FXML
    TableView tblDetalleReservaciones;
    @FXML
    TableColumn colId, colDpi;
    @FXML
    Button btnBack, btnBuscar;

    @Autowired
    ReservacionService reservacionService;
    @Autowired
    Cliente cliente;

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
        }else if(event.getSource() == btnEliminar){
            eliminarReservacion();
        }
    }

    public void cargarDatos(){
        tblDetalleReservaciones.getItems().clear();
        tblDetalleReservaciones.setItems(listarReservaciones());
        colId.setCellValueFactory(new PropertyValueFactory<Reservacion, Long>("id"));
        colDpi.setCellValueFactory(new PropertyValueFactory<Reservacion, Int>("fechaReservacion"));
    }

    public void cargarTextField(){
        Reservacion reservacion = (Reservacion)tblReservaciones.getSelectionModel().getSelectedItem();
        if (reservacion != null) {
            tfId.setText(Long.toString(reservacion.getId()));
            tfDpi.setText(Integer.toString(cliente.getDpi()));
        }
    }

    public void limpiarTextField(){
        tfId.clear(); //No tiene metodo limpiar
        tfDpi.clear();
    }

    public ObservableList<Reservacion>listarReservaciones(){
        return FXCollections.observableArrayList(reservacionService.listarReservaciones());
    }

    public void agregarReservacion(){
        //No tiene metodo guardar
    }

    public void editarReservacion(){
        //No tiene metodo editar
    }
    
    //No tiene metodo Eliminar
    /*
    public void eliminarReservacion(){
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfId.getText()));
        reservacionService.eliminarReservacion(reservacion);
        cargarDatos();
    } */
}
