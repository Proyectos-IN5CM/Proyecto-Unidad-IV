package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class MenuPrincipalController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    private MenuItem btnProductos, btnVentas;


    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnProductos){
            stage.menuProductosView(); 
        } else if(event.getSource() == btnVentas){
            stage.menuVentasView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
    }
}
