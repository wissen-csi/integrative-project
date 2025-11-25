package com.prototype.services;

import java.util.List;

import com.prototype.model.apis.ApiCloudinary;
import com.prototype.model.apis.CvApi;
import com.prototype.model.daos.BiomedicalEquipmentDAO;
import com.prototype.model.daos.EquipmentDAO;
import com.prototype.model.daos.ProviderDAO;
import com.prototype.model.daos.TechEquipmentDAO;
import com.prototype.model.entities.TechEquipment;
import com.prototype.model.entities.BiomedicalEquipment;
import com.prototype.model.entities.Equipment;
import com.prototype.model.entities.Provider;
import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;
import com.prototype.model.enums.FrequencyType;
import com.prototype.model.helpers.IoManager;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Service class responsible for equipment-related operations, including
 * creation, update, deletion, image handling, and Cloudinary uploads.
 * Supports both technological and biomedical equipment.
 */
public class EquipmentService {

    private final EquipmentDAO equipmentDAO;
    private final TechEquipmentDAO techEquipmentDAO;
    private final BiomedicalEquipmentDAO biomedicalEquipmentDAO;
    private final ProviderDAO providerDAO;

    /**
     * Default constructor initializing DAOs for all equipment types.
     */
    public EquipmentService() {
        this.equipmentDAO = new EquipmentDAO();
        this.techEquipmentDAO = new TechEquipmentDAO();
        this.biomedicalEquipmentDAO = new BiomedicalEquipmentDAO();
        this.providerDAO = new ProviderDAO();
    }

    /**
     * Retrieves all equipment records, including both technological and biomedical.
     *
     * @return list of {@link Equipment}
     */
    public List<Equipment> findAll() {
        return equipmentDAO.findAll();
    }

    /**
     * Finds equipment by its ID.
     *
     * @param id the equipment ID
     * @return the matching {@link Equipment}
     * @throws IllegalArgumentException if the ID is null
     * @throws RuntimeException if the equipment does not exist
     */
    public Equipment findById(Long id) {
        IoManager.requireNotNull(id, "ID del equipo");
        Equipment equipment = equipmentDAO.findById(id);
        IoManager.requireExists(equipment, "Equipment", id);
        return equipment;
    }

    /**
     * Deletes equipment by ID.
     *
     * @param id the equipment ID
     * @throws IllegalArgumentException if the ID is null
     * @throws RuntimeException if the equipment does not exist
     */
    public void delete(Long id) {
        IoManager.requireNotNull(id, "ID del equipo");
        Equipment eq = equipmentDAO.findById(id);
        IoManager.requireExists(eq, "Equipment", id);
        equipmentDAO.delete(id);
    }

    /**
     * Takes a picture using the {@link CvApi} camera module and converts it to a JavaFX {@link Image}.
     *
     * @return captured image or null if an error occurs
     */
    public Image takeImage() {
        BufferedImage bufferedImage;
        try {
            bufferedImage = CvApi.takePicture();
        } catch (Exception e) {
            return null;
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;
    }

    /**
     * Uploads a JavaFX {@link Image} to Cloudinary using {@link ApiCloudinary}.
     *
     * @param image the image to upload
     * @return URL of the stored image, or empty string if an error occurs
     */
    public String ImageCloud(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            return ApiCloudinary.saveimage(bufferedImage);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Creates a new technological equipment record.
     *
     * @param serial equipment serial number
     * @param brand equipment brand
     * @param model equipment model
     * @param type equipment type
     * @param state equipment status
     * @param providerId provider identifier
     * @param imagePath path or URL of the equipment image
     * @param os operating system
     * @param ramGB RAM size in GB
     * @param frequencyType maintenance frequency type
     * @return created {@link TechEquipment}
     */
    public Equipment createTechEquipment(
            String serial,
            String brand,
            String model,
            EquipmentType type,
            EquipmentStatus state,
            Long providerId,
            String imagePath,
            String os,
            int ramGB,
            FrequencyType frequencyType) {

        IoManager.requireNotBlank(serial, "Serial");
        IoManager.requireNotBlank(brand, "Marca");
        IoManager.requireNotBlank(model, "Modelo");
        IoManager.requireNotNull(type, "Tipo de equipo");
        IoManager.requireNotNull(state, "Estado del equipo");
        IoManager.requireNotNull(providerId, "ID del proveedor");
        IoManager.requireNotNull(frequencyType, "Tipo de Frecuencia");

        IoManager.requireNotBlank(os, "Sistema operativo");

        Provider provider = providerDAO.findById(providerId);
        IoManager.requireExists(provider, "Provider", providerId);

        TechEquipment techEquipment = new TechEquipment();
        techEquipment.setSerial(serial);
        techEquipment.setBrand(brand);
        techEquipment.setModel(model);
        techEquipment.setType(type);
        techEquipment.setState(state);
        techEquipment.setProvider(provider);
        techEquipment.setImagePath(imagePath);
        techEquipment.setOs(os);
        techEquipment.setRamGB(ramGB);
        techEquipment.setFrecuencyType(frequencyType);
        return techEquipmentDAO.save(techEquipment);
    }

    /**
     * Updates an existing technological equipment record.
     *
     * @param id equipment ID
     * @param serial serial number
     * @param brand brand
     * @param model model
     * @param type equipment type
     * @param state equipment status
     * @param providerId provider ID
     * @param imagePath image path or URL
     * @param os operating system
     * @param ramGB RAM amount
     * @param frequencyType maintenance frequency
     * @return updated {@link TechEquipment}
     */
    public TechEquipment updateTechEquipment(
            Long id,
            String serial,
            String brand,
            String model,
            EquipmentType type,
            EquipmentStatus state,
            Long providerId,
            String imagePath,
            String os,
            int ramGB,
            FrequencyType frequencyType) {

        IoManager.requireNotNull(id, "ID del equipo tecnológico");

        TechEquipment techEquipment = techEquipmentDAO.findById(id);
        IoManager.requireExists(techEquipment, "TechEquipment", id);

        IoManager.requireNotBlank(serial, "Serial");
        IoManager.requireNotBlank(brand, "Marca");
        IoManager.requireNotBlank(model, "Modelo");
        IoManager.requireNotNull(type, "Tipo de equipo");
        IoManager.requireNotNull(state, "Estado del equipo");
        IoManager.requireNotNull(providerId, "ID del proveedor");
        IoManager.requireNotBlank(os, "Sistema operativo");

        Provider provider = providerDAO.findById(providerId);
        IoManager.requireExists(provider, "Provider", providerId);

        techEquipment.setSerial(serial);
        techEquipment.setBrand(brand);
        techEquipment.setModel(model);
        techEquipment.setType(type);
        techEquipment.setState(state);
        techEquipment.setProvider(provider);
        techEquipment.setImagePath(imagePath);
        techEquipment.setOs(os);
        techEquipment.setRamGB(ramGB);
        techEquipment.setFrecuencyType(frequencyType);
        techEquipment.setImagePath(imagePath);

        return techEquipmentDAO.update(techEquipment);
    }

    /**
     * Creates a new biomedical equipment record.
     *
     * @param serial equipment serial number
     * @param brand equipment brand
     * @param model equipment model
     * @param type equipment type
     * @param state equipment status
     * @param providerId provider ID
     * @param imagePath equipment image path or URL
     * @param riskClass biomedical risk class
     * @param calibrationCert calibration certificate identifier
     * @param frequencyType maintenance frequency
     * @return created {@link BiomedicalEquipment}
     */
    public BiomedicalEquipment createBiomedicalEquipment(
            String serial,
            String brand,
            String model,
            EquipmentType type,
            EquipmentStatus state,
            Long providerId,
            String imagePath,
            String riskClass,
            String calibrationCert,
            FrequencyType frequencyType) {

        IoManager.requireNotBlank(serial, "Serial");
        IoManager.requireNotBlank(brand, "Marca");
        IoManager.requireNotBlank(model, "Modelo");
        IoManager.requireNotNull(type, "Tipo de equipo");
        IoManager.requireNotNull(state, "Estado del equipo");
        IoManager.requireNotNull(providerId, "ID del proveedor");

        IoManager.requireNotBlank(riskClass, "Clase de riesgo");
        IoManager.requireNotBlank(calibrationCert, "Certificado de calibración");

        Provider provider = providerDAO.findById(providerId);
        IoManager.requireExists(provider, "Provider", providerId);

        BiomedicalEquipment biomedicalEquipment = new BiomedicalEquipment();
        biomedicalEquipment.setSerial(serial);
        biomedicalEquipment.setBrand(brand);
        biomedicalEquipment.setModel(model);
        biomedicalEquipment.setType(type);
        biomedicalEquipment.setState(state);
        biomedicalEquipment.setProvider(provider);
        biomedicalEquipment.setImagePath(imagePath);
        biomedicalEquipment.setRiskClass(riskClass);
        biomedicalEquipment.setCalibrationCert(calibrationCert);
        biomedicalEquipment.setFrecuencyType(frequencyType);

        return biomedicalEquipmentDAO.save(biomedicalEquipment);
    }

    /**
     * Updates an existing biomedical equipment record.
     *
     * @param id equipment ID
     * @param serial serial number
     * @param brand brand
     * @param model model
     * @param type equipment type
     * @param state equipment state
     * @param providerId provider ID
     * @param imagePath image path or URL
     * @param riskClass biomedical risk class
     * @param calibrationCert calibration certificate
     * @param frequencyType maintenance frequency
     * @return updated {@link BiomedicalEquipment}
     */
    public BiomedicalEquipment updateBiomedicalEquipment(
            Long id,
            String serial,
            String brand,
            String model,
            EquipmentType type,
            EquipmentStatus state,
            Long providerId,
            String imagePath,
            String riskClass,
            String calibrationCert,
            FrequencyType frequencyType) {

        IoManager.requireNotNull(id, "ID del equipo biomédico");

        BiomedicalEquipment biomedicalEquipment = biomedicalEquipmentDAO.findById(id);
        IoManager.requireExists(biomedicalEquipment, "BiomedicalEquipment", id);

        IoManager.requireNotBlank(serial, "Serial");
        IoManager.requireNotBlank(brand, "Marca");
        IoManager.requireNotBlank(model, "Modelo");
        IoManager.requireNotNull(type, "Tipo de equipo");
        IoManager.requireNotNull(state, "Estado del equipo");
        IoManager.requireNotNull(providerId, "ID del proveedor");
        IoManager.requireNotBlank(riskClass, "Clase de riesgo");
        IoManager.requireNotBlank(calibrationCert, "Certificado de calibración");
        IoManager.requireNotNull(frequencyType, "Tipo De Frecuencia");

        Provider provider = providerDAO.findById(providerId);
        IoManager.requireExists(provider, "Provider", providerId);

        biomedicalEquipment.setSerial(serial);
        biomedicalEquipment.setBrand(brand);
        biomedicalEquipment.setModel(model);
        biomedicalEquipment.setType(type);
        biomedicalEquipment.setState(state);
        biomedicalEquipment.setProvider(provider);
        biomedicalEquipment.setImagePath(imagePath);
        biomedicalEquipment.setRiskClass(riskClass);
        biomedicalEquipment.setCalibrationCert(calibrationCert);
        biomedicalEquipment.setFrecuencyType(frequencyType);

        return biomedicalEquipmentDAO.update(biomedicalEquipment);
    }
}
