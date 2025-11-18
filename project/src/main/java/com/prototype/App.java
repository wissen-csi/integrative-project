package com.prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.prototype.model.daos.BiomedicalEquipmentDAO;
import com.prototype.model.daos.EntryRequestDAO;

import com.prototype.model.daos.PersonDAO;
import com.prototype.model.entities.BiomedicalEquipment;
import com.prototype.model.entities.EntryRequest;
import com.prototype.model.entities.Equipment;
import com.prototype.model.entities.Person;
import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;
import com.prototype.model.enums.RequestStatus;
import com.prototype.model.enums.Role;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {
        
        Person p = new Person("John Doe", "1234567890", Role.BOSS);
        PersonDAO personDAO = new PersonDAO();
        personDAO.save(p);
        
        BiomedicalEquipment be = new BiomedicalEquipment("asd", "asd", "as", EquipmentType.AUDIOVISUAL, EquipmentStatus.DAMAGED,
         "sa", "dsf", "asdfsdv", null);
        BiomedicalEquipmentDAO biomedicalEquipmentDAO = new BiomedicalEquipmentDAO();
        biomedicalEquipmentDAO.save(be);
        Equipment equipment = be;
        

        EntryRequest entryRequest = new EntryRequest(equipment, p, "asd", RequestStatus.FAILED);
        EntryRequestDAO entryRequestDAO = new EntryRequestDAO();
        entryRequestDAO.save(entryRequest);
        launch();




    }



}