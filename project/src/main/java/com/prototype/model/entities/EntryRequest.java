package com.prototype.model.entities;

import java.time.LocalDateTime;

import com.prototype.model.config.UUIDGenerator;
import com.prototype.model.enums.RequestStatus;



/**
 * La clase {@code EntryRequest} representa una solicitud de ingreso o registro
 * de un equipo dentro del sistema del hospital.
 * 
 * <p> 
 * Esta entidad modela la relación entre un {@link Equipment} y las personas
 * involucradas en la solicitud: el solicitante y el responsable interno.
 * Incluye además el propósito de la solicitud, la fecha en que fue realizada
 * y su estado actual dentro del flujo de trabajo.
 * </p>
 * 
 * <p>
 * Es un POJO (Plain Old Java Object), ya que contiene solo atributos, 
 * constructores, métodos de acceso (getters y setters) y un método {@code toString()} 
 * para representar la instancia como cadena.
 * </p>
 * 
 * @author Salomón
 * @version 1.0
 */
public class EntryRequest {

    /**
     * Identificador único de la solicitud.
     * Se genera automáticamente usando {@link UUIDGenerator} cuando no se recibe por parámetro.
     */
    private String id;

    /**
     * Objeto {@link Equipment} que representa el equipo solicitado o registrado.
     */
    private Equipment equipment;

    /**
     * Persona que realiza la solicitud.
     * Representada por un objeto {@link Person}.
     */
    private Person requester;

    /**
     * Persona responsable interna asignada al proceso de solicitud.
     * Puede ser {@code null} si aún no se ha asignado un responsable.
     */
    private Person internalResponsible;

    /**
     * Propósito o descripción de la solicitud.
     * Indica la razón o el motivo por el cual se está registrando el equipo.
     */
    private String purpose;

    /**
     * Fecha y hora en que se realizó la solicitud.
     * Se representa con {@link LocalDateTime}.
     */
    private LocalDateTime requestedAt;

    /**
     * Estado actual de la solicitud.
     * Utiliza el enumerado {@link RequestStatus} para definir valores posibles:
     * PENDING, SUCCESS, ERROR o FAILED.
     */
    private RequestStatus status;

    // ===================== CONSTRUCTORES =====================

    /**
     * Constructor completo de {@code EntryRequest}.
     * 
     * @param id identificador único de la solicitud.
     * @param equipment equipo solicitado.
     * @param requester persona que realiza la solicitud.
     * @param internalResponsible persona responsable interna (puede ser null).
     * @param purpose propósito o descripción de la solicitud.
     * @param requestedAt fecha y hora de la solicitud.
     * @param status estado actual de la solicitud.
     */
    public EntryRequest(){}
    public EntryRequest(String id, Equipment equipment, Person requester, Person internalResponsible, String purpose,
            LocalDateTime requestedAt, RequestStatus status) {
        this.id = id;
        this.equipment = equipment;
        this.requester = requester;
        this.internalResponsible = internalResponsible;
        this.purpose = purpose;
        this.requestedAt = requestedAt;
        this.status = status;
    }

    /**
     * Constructor que genera automáticamente el {@code id} utilizando {@link UUIDGenerator}.
     * 
     * @param equipment equipo solicitado.
     * @param requester persona que realiza la solicitud.
     * @param internalResponsible persona responsable interna (puede ser null).
     * @param purpose propósito o descripción de la solicitud.
     * @param requestedAt fecha y hora de la solicitud.
     * @param status estado actual de la solicitud.
     */
    public EntryRequest(Equipment equipment, Person requester, 
            Person internalResponsible, String purpose,
            LocalDateTime requestedAt, RequestStatus status) {
        this.id = UUIDGenerator.generate();
        this.equipment = equipment;
        this.requester = requester;
        this.internalResponsible = internalResponsible;
        this.purpose = purpose;
        this.requestedAt = requestedAt;
        this.status = status;
    }

    // ===================== GETTERS Y SETTERS =====================

    /**
     * Obtiene el identificador de la solicitud.
     * @return id único de la solicitud.
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un nuevo identificador a la solicitud.
     * @param id identificador único.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el equipo asociado a la solicitud.
     * @return objeto {@link Equipment}.
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Establece el equipo asociado a la solicitud.
     * @param equipment equipo solicitado.
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * Obtiene la persona solicitante.
     * @return objeto {@link Person} solicitante.
     */
    public Person getRequester() {
        return requester;
    }

    /**
     * Establece la persona solicitante.
     * @param requester persona que realiza la solicitud.
     */
    public void setRequester(Person requester) {
        this.requester = requester;
    }

    /**
     * Obtiene la persona responsable interna asignada.
     * @return objeto {@link Person} o {@code null} si no se ha asignado.
     */
    public Person getInternalResponsible() {
        return internalResponsible;
    }

    /**
     * Asigna una persona responsable interna.
     * @param internalResponsible persona responsable.
     */
    public void setInternalResponsible(Person internalResponsible) {
        this.internalResponsible = internalResponsible;
    }

    /**
     * Obtiene el propósito de la solicitud.
     * @return texto con la descripción del propósito.
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Establece el propósito o descripción de la solicitud.
     * @param purpose texto descriptivo.
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Obtiene la fecha y hora en que se realizó la solicitud.
     * @return objeto {@link LocalDateTime} con la fecha de solicitud.
     */
    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    /**
     * Establece la fecha y hora de la solicitud.
     * @param requestedAt objeto {@link LocalDateTime}.
     */
    public void setRequested(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    /**
     * Obtiene el estado actual de la solicitud.
     * @return estado como {@link RequestStatus}.
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Actualiza el estado de la solicitud.
     * @param status nuevo estado de tipo {@link RequestStatus}.
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    // ===================== MÉTODO toString =====================

    /**
     * Retorna una representación en texto del objeto {@code EntryRequest}.
     * @return cadena con los datos más relevantes de la solicitud.
     */
    @Override
    public String toString() {
        return "EntryRequest{" +
                "id='" + id + '\'' +
                ", equipment=" + equipment +
                ", requester=" + requester +
                ", internalResponsible=" + internalResponsible +
                ", purpose='" + purpose + '\'' +
                ", requestedAt=" + requestedAt +
                ", status=" + status +
                '}';
    }
}
