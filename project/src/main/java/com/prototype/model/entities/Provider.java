package com.prototype.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing an equipment provider registered within the system.
 * 
 * <p>A provider supplies technological or biomedical equipment, and this
 * entity stores its basic business information such as name, tax ID, contact
 * email, and address.</p>
 *
 * <p>Each provider may be associated with multiple {@link Equipment}
 * instances, forming a one-to-many relationship.</p>
 */
@Entity
@Table(name = "providers")
public class Provider {

    /**
     * Automatically generated identifier for the provider.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Legal or commercial name of the provider.
     */
    @Column(nullable = false, length = 45)
    private String name;

    /**
     * Tax identification number of the provider.
     *
     * <p>Typically corresponds to the RUC, NIT, or equivalent tax registry
     * depending on the country.</p>
     */
    @Column(name = "tax_id", nullable = false, length = 13)
    private String taxId;

    /**
     * Contact email used for communication with the provider.
     */
    @Column(name = "contact_email", nullable = false, length = 150)
    private String contactEmail;

    /**
     * List of equipment items supplied by this provider.
     *
     * <p>The cascade configuration allows merge, refresh, and remove operations
     * to propagate to the associated equipment records.</p>
     */
    @OneToMany(mappedBy = "provider", cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    private List<Equipment> equipments;

    /**
     * Physical or administrative address of the provider.
     */
    @Column(nullable = false, length = 100)
    private String address;

    /**
     * Default constructor required by JPA.
     */
    public Provider() {
    }

    /**
     * Deprecated full constructor including the ID.
     *
     * <p>Retained for compatibility. New provider records should rely on the
     * constructor where the ID is managed by the persistence layer.</p>
     *
     * @param id        provider ID
     * @param name      provider's legal or commercial name
     * @param taxId     provider's tax identification number
     * @param contact   provider's contact email
     * @param address   provider's address
     */
    @Deprecated
    public Provider(Long id, String name, String taxId, String contact, String address) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.contactEmail = contact;
        this.address = address;
        this.equipments = new ArrayList<>();
    }

    /**
     * Constructor used to create new providers without specifying an ID.
     * The ID will be automatically generated.
     *
     * @param name      provider's legal name
     * @param taxId     tax identification number
     * @param contact   contact email
     * @param address   provider address
     */
    public Provider(String name, String taxId, String contact, String address) {
        this.id = null;
        this.name = name;
        this.taxId = taxId;
        this.contactEmail = contact;
        this.address = address;
        this.equipments = new ArrayList<>();
    }

    /** @return provider ID. */
    public Long getId() {
        return id;
    }

    /** @param id sets the provider ID. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return provider name. */
    public String getName() {
        return name;
    }

    /** @param name sets the provider name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return tax identification number. */
    public String getTaxId() {
        return taxId;
    }

    /** @param taxId sets the tax identification number. */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /** @return provider contact email. */
    public String getContactEmail() {
        return contactEmail;
    }

    /** @param contactEmail sets the provider contact email. */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /** @return list of equipment supplied by the provider. */
    public List<Equipment> getEquipments() {
        return equipments;
    }

    /** @param equipments sets the list of supplied equipment. */
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    /** @return provider address. */
    public String getAddress() {
        return address;
    }

    /** @param address sets the provider address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns a textual representation of the provider including its basic
     * attributes and associated equipment list.
     */
    @Override
    public String toString() {
        return "Provider {id=" + id + 
           ", name=" + name + 
           ", taxId=" + taxId + 
           ", contactEmail=" + contactEmail + 
           ", equipments=" + equipments + 
           ", address=" + address + 
           "}";
    }

}
