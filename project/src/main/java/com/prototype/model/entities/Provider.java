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

import com.prototype.model.config.UUIDGenerator;

/**
 * La clase {@code Provider} representa a un proveedor dentro del sistema del hospital.
 * 
 * <p>
 * Los proveedores son las entidades externas responsables de suministrar equipos, 
 * repuestos o servicios relacionados con los dispositivos tecnológicos o biomédicos 
 * registrados en el inventario.
 * </p>
 * 
 * <p>
 * Esta clase es un POJO (Plain Old Java Object), ya que contiene únicamente atributos,
 * constructores, getters, setters y un método {@code toString()} para representar
 * la información del proveedor de forma legible.
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@Entity
@Table(name ="providers")
public class Provider {

    // ===================== ATRIBUTOS =====================

    /**
     * Identificador único del proveedor.
     * Se genera automáticamente con {@link UUIDGenerator} si no se pasa por parámetro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del proveedor o empresa.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Identificador tributario del proveedor (por ejemplo, NIT o RUT).
     * Se genera concatenando un prefijo "taxId-" con un UUID aleatorio.
     */
    @Column(name = "tax_id", nullable = false)
    private String taxId;

    /**
     * Correo electrónico de contacto del proveedor.
     */
    @Column(name = "contact_email",nullable = false)
    private String contactEmail;
    @OneToMany(mappedBy = "Provider",cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Equipment> equipments;

    // ===================== CONSTRUCTORES =====================
    public Provider(){
    }
    /**
     * Constructor completo para inicializar un proveedor con un ID definido.
     * 
     * @param id identificador único del proveedor.
     * @param name nombre del proveedor.
     * @param taxId identificador tributario (NIT o equivalente).
     * @param contact correo electrónico de contacto.
     */
    public Provider(Long id, String name, String taxId, String contact) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.contactEmail = contact;
        this.equipments = new ArrayList<>();
    }

    /**
     * Constructor que genera automáticamente el ID del proveedor y su taxId.
     * 
     * @param name nombre del proveedor.
     * @param taxId identificador tributario (NIT o equivalente).
     * @param contact correo electrónico de contacto.
     */
    public Provider(String name, String taxId, String contact) {
        this.id = null;
        this.name = name;
        this.taxId = "taxId-" + UUIDGenerator.generate();
        this.contactEmail = contact;
    }

    // ===================== GETTERS Y SETTERS =====================

    /**
     * Obtiene el identificador único del proveedor.
     * 
     * @return id del proveedor.
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del proveedor.
     * 
     * @return nombre del proveedor.
     */
    public String getName() {
        return name;
    }

    /**
     * Actualiza el nombre del proveedor.
     * 
     * @param name nuevo nombre del proveedor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el identificador tributario del proveedor.
     * 
     * @return código tributario generado o asignado.
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Obtiene el correo electrónico de contacto del proveedor.
     * 
     * @return dirección de correo electrónico.
     */
    public String getContact() {
        return contactEmail;
    }

    // ===================== MÉTODO toString =====================

    /**
     * Retorna una representación en texto del proveedor con sus datos principales.
     * 
     * @return cadena formateada con la información del proveedor.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("El proveedor tiene los siguientes datos: ").append("\n");
        builder.append("id: ").append(id).append("\n");
        builder.append("name: ").append(name).append("\n");
        builder.append("taxId: ").append(taxId).append("\n");
        builder.append("contactEmail: ").append(contactEmail).append("\n");
        return builder.toString(); // se llama al objeto y se convierte a String con toString()
    }
}
