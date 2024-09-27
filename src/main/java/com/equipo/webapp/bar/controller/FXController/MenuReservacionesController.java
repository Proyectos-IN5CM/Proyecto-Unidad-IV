package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale.Category;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.model.Reservacion;
import com.equipo.webapp.bar.repository.ReservacionRepository;
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
    TextField tfId, tfFecha, ftLugar, tfEstado, tfBuscarReservacion;
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

            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy"); // Date a String
            String strDate = sdf.format(reservacion.getFechaReservacion());

            tfId.setText(Long.toString(reservacion.getId()));
            taDescripcion.setText(reservacion.getDescripcion());
            tfEstado.setText(Boolean.toString(reservacion.getEstado()));
            tfFecha.setText(strDate); //Date convertido a String
            ftLugar.setText(reservacion.getLugarReservacion());
        }
    }

    public void limpiarTextField(){
        tfId.clear();
        tfFecha.clear();
        ftLugar.clear();
        taDescripcion.clear();
        tfEstado.clear();
    }

    public ObservableList<Reservacion>listarReservaciones(){
        return FXCollections.observableArrayList(reservacionService.listarReservaciones());
    }

    public void agregarReservacion(){
        Reservacion reservacion = new Reservacion();
        reservacion.setDescripcion(taDescripcion.getText());
        reservacion.setEstado(Boolean.parseBoolean(tfEstado.getText()));
        reservacion.setFechaReservacion(Date.valueOf(tfFecha.getText()));
        reservacion.setLugarReservacion(ftLugar.getText());
        reservacionService.guardarReservacion(reservacion);
        cargarDatos();
    }

    public void editarReservacion(){
        Reservacion reservacion = reservacionService.buscarReservacionPorId(Long.parseLong(tfId.getText()));
        reservacion.setDescripcion(taDescripcion.getText());
        reservacion.setEstado(Boolean.parseBoolean(tfEstado.getText()));
        reservacion.setFechaReservacion(Date.valueOf(tfFecha.getText()));
        reservacion.setLugarReservacion(ftLugar.getText());
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
