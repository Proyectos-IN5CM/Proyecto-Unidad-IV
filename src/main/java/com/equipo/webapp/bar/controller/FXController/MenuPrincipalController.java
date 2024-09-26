package com.equipo.webapp.bar.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.equipo.webapp.bar.system.Main;


import javafx.fxml.Initializable;
import lombok.Setter;

@Component
public class MenuPrincipalController implements Initializable {

    @Setter
    private Main stage;

 
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        
    }
}
