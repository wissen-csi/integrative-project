package com.prototype.controller;


import java.util.List;
import java.util.Optional;


import com.prototype.model.entities.Person;
import com.prototype.model.enums.Role;
import com.prototype.services.PersonService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PersonController {


    // ========= CAMPOS DEL FORMULARIO =========

    @FXML
    private Label fullNameLabel;
    @FXML
    private TextField fullNameTextField;

    @FXML
    private Label documentLabel;
    @FXML
    private TextField documentTextField;

    @FXML
    private Label personRoleLabel;
    @FXML
    private ComboBox<Role> personRoleComboBox;

    // Estos dos parecen venir del layout anterior; aquí no se usan,
    // pero los dejo para que el FXML no reviente al cargar:
    @FXML
    private Label providerIdLabel;
    @FXML
    private TextField providerIdTextField;

    @FXML
    private Label statusLabel;

    // Campo para el ID (arriba del formulario, "ID (para buscar, actualizar)")
    @FXML
    private TextField idTextField;

    // ========= TABLA =========

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, Long> idColumn;
    @FXML
    private TableColumn<Person, String> fullNameColumn;
    @FXML
    private TableColumn<Person, String> documentColumn;
    @FXML
    private TableColumn<Person, String> roleColumn;

    // ========= SERVICE + LISTA OBSERVABLE =========

    private final PersonService personService = new PersonService();
    private final ObservableList<Person> personList = FXCollections.observableArrayList();

    // =====================================================================
    //                               INIT
    // =====================================================================

    @FXML
    public void initialize() {
        // 1. Llenar el ComboBox con los valores del enum Role
        personRoleComboBox.setItems(FXCollections.observableArrayList(Role.values()));

        // 2. Configurar columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        documentColumn.setCellValueFactory(new PropertyValueFactory<>("document"));

        // Para el rol, mostramos el nombre del enum (WATCHMAN, ADMIN, etc.)
        roleColumn.setCellValueFactory(cellData -> {
            Person p = cellData.getValue();
            String roleName = (p.getRole() != null) ? p.getRole().name() : "";
            return new SimpleStringProperty(roleName);
        });

        // 3. Cargar la lista inicial de personas
        loadPersonList();

        // 4. Listener para seleccionar una fila y cargarla en el formulario
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    populateForm(newSelection);
                }
            }
        );
    }

    private void loadPersonList() {
        personList.clear();
        List<Person> people = personService.findAll();
        personList.addAll(people);
        personTable.setItems(personList);
    }

    private void populateForm(Person p) {
        idTextField.setText(p.getId() != null ? String.valueOf(p.getId()) : "");
        fullNameTextField.setText(p.getFullName() != null ? p.getFullName() : "");
        documentTextField.setText(p.getDocument() != null ? p.getDocument() : "");
        personRoleComboBox.setValue(p.getRole());

        statusLabel.setText("Persona seleccionada: " + p.getId());
    }

    private void clearForm() {
        idTextField.clear();
        fullNameTextField.clear();
        documentTextField.clear();
        personRoleComboBox.getSelectionModel().clearSelection();
        statusLabel.setText("");
    }

    // =====================================================================
    //                            BOTÓN LIMPIAR
    // =====================================================================

    @FXML
    private void handleClear(ActionEvent event) {
        clearForm();
        personTable.getSelectionModel().clearSelection();
        loadPersonList();
    }

    // =====================================================================
    //                               CREAR
    // =====================================================================

    @FXML
    private void handleCreate(ActionEvent event) {
        try {
            String fullName = fullNameTextField.getText();
            String document = documentTextField.getText();
            Role role = personRoleComboBox.getValue();

            Person created = personService.createPerson(fullName, document, role);

            statusLabel.setText("Persona creada con ID: " + created.getId());
            loadPersonList();
            clearForm();
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Validación", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de creación", "No se pudo crear la persona: " + e.getMessage());
        }
    }

    // =====================================================================
    //                               ACTUALIZAR
    // =====================================================================

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());
            String fullName = fullNameTextField.getText();
            String document = documentTextField.getText();
            Role role = personRoleComboBox.getValue();

            Person updated = personService.updatePerson(id, fullName, document, role);

            statusLabel.setText("Persona actualizada: " + updated.getId());
            loadPersonList();
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Validación / Negocio", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de actualización", "No se pudo actualizar la persona: " + e.getMessage());
        }
    }

    // =====================================================================
    //                               ELIMINAR
    // =====================================================================

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de que desea eliminar la persona con ID: " + id + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                personService.deletePerson(id);
                statusLabel.setText("Persona eliminada exitosamente.");
                loadPersonList();
                clearForm();
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error de negocio", e.getMessage());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error de eliminación", "No se pudo eliminar la persona: " + e.getMessage());
        }
    }

    // =====================================================================
    //                               BUSCAR
    // =====================================================================

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            Long id = Long.parseLong(idTextField.getText());
            Person p = personService.findById(id);

            if (p != null) {
                populateForm(p);
                personTable.getItems().setAll(p);
                personTable.getSelectionModel().select(p);
                statusLabel.setText("Persona encontrada.");
            } else {
                showAlert(AlertType.INFORMATION, "Búsqueda", "No se encontró persona con ID: " + id);
                statusLabel.setText("Persona no encontrada.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El ID debe ser un número.");
        }
    }

    // =====================================================================
    //                        MÉTODO UTILIDAD ALERTAS
    // =====================================================================

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   

}