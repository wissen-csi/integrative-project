package com.prototype.services;

import java.util.List;

import com.prototype.model.apis.CvApi;
import com.prototype.model.apis.GoogleApiZxing;
import com.prototype.model.daos.EntryRequestDAO;
import com.prototype.model.daos.EquipmentDAO;
import com.prototype.model.daos.PersonDAO;
import com.prototype.model.entities.EntryRequest;
import com.prototype.model.entities.Equipment;
import com.prototype.model.entities.Person;
import com.prototype.model.enums.RequestType;
import com.prototype.model.helpers.IoManager;

import java.awt.image.BufferedImage;

/**
 * Service class responsible for managing {@link EntryRequest} operations,
 * including creation, update, deletion, and QR generation/reading.
 */
public class EntryRequestService {

    private final EntryRequestDAO entryRequestDAO;
    private final EquipmentDAO equipmentDAO;
    private final PersonDAO personDAO;

    /**
     * Default constructor initializing required DAOs.
     */
    public EntryRequestService() {
        this.entryRequestDAO = new EntryRequestDAO();
        this.equipmentDAO = new EquipmentDAO();
        this.personDAO = new PersonDAO();
    }

    /**
     * Retrieves all existing entry requests.
     *
     * @return list of all {@link EntryRequest} records
     */
    public List<EntryRequest> findAll() {
        return entryRequestDAO.findAll();
    }

    /**
     * Finds an entry request by ID.
     *
     * @param id the ID of the entry request to retrieve
     * @return the found {@link EntryRequest}
     * @throws IllegalArgumentException if the ID is null
     * @throws RuntimeException if the entry request does not exist
     */
    public EntryRequest findById(Long id) {
        IoManager.requireNotNull(id, "ID de la solicitud");

        EntryRequest entryRequest = entryRequestDAO.findById(id);
        IoManager.requireExists(entryRequest, "EntryRequest", id);

        return entryRequest;
    }

    /**
     * Generates a QR code image for the given person and equipment IDs.
     *
     * @param idPersona the person ID encoded in the QR
     * @param idEquipment the equipment ID encoded in the QR
     * @param name the filename used to save the generated QR image
     * @return the generated QR image as {@link BufferedImage}, or null if an error occurs
     */
    public BufferedImage createQR(Long idPersona, Long idEquipment, String name) {
        try {
            BufferedImage bufferedImage = GoogleApiZxing
                    .generateImagen(GoogleApiZxing.createQr(idPersona + "," + idEquipment));

            GoogleApiZxing.saveQrToPictures(bufferedImage, name);
            return bufferedImage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a QR code using {@link CvApi}, extracts its data, determines the last
     * request for the equipment and person, automatically toggles the request type
     * between ENTRY and EXIT, and saves the new request.
     */
    public void bufferedQR() {
        String path;

        try {
            path = CvApi.readQr();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String[] temp = path.split(",");
        Long idPerson = Long.parseLong(temp[0]);
        Long idEquipment = Long.parseLong(temp[1]);

        EntryRequest entryRequest = entryRequestDAO.lastRequest(idEquipment, idPerson);

        if (entryRequest.getRequestType() == RequestType.ENTRY) {
            entryRequest.setRequestType(RequestType.EXIT);
        } else {
            entryRequest.setRequestType(RequestType.ENTRY);
        }

        entryRequest.setId(null);
        entryRequest.setRequestedAt(null);

        entryRequestDAO.save(entryRequest);
    }

    /**
     * Creates a new entry request with the provided parameters.
     *
     * @param requesterId ID of the person making the request
     * @param equipmentId ID of the equipment involved
     * @param purpose purpose or reason of the request
     * @param requestType the type of request (ENTRY or EXIT)
     * @return the created {@link EntryRequest}
     * @throws IllegalArgumentException if any mandatory field is null or blank
     */
    public EntryRequest createEntryRequest(
            Long requesterId,
            Long equipmentId,
            String purpose,
            RequestType requestType) {

        IoManager.requireNotNull(requesterId, "ID del solicitante");
        IoManager.requireNotNull(equipmentId, "ID del equipo");
        IoManager.requireNotBlank(purpose, "Propósito de la solicitud");
        IoManager.requireNotNull(requestType, "Tipo de solicitud");

        Person requester = personDAO.findById(requesterId);
        IoManager.requireExists(requester, "Person", requesterId);

        Equipment equipment = equipmentDAO.findById(equipmentId);
        IoManager.requireExists(equipment, "Equipment", equipmentId);

        EntryRequest entryRequest = new EntryRequest();
        entryRequest.setRequester(requester);
        entryRequest.setEquipment(equipment);
        entryRequest.setPurpose(purpose);
        entryRequest.setRequestType(requestType);

        return entryRequestDAO.save(entryRequest);
    }

    /**
     * Updates an existing entry request.
     *
     * @param id ID of the entry request to update
     * @param newRequesterId optional new requester ID
     * @param newEquipmentId optional new equipment ID
     * @param newPurpose optional new purpose
     * @param requestType optional new request type
     * @return the updated {@link EntryRequest}
     * @throws IllegalArgumentException if the ID is null
     * @throws RuntimeException if the entry request does not exist
     */
    public EntryRequest updateEntryRequest(
            Long id,
            Long newRequesterId,
            Long newEquipmentId,
            String newPurpose,
            RequestType requestType) {

        IoManager.requireNotNull(id, "ID de la solicitud");

        EntryRequest entryRequest = entryRequestDAO.findById(id);
        IoManager.requireExists(entryRequest, "EntryRequest", id);

        if (newRequesterId != null) {
            Person person = personDAO.findById(newRequesterId);
            IoManager.requireExists(person, "Person", newRequesterId);
            entryRequest.setRequester(person);
        }

        if (newEquipmentId != null) {
            Equipment equipment = equipmentDAO.findById(newEquipmentId);
            IoManager.requireExists(equipment, "Equipment", newEquipmentId);
            entryRequest.setEquipment(equipment);
        }

        if (newPurpose != null && !newPurpose.isBlank()) {
            entryRequest.setPurpose(newPurpose);
        }

        if (requestType != null) {
            entryRequest.setRequestType(requestType);
        }

        return entryRequestDAO.update(entryRequest);
    }

    /**
     * Deletes an entry request by ID.
     *
     * @param id the ID of the entry request to delete
     * @return the deleted {@link EntryRequest}
     * @throws IllegalArgumentException if the ID is null
     * @throws RuntimeException if the entry request does not exist
     */
    public EntryRequest deleteEntryRequest(Long id) {

        IoManager.requireNotNull(id, "ID de la solicitud");

        EntryRequest req = entryRequestDAO.findById(id);
        IoManager.requireExists(req, "EntryRequest", id);

        return entryRequestDAO.delete(id);
    }
}
