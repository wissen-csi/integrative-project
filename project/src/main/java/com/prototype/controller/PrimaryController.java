package com.prototype.controller;

import java.io.IOException;

import com.prototype.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button crearequipo;

    @FXML
    private Button crearpersona;

    @FXML
    private Button crearproveedor;

    @FXML
    private Button primaryButton;

    @FXML
    void cambiarvistapersona(ActionEvent event) {

    }

    @FXML
    void cambiarvistaproveedor(ActionEvent event) {

    }

    @FXML
    void crearequipo(ActionEvent event) {

    }

    @FXML
    public void switchToSecondary() throws IOException, InterruptedException {

        App.setRoot("/com/prototype/secondary");

    }
}
