package com.equipo.webapp.bar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.equipo.webapp.bar.system.Main;

import javafx.application.Application;

@SpringBootApplication
public class WebappApplication {

	public static void main(String[] args) {
		
		Application.launch(Main.class, args);
		SpringApplication.run(WebappApplication.class, args);
	}

}
