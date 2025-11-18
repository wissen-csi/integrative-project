package com.prototype.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bytedeco.javacv.FrameGrabber.Exception;

import com.prototype.model.apis.ApiCloudinary;
import com.prototype.model.apis.CvApi;
import com.prototype.model.daos.BiomedicalEquipmentDAO;
import com.prototype.model.entities.BiomedicalEquipment;
import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SecondaryController implements Initializable {

    @FXML
    private TextField brand;

    @FXML
    private TextField calibracion;

    @FXML
    private ComboBox<EquipmentType> equipmenttype;

    @FXML
    private ComboBox<EquipmentStatus> estatus;

    @FXML
    private Button foto;

    @FXML
    private Button guardar;

    @FXML
    private Spinner<Integer> idproveedor;

    @FXML
    private ImageView image;

    @FXML
    private TextField model;

    @FXML
    private TextField riskclass;

    @FXML
    private TextField serial;

    @FXML
    void capturar(ActionEvent event) throws Exception, InterruptedException {
        BufferedImage bufferedImage = CvApi.takePicture();
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        image.setImage(fxImage);
    }

    @FXML
    void save(ActionEvent event) throws IOException {
        EquipmentStatus equipmentStatus = estatus.getValue();
        EquipmentType equipmentType1 = equipmenttype.getValue();
        String brand1 = brand.getText();
        String calibracion1 = calibracion.getText();
        String model1 = model.getText();
        String riskclass1 = riskclass.getText();
        String serial1 = serial.getText();
        Image x = image.getImage();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(x, null);
        String path = ApiCloudinary.saveimage(bufferedImage);
        BiomedicalEquipment biomedicalEquipment = new BiomedicalEquipment(serial1, brand1, model1, equipmentType1,
                equipmentStatus, path, riskclass1, calibracion1, null);
        BiomedicalEquipmentDAO biomedicalEquipmentDAO = new BiomedicalEquipmentDAO();
        biomedicalEquipmentDAO.save(biomedicalEquipment);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        estatus.getItems().setAll(EquipmentStatus.values());
        equipmenttype.getItems().setAll(EquipmentType.values());
        brand.setPromptText("null");
        calibracion.setPromptText("null");
        model.setPromptText("null");
        riskclass.setPromptText("null");
        serial.setPromptText("null");
        SpinnerValueFactory<Integer> enteroFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 0);
        idproveedor.setValueFactory(enteroFactory);

        image.setImage(null);

        foto.setDisable(false);
        guardar.setDisable(false);

    }

}