package com.prototype.controller;

import java.util.Optional;

import com.prototype.model.config.UUIDGenerator;
import com.prototype.model.entities.EntryRequest;
import com.prototype.model.entities.Equipment;
import com.prototype.model.entities.Person;
import com.prototype.model.enums.RequestType;
import com.prototype.services.EntryRequestService;
import java.awt.image.BufferedImage;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntryRequestController {

    // ================== CAMPOS DE FORMULARIO ==================
    @FXML private Button readQR;
    @FXML private ImageView imageQR;
    @FXML private TextField localDateTime;

    @FXML private TextField requestIdTextField;
    @FXML private TextField personIdTextField;
    @FXML private TextField purposeTextField;

    @FXML private ComboBox<RequestType> requestTypeComboBox;

    @FXML private TextField equipmentIdTextField;
    @FXML private Label statusLabel;

    // ================== TABLA ==================

    @FXML private TableView<EntryRequest> entryRequestTable;

    @FXML private TableColumn<EntryRequest, Long> idColumn;
    @FXML private TableColumn<EntryRequest, String> purposeColumn;
    @FXML private TableColumn<EntryRequest, String> requestTypeColumn;
    @FXML private TableColumn<EntryRequest, String> equipmentColumn;
    @FXML private TableColumn<EntryRequest, String> personColumn;
    @FXML private TableColumn<EntryRequest, String> requestedAt;

    // ================== SERVICE + LISTA ==================

    private final EntryRequestService entryRequestService = new EntryRequestService();
    private final ObservableList<EntryRequest> entryRequestList = FXCollections.observableArrayList();


    // ================== INIT ==================

    @FXML
    public void initialize() {

        // Solo este enum
        requestTypeComboBox.getItems().setAll(RequestType.values());

        setupTableColumns();
        loadEntryRequestList();

        entryRequestTable.getSelectionModel()
                .selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                    if (newSel != null)
                        populateForm(newSel);
                });
    }

    private void setupTableColumns() {

        idColumn.setCellValueFactory(
                cd -> new SimpleLongProperty(cd.getValue().getId()).asObject());

        purposeColumn.setCellValueFactory(
                cd -> new SimpleStringProperty(cd.getValue().getPurpose()));

        requestTypeColumn.setCellValueFactory(
                cd -> new SimpleStringProperty(
                        cd.getValue().getRequestType() != null
                                ? cd.getValue().getRequestType().name()
                                : "")
        );

        equipmentColumn.setCellValueFactory(cd -> {
            Equipment e = cd.getValue().getEquipment();
            return new SimpleStringProperty(e != null ? "ID " + e.getId() : "");
        });

        personColumn.setCellValueFactory(cd -> {
            Person p = cd.getValue().getRequester();
            return new SimpleStringProperty(p != null ? "ID " + p.getId() : "");
        });

        requestedAt.setCellValueFactory(cd -> {
            var date = cd.getValue().getRequestedAt();
            return new SimpleStringProperty(date != null ? date.toString() : "");
        });
    }

    private void loadEntryRequestList() {
        entryRequestList.setAll(entryRequestService.findAll());
        entryRequestTable.setItems(entryRequestList);
    }

    // ================== FORMULARIO ==================

    private void populateForm(EntryRequest req) {

        requestIdTextField.setText(String.valueOf(req.getId()));

        personIdTextField.setText(
                req.getRequester() != null ? String.valueOf(req.getRequester().getId()) : "");

        equipmentIdTextField.setText(
                req.getEquipment() != null ? String.valueOf(req.getEquipment().getId()) : "");

        purposeTextField.setText(req.getPurpose());

        // ÚNICO ENUM
        requestTypeComboBox.setValue(req.getRequestType());

        localDateTime.setText(
                req.getRequestedAt() != null ? req.getRequestedAt().toString() : ""
        );

        statusLabel.setText("Solicitud cargada: " + req.getId());
    }

    private void clearForm() {
        requestIdTextField.clear();
        personIdTextField.clear();
        equipmentIdTextField.clear();
        purposeTextField.clear();
        requestTypeComboBox.getSelectionModel().clearSelection();
        localDateTime.clear();
        entryRequestTable.getSelectionModel().clearSelection();
        statusLabel.setText("");
    }

    // ================== CRUD ==================

    @FXML
    private void handleClear(ActionEvent event) {
        clearForm();
        loadEntryRequestList();
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        try {
            Long personId = Long.parseLong(personIdTextField.getText());
            Long equipmentId = Long.parseLong(equipmentIdTextField.getText());
            String purpose = purposeTextField.getText();
            RequestType type = requestTypeComboBox.getValue();

            EntryRequest created = entryRequestService.createEntryRequest(
                    personId,
                    equipmentId,
                    purpose,
                    type);

            statusLabel.setText("Solicitud creada. ID: " + created.getId());
            clearForm();
            loadEntryRequestList();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo crear: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {

        try {
            Long id = Long.parseLong(requestIdTextField.getText());

            Long newPersonId = personIdTextField.getText().isBlank()
                    ? null
                    : Long.parseLong(personIdTextField.getText());

            Long newEquipmentId = equipmentIdTextField.getText().isBlank()
                    ? null
                    : Long.parseLong(equipmentIdTextField.getText());

            String newPurpose = purposeTextField.getText().isBlank()
                    ? null
                    : purposeTextField.getText();

            RequestType type = requestTypeComboBox.getValue();

            EntryRequest updated = entryRequestService.updateEntryRequest(
                    id,
                    newPersonId,
                    newEquipmentId,
                    newPurpose,
                    type);

            statusLabel.setText("Solicitud actualizada: " + updated.getId());
            clearForm();
            loadEntryRequestList();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo actualizar: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {

        try {
            Long id = Long.parseLong(requestIdTextField.getText());

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Eliminar solicitud con ID " + id + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                entryRequestService.deleteEntryRequest(id);
                statusLabel.setText("Solicitud eliminada.");
                clearForm();
                loadEntryRequestList();
            }

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo eliminar: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            Long id = Long.parseLong(requestIdTextField.getText());

            EntryRequest req = entryRequestService.findById(id);

            entryRequestTable.getItems().setAll(req);
            entryRequestTable.getSelectionModel().select(req);

            populateForm(req);

            statusLabel.setText("Solicitud encontrada.");

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo buscar: " + e.getMessage());
        }
    }

    // ================== ALERTAS ==================

    private void showAlert(AlertType t, String title, String msg) {
        Alert a = new Alert(t);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    void bufferedQR(ActionEvent event) {
        entryRequestService.bufferedQR();
    }

    @FXML
    void generationQR(ActionEvent event) {
        BufferedImage bufferedImage = entryRequestService.createQR(
                Long.parseLong(personIdTextField.getText()),
                Long.parseLong(equipmentIdTextField.getText()),
                UUIDGenerator.generate()
        );
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageQR.setImage(image);
    }

}
