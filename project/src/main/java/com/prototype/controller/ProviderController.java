
package com.prototype.controller;


import java.util.List;


import com.prototype.model.entities.Provider;
import com.prototype.services.ProviderService;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ProviderController {

    // ================== CAMPOS DEL FORMULARIO ==================

    @FXML
    private TextField idTextField; // ID

    @FXML
    private TextField providerNameTextField; // Nombre proveedor

    @FXML
    private TextField taxIdTextField; // Identificador tributario

    @FXML
    private TextField contactEmailTextField; // Email de contacto

    @FXML
    private TextField addressTextField; // Dirección

    @FXML
    private Label statusLabel;

    // ================== BOTONES ==================

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button findByIdButton;

    @FXML
    private Button cleanFormButton;

    // ================== TABLA ==================

    @FXML
    private TableView<Provider> providerTable;

    @FXML
    private TableColumn<Provider, Long> idColumn;

    @FXML
    private TableColumn<Provider, String> nameProviderColumn;

    @FXML
    private TableColumn<Provider, String> tributaryIdColumn;

    @FXML
    private TableColumn<Provider, String> contactEmailColumn;

    @FXML
    private TableColumn<Provider, String> addressColumn;

    // ================== SERVICE + LISTA ==================

    private final ProviderService providerService = new ProviderService();
    private final ObservableList<Provider> providerList = FXCollections.observableArrayList();

    // ================== INIT ==================

    @FXML
    public void initialize() {
        setupTableColumns();
        loadProviderList();

        providerTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        populateForm(newSel);
                    }
                });
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(
                cd -> new SimpleLongProperty(cd.getValue().getId() != null ? cd.getValue().getId() : 0L).asObject());

        nameProviderColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));

        tributaryIdColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTaxId()));

        contactEmailColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getContactEmail()));

        addressColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getAddress()));
    }

    private void loadProviderList() {
        List<Provider> all = providerService.findAll();
        providerList.setAll(all);
        providerTable.setItems(providerList);
    }

    // ================== FORMULARIO ==================

    private void populateForm(Provider p) {
        idTextField.setText(p.getId() != null ? String.valueOf(p.getId()) : "");
        providerNameTextField.setText(p.getName());
        taxIdTextField.setText(p.getTaxId());
        contactEmailTextField.setText(p.getContactEmail());
        addressTextField.setText(p.getAddress());
        statusLabel.setText("Proveedor seleccionado: " + p.getId());
    }

    private void clearForm() {
        idTextField.clear();
        providerNameTextField.clear();
        taxIdTextField.clear();
        contactEmailTextField.clear();
        addressTextField.clear();
        statusLabel.setText("");
        providerTable.getSelectionModel().clearSelection();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ================== HANDLERS ==================

    @FXML
    private void handleClear(ActionEvent event) {
        clearForm();
        loadProviderList();
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        try {
            String name = providerNameTextField.getText();
            String taxId = taxIdTextField.getText();
            String email = contactEmailTextField.getText();
            String address = addressTextField.getText();

            Provider created = providerService.createProvider(name, taxId, email, address);
            statusLabel.setText("Proveedor creado con ID: " + created.getId());
            clearForm();
            loadProviderList();
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de creación", "No se pudo crear el proveedor: " + e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de creación",
                    "Ocurrió un error al crear el proveedor: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());

            String newName = providerNameTextField.getText();
            String newTaxId = taxIdTextField.getText();
            String newEmail = contactEmailTextField.getText();
            String newAddress = addressTextField.getText();

            Provider updated = providerService.updateProvider(id, newName, newTaxId, newEmail, newAddress);
            statusLabel.setText("Proveedor actualizado: " + updated.getId());
            clearForm();
            loadProviderList();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser numérico.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de actualización", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de actualización",
                    "Ocurrió un error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());
            Provider removed = providerService.removeProvider(id);
            statusLabel.setText("Proveedor eliminado: " + removed.getId());
            clearForm();
            loadProviderList();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser numérico.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de eliminación", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de eliminación",
                    "Ocurrió un error al eliminar: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());
            Provider p = providerService.findById(id);
            populateForm(p);
            statusLabel.setText("Proveedor encontrado: " + p.getId());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser numérico.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de búsqueda", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de búsqueda",
                    "Ocurrió un error al buscar: " + e.getMessage());
        }
    }
}
