package com.prototype.controller;



import com.prototype.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class MainMenuController {

    @FXML
    private BorderPane mainMenuBorderPane;

    @FXML
    private void loadPersonsView() {
        this.loadView("person-view");
    }

    @FXML
    private void loadProviderView() {
        this.loadView("provider-view");
    }

    @FXML
    private void loadEquipmentView() {
        this.loadView("equipment-view");
    }

    @FXML
    private void loadRequestView() {
        this.loadView("entryrequest-view");
    }

    @FXML
    private void loadExitFunction() {
        System.exit(0);
    }

    /**
     * Método genérico para cargar un archivo FXML en el área central.
     * O sea, que dependiendo del boton que se elija, se carga una vista diferente.
     * 
     * @param fxmlName El nombre del archivo FXML (sin la extensión .fxml)
     */
    private void loadView(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlName + ".fxml"));
            Parent view = loader.load();
            this.mainMenuBorderPane.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error al Cargar", "No se pudo cargar la vista: " + fxmlName + ".fxml");
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
