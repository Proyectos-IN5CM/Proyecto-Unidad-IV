package com.equipo.webapp.bar.system;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.equipo.webapp.bar.WebappApplication;
import com.equipo.webapp.bar.controller.FXController.MenuEmpleadosController;
import com.equipo.webapp.bar.controller.FXController.MenuPrincipalController;
import com.equipo.webapp.bar.controller.FXController.MenuProductosController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    // atributos
    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    // se ejecuta cada vez que instancio la clase main
    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(WebappApplication.class).run();
    }

    // se ejecuta al iniciar la aplicación de javafx
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Barr - Ondo | ¿Qué pasó ayer?");
        // carga la escena principal
        menuPrincipalView();
        stage.show();
    }

    public Initializable cambiarEscena(String fxmlName, int with, int height) throws IOException{
        Initializable initializable = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext::getBean);
        InputStream archivo = Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane)loader.load(archivo), with, height);
        stage.setScene(scene);
        stage.sizeToScene();

        initializable = (Initializable)loader.getController();

        return initializable;
    }

    public void menuPrincipalView(){
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 900, 625);
            menuPrincipalView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProductosView(){
        try {
            MenuProductosController menuProductosView = (MenuProductosController)cambiarEscena("MenuProductosView.fxml", 900, 625);
            menuProductosView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
    public void menuEmpleadosView(){
        try {
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController)cambiarEscena("MenuEmpleadosView.fxml", 900, 625);
            menuEmpleadosView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
}
