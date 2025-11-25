package com.prototype.controller;


import java.util.List;
import java.util.Optional;


import com.prototype.model.entities.BiomedicalEquipment;
import com.prototype.model.entities.Equipment;
import com.prototype.model.entities.TechEquipment;
import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;
import com.prototype.model.enums.FrequencyType;
import com.prototype.services.EquipmentService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class EquipmentController {
        @FXML
    private Label providerLabel;

    @FXML
    private TextField providerTextField;
        @FXML
    private TableColumn<Equipment, String> path_image;
    
    @FXML
    private ImageView equipment_imageView;

    // ================== CAMPOS FORMULARIO ==================

    @FXML
    private Label equipmentIdLabel;
    @FXML
    private TextField equipmentIdTextField;

    @FXML
    private Label frecuencyTypeLabel;
    @FXML
    private ComboBox<FrequencyType> frecuencyTypeComboBox;

    @FXML
    private Label serialEquipmentLabel;
    @FXML
    private TextField serialEquipmentTextField;

    @FXML
    private Label brandEquipmentLabel;
    @FXML
    private TextField brandEquipmentTextField;

    @FXML
    private Label modelEquipmentLabel;
    @FXML
    private TextField modelEquipmentTextField;

    @FXML
    private Label equipmentTypeLabel;
    @FXML
    private ComboBox<EquipmentType> equipmentTypeComboBox;

    @FXML
    private Label equipmentStatusLabel;
    @FXML
    private ComboBox<EquipmentStatus> equipmentStatusComboBox;

    // Exclusivos TECH
    @FXML
    private Label osLabel;
    @FXML
    private TextField osTextField;

    @FXML
    private Label ramGBLabel;
    @FXML
    private TextField ramGBTextField;

    // Exclusivos BIOMEDICAL
    @FXML
    private Label riskClassLabel;
    @FXML
    private TextField riskClassTextField;

    @FXML
    private Label calibrationCertLabel;
    @FXML
    private TextField calibrationCertTextField;

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
    @FXML
    private Button generateQrButton; // todavía sin usar
    @FXML
    private Button takePhotoButton;

    // ================== TABLA ==================

    @FXML
    private TableView<Equipment> cursoTable;

    @FXML
    private TableColumn<Equipment, Integer> idColumn;
    @FXML
    private TableColumn<Equipment, String> frequencyTypeColumn;
    @FXML
    private TableColumn<Equipment, String> serialEquipmentColumn;
    @FXML
    private TableColumn<Equipment, String> equipmentBrandColumn;
    @FXML
    private TableColumn<Equipment, String> equipmentModelColumn;
    @FXML
    private TableColumn<Equipment, String> equipmentTypeColumn;
    @FXML
    private TableColumn<Equipment, String> equipmentStateColumn;
    @FXML
    private TableColumn<Equipment, String> osColumn;
    @FXML
    private TableColumn<Equipment, Integer> ramGBColumn;
    @FXML
    private TableColumn<Equipment, String> riskClassColumn;
    @FXML
    private TableColumn<Equipment, String> calibracionCertColumn;
    @FXML
    private TableColumn<Equipment, Long> providerId;

    // ================== SERVICE + LISTA ==================

    private final EquipmentService equipmentService = new EquipmentService();
    private final ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    // Placeholder mientras no tengamos selección de proveedor en la UI

  
    // ================== INIT ==================

    @FXML
    public void initialize() {
        equipment_imageView.setImage(null);
        // Combos
        equipmentTypeComboBox.getItems().setAll(EquipmentType.values());
        frecuencyTypeComboBox.getItems().setAll(FrequencyType.values());
        equipmentStatusComboBox.getItems().setAll(EquipmentStatus.values());

        // Ocultar exclusivos al inicio
        showTechFields(false);
        showBiomedFields(false);

        // Listener tipo equipo
        equipmentTypeComboBox.valueProperty().addListener(
                (obs, oldType, newType) -> onEquipmentTypeChanged(newType));

        // Columnas tabla
        setupTableColumns();

        // Cargar datos
        loadEquipmentList();

        // Listener selección tabla
        cursoTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        populateForm(newSel);
                    }
                });
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cd -> new SimpleIntegerProperty(
                cd.getValue().getId() != null ? cd.getValue().getId().intValue() : 0).asObject());

        frequencyTypeColumn.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getFrequencyType() != null
                        ? cd.getValue().getFrequencyType().name()
                        : ""));

        serialEquipmentColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getSerial()));
        equipmentBrandColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBrand()));
        equipmentModelColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getModel()));
        
        equipmentTypeColumn.setCellValueFactory(cd -> new SimpleStringProperty(
            cd.getValue().getType() != null
                    ? cd.getValue().getType().name()
                    : ""));

        path_image.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getImagePath()));

        equipmentStateColumn.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getState() != null
                        ? cd.getValue().getState().name()
                        : ""));

        osColumn.setCellValueFactory(cd -> {
            Equipment e = cd.getValue();
            if (e instanceof TechEquipment tech) {
                return new SimpleStringProperty(tech.getOs());
            }
            return new SimpleStringProperty("");
        });

        ramGBColumn.setCellValueFactory(cd -> {
            Equipment e = cd.getValue();
            if (e instanceof TechEquipment tech) {
                return new SimpleIntegerProperty(tech.getRamGB()).asObject();
            }
            return new SimpleIntegerProperty(0).asObject();
        });

        riskClassColumn.setCellValueFactory(cd -> {
            Equipment e = cd.getValue();
            if (e instanceof BiomedicalEquipment bio) {
                return new SimpleStringProperty(bio.getRiskClass());
            }
            return new SimpleStringProperty("");
        });

        calibracionCertColumn.setCellValueFactory(cd -> {
            Equipment e = cd.getValue();
            if (e instanceof BiomedicalEquipment bio) {
                return new SimpleStringProperty(bio.getCalibrationCert());
            }
            return new SimpleStringProperty("");
        });

        providerId.setCellValueFactory(cd -> {
            var provider = cd.getValue().getProvider();
                return new SimpleLongProperty(
                provider != null ? provider.getId() : 0L).asObject();
        });
    }

    private void loadEquipmentList() {
        List<Equipment> all = equipmentService.findAll();
        equipmentList.setAll(all);
        cursoTable.setItems(equipmentList);
    }

    // ================== VISIBILIDAD EXCLUSIVOS ==================

    private void onEquipmentTypeChanged(EquipmentType newType) {
        if (newType == null) {
            showTechFields(false);
            showBiomedFields(false);
            return;
        }

        switch (newType) {
            case TECH:
                showTechFields(true);
                showBiomedFields(false);
                break;
            case BIOMEDICAL:
                showTechFields(false);
                showBiomedFields(true);
                break;
            default:
                showTechFields(false);
                showBiomedFields(false);
        }
    }

    private void showTechFields(boolean visible) {
        osLabel.setVisible(visible);
        osLabel.setManaged(visible);
        osTextField.setVisible(visible);
        osTextField.setManaged(visible);

        ramGBLabel.setVisible(visible);
        ramGBLabel.setManaged(visible);
        ramGBTextField.setVisible(visible);
        ramGBTextField.setManaged(visible);
    }

    private void showBiomedFields(boolean visible) {
        riskClassLabel.setVisible(visible);
        riskClassLabel.setManaged(visible);
        riskClassTextField.setVisible(visible);
        riskClassTextField.setManaged(visible);

        calibrationCertLabel.setVisible(visible);
        calibrationCertLabel.setManaged(visible);
        calibrationCertTextField.setVisible(visible);
        calibrationCertTextField.setManaged(visible);
    }

    // ================== FORMULARIO ==================

    private void populateForm(Equipment equipment) {
        equipmentIdTextField.setText(
                equipment.getId() != null ? String.valueOf(equipment.getId()) : "");
        serialEquipmentTextField.setText(equipment.getSerial());
        brandEquipmentTextField.setText(equipment.getBrand());
        modelEquipmentTextField.setText(equipment.getModel());
        frecuencyTypeComboBox.setValue(equipment.getFrequencyType());
        equipmentTypeComboBox.setValue(equipment.getType());
        equipmentStatusComboBox.setValue(equipment.getState());

        // Mostrar exclusivos según tipo
        onEquipmentTypeChanged(equipment.getType());

        osTextField.clear();
        ramGBTextField.clear();
        riskClassTextField.clear();
        calibrationCertTextField.clear();

        if (equipment instanceof TechEquipment tech) {
            osTextField.setText(tech.getOs());
            ramGBTextField.setText(String.valueOf(tech.getRamGB()));
        } else if (equipment instanceof BiomedicalEquipment bio) {
            riskClassTextField.setText(bio.getRiskClass());
            calibrationCertTextField.setText(bio.getCalibrationCert());
        }

        statusLabel.setText("Equipo seleccionado: " + equipment.getId());
             String urlImage = equipment.getImagePath();

        if (urlImage != null && !urlImage.isBlank()) {
            try {
            Image img = new Image(urlImage, true); // carga asincrónica
            equipment_imageView.setImage(img);
            } catch (Exception e) {
            equipment_imageView.setImage(null);
        }
     } else {
        equipment_imageView.setImage(null);
     }
    }
    
    private void clearForm() {
        equipmentIdTextField.clear();
        serialEquipmentTextField.clear();
        brandEquipmentTextField.clear();
        modelEquipmentTextField.clear();
        frecuencyTypeComboBox.getSelectionModel().clearSelection();
        equipmentTypeComboBox.getSelectionModel().clearSelection();
        equipmentStatusComboBox.getSelectionModel().clearSelection();
        osTextField.clear();
        ramGBTextField.clear();
        riskClassTextField.clear();
        calibrationCertTextField.clear();
        showTechFields(false);
        showBiomedFields(false);
        statusLabel.setText("");
    }

    // ================== HANDLERS ==================

    @FXML
    private void handleClear(ActionEvent event) {
        clearForm();
        cursoTable.getSelectionModel().clearSelection();
        loadEquipmentList();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            Long id = Long.parseLong(equipmentIdTextField.getText());

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de que desea eliminar el equipo con ID: " + id + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                equipmentService.delete(id);
                statusLabel.setText("Equipo eliminado exitosamente.");
                loadEquipmentList();
                clearForm();
            }

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de negocio", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de eliminación", "No se pudo eliminar el equipo: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            Long id = Long.parseLong(equipmentIdTextField.getText());
            Equipment e = equipmentService.findById(id);

            if (e != null) {
                populateForm(e);
                cursoTable.getItems().setAll(e);
                cursoTable.getSelectionModel().select(e);
                statusLabel.setText("Equipo encontrado.");
            } else {
                showAlert(AlertType.INFORMATION, "Búsqueda", "No se encontró equipo con ID: " + id);
                statusLabel.setText("Equipo no encontrado.");
            }
        } catch (NumberFormatException ex) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Error de búsqueda", "No se pudo buscar el equipo: " + ex.getMessage());
        }
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        try {
            EquipmentType type = equipmentTypeComboBox.getValue();
            EquipmentStatus status = equipmentStatusComboBox.getValue();
            FrequencyType freq = frecuencyTypeComboBox.getValue();

            String serial = serialEquipmentTextField.getText();
            String brand = brandEquipmentTextField.getText();
            String model = modelEquipmentTextField.getText();
            String imagePath = equipmentService.ImageCloud(equipment_imageView.getImage());
            Long idProvider = Long.parseLong(providerTextField.getText());


            if (type == null || status == null || freq == null) {
                showAlert(Alert.AlertType.ERROR, "Datos incompletos",
                        "Debe seleccionar Tipo de Equipo, Estado y Frecuencia.");
                return;
            }

            Equipment created;

            if (type == EquipmentType.TECH) {
                int ram = 0;
                if (!ramGBTextField.getText().isBlank()) {
                    ram = Integer.parseInt(ramGBTextField.getText());
                }
                String os = osTextField.getText();

                created = equipmentService.createTechEquipment(
                        serial,
                        brand,
                        model,
                        type,
                        status,
                        idProvider,
                        imagePath,
                        os,
                        ram,
                        freq 
                );

            } else if (type == EquipmentType.BIOMEDICAL) {
                String riskClass = riskClassTextField.getText();
                String calibCert = calibrationCertTextField.getText();

                created = equipmentService.createBiomedicalEquipment(
                        serial,
                        brand,
                        model,
                        type,
                        status,
                        idProvider,
                        imagePath,
                        riskClass,
                        calibCert,
                        freq 
                );

            } else {
                showAlert(Alert.AlertType.ERROR, "Tipo de equipo", "Seleccione un tipo de equipo.");
                return;
            }

            statusLabel.setText("Equipo creado con ID: " + created.getId());
            clearForm();
            loadEquipmentList();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de formato",
                    "La RAM debe ser un número entero.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de creación",
                    "No se pudo crear el equipo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            Long id = Long.parseLong(equipmentIdTextField.getText());

            EquipmentType type = equipmentTypeComboBox.getValue();
            EquipmentStatus status = equipmentStatusComboBox.getValue();
            FrequencyType freq = frecuencyTypeComboBox.getValue();

            String serial = serialEquipmentTextField.getText();
            String brand = brandEquipmentTextField.getText();
            String model = modelEquipmentTextField.getText();
            String imagePath = equipmentService.ImageCloud(equipment_imageView.getImage());
            Long idProvider = Long.parseLong(providerTextField.getText());



            if (type == null || status == null || freq == null) {
                showAlert(Alert.AlertType.ERROR, "Datos incompletos",
                        "Debe seleccionar Tipo de Equipo, Estado y Frecuencia.");
                return;
            }

            Equipment updated;

            if (type == EquipmentType.TECH) {
                int ram = 0;
                if (!ramGBTextField.getText().isBlank()) {
                    ram = Integer.parseInt(ramGBTextField.getText());
                }
                String os = osTextField.getText();

                updated = equipmentService.updateTechEquipment(
                        id,
                        serial,
                        brand,
                        model,
                        type,
                        status,
                        idProvider,
                        imagePath,
                        os,
                        ram,
                        freq);

            } else if (type == EquipmentType.BIOMEDICAL) {
                String riskClass = riskClassTextField.getText();
                String calibCert = calibrationCertTextField.getText();

                updated = equipmentService.updateBiomedicalEquipment(
                        id,
                        serial,
                        brand,
                        model,
                        type,
                        status,
                        idProvider,
                        imagePath,
                        riskClass,
                        calibCert,
                        freq);

            } else {
                showAlert(Alert.AlertType.ERROR, "Tipo de equipo", "Seleccione un tipo de equipo.");
                return;
            }

            statusLabel.setText("Equipo actualizado: " + updated.getId());
            clearForm();
            loadEquipmentList();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de formato",
                    "El ID y la RAM deben ser números.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de actualización",
                    "No se pudo actualizar el equipo: " + e.getMessage());
            e.printStackTrace();
        }
    }
        @FXML
    void takePhoto(ActionEvent event) {
        equipment_imageView.setImage(equipmentService.takeImage());

    }
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}