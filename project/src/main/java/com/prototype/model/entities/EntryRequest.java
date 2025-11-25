package com.prototype.model.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.prototype.model.enums.RequestType;

/**
 * Entity that represents a request to authorize the entry of technological
 * or biomedical equipment into the institution.
 *
 * <p>This entity records who made the request, which equipment is involved,
 * the stated purpose, the timestamp of creation, and the request type.</p>
 *
 * <p>Instances of this class are persisted in the {@code entry_request} table.</p>
 */
@Entity
@Table(name = "entry_request")
public class EntryRequest {

    /** Placeholder static reference used elsewhere in the application. */
    public static Class<Long> classS;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Equipment associated with this entry request.
     * Loaded lazily to avoid unnecessary data retrieval.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "id_equipment")
    private Equipment equipment;

    /**
     * Person who submitted the request.
     * Also loaded lazily for efficiency.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Person requester;

    /**
     * Purpose or justification for the equipment entry.
     */
    @Column(nullable = false, length = 100)
    private String purpose;

    /**
     * Timestamp generated automatically upon the creation of the record.
     */
    @CreationTimestamp
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    /**
     * Type of request (e.g., ENTRY, EXIT).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType;

    /**
     * Default constructor required by JPA.
     */
    public EntryRequest() {
    }

    /**
     * Deprecated constructor including the ID.
     * <p>Kept only for compatibility. New instances should rely on automatic ID generation.</p>
     */
    @Deprecated
    public EntryRequest(Long id, Equipment equipment, Person requester, String purpose,
                        LocalDateTime requestedAt, RequestType requestType) {
        this.id = id;
        this.equipment = equipment;
        this.requester = requester;
        this.purpose = purpose;
        this.requestedAt = requestedAt;
        this.requestType = requestType;
    }

    /**
     * Constructor for creating new entry requests without specifying an ID
     * or creation timestamp.
     */
    public EntryRequest(Equipment equipment, Person requester, String purpose, RequestType requestType) {
        this.id = null;
        this.equipment = equipment;
        this.requester = requester;
        this.purpose = purpose;
        this.requestedAt = null;
        this.requestType = requestType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Person getRequester() {
        return requester;
    }

    public void setRequester(Person requester) {
        this.requester = requester;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "EntryRequest {id=" + id +
                ", equipment=" + equipment +
                ", requester=" + requester +
                ", purpose='" + purpose + "'" +
                ", requestedAt=" + requestedAt +
                ", requestType=" + requestType +
                "}";
    }
}
