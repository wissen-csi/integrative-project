package com.prototype.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.prototype.model.enums.Role;

/**
 * La clase {@code Person} representa a una persona registrada en el sistema del
 * hospital,
 * que puede desempeñar diferentes roles dentro de los procesos de control de
 * ingreso
 * y registro de equipos tecnológicos o biomédicos.
 * 
 * <p>
 * Esta entidad forma parte del dominio del sistema y modela la información
 * básica
 * de una persona: su identificador, nombre completo, documento y rol
 * institucional.
 * </p>
 * 
 * <p>
 * Es una clase tipo POJO (Plain Old Java Object), ya que contiene únicamente
 * atributos,
 * constructores, getters, setters y un método {@code toString()} para mostrar
 * su información.
 * </p>
 * 
 * @author
 * @version 1.0
 */
@Entity
@Table(name="people")
public class Person {

    // ===================== ATRIBUTOS =====================

    /**
     * Identificador único de la persona.
     * Se genera automáticamente mediante {@link UUIDGenerator} si no se pasa por
     * parámetro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo de la persona.
     */
    @Column(name = "full_name",nullable = false)
    private String fullName;

    /**
     * Documento de identidad de la persona (cédula o equivalente).
     * Este valor debe ser único dentro del sistema.
     */
    @Column(nullable = false)
    private String document;

    /**
     * Rol de la persona dentro del sistema (ej. ADMIN, DOCTOR, NURSE, WATCHMAN,
     * etc.).
     * Definido por el enumerado {@link Role}.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "Person",cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.REMOVE})
    private List<Equipment> equipments;

    // ===================== CONSTRUCTORES =====================

    /**
     * Constructor completo para inicializar una persona con un ID predefinido.
     * 
     * @param id       identificador único de la persona.
     * @param fullName nombre completo.
     * @param document documento de identidad.
     * @param role     rol asignado dentro del sistema.
     */
    public Person(){}
    public Person(Long id, String fullName, String document, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.document = document;
        this.role = role;
    }

    /**
     * Constructor que genera automáticamente el ID de la persona usando
     * {@link UUIDGenerator}.
     * 
     * @param fullName nombre completo.
     * @param document documento de identidad.
     * @param role     rol asignado dentro del sistema.
     */
    public Person(String fullName, String document, Role role) {
        this.id = null;
        this.fullName = fullName;
        this.document = document;
        this.role = role;
    }

    // ===================== GETTERS Y SETTERS =====================

    /**
     * Obtiene el identificador único de la persona.
     * 
     * @return id de la persona.
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre completo de la persona.
     * 
     * @return nombre completo.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Actualiza el nombre completo de la persona.
     * 
     * @param fullName nuevo nombre completo.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Obtiene el número de documento de identidad.
     * 
     * @return documento de identidad.
     */
    public String getDocument() {
        return document;
    }

    /**
     * Actualiza el número de documento de identidad.
     * 
     * @param document nuevo número de documento.
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Obtiene el rol de la persona dentro del sistema.
     * 
     * @return rol de tipo {@link Role}.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Actualiza el rol de la persona dentro del sistema.
     * 
     * @param role nuevo rol de tipo {@link Role}.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    // ===================== MÉTODO toString =====================

    /**
     * Retorna una representación en texto de la persona con sus datos principales.
     * 
     * @return cadena legible con los valores de los atributos.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("La persona tiene los siguientes datos: ").append("\n");
        stringBuilder.append("id: ").append(id).append("\n");
        stringBuilder.append("fullName: ").append(fullName).append("\n");
        stringBuilder.append("document: ").append(document).append("\n");
        stringBuilder.append("role: ").append(role).append("\n");
        return stringBuilder.toString();

    }

}
