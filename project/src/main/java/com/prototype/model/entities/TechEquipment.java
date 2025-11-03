package com.prototype.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;

/**
 * La clase {@code TechEquipment} representa un equipo tecnológico dentro del sistema
 * de inventario del hospital. 
 * 
 * <p>
 * Hereda de la clase {@link Equipment}, y añade información específica sobre los equipos
 * de tipo tecnológico, tales como su sistema operativo y la cantidad de memoria RAM.
 * </p>
 * 
 * <p>
 * Este tipo de clase forma parte de la jerarquía de herencia de {@code Equipment},
 * donde cada subclase (como {@code BiomedicalEquipment}) especializa atributos propios
 * según su categoría.
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@Entity
@Table(name="Tech_equipments")
public class TechEquipment extends Equipment {

    // ===================== ATRIBUTOS =====================

    /**
     * Sistema operativo instalado en el equipo tecnológico.
     */
    @Column(nullable = false)
    private String os;

    /**
     * Cantidad de memoria RAM en gigabytes.
     */
    @Column(nullable = false)
    private int ramGB;

    // ===================== CONSTRUCTORES =====================

    /**
     * Constructor con todos los parámetros, incluyendo el ID del equipo.
     * 
     * @param id identificador único del equipo.
     * @param serial número de serie del equipo.
     * @param brand marca del equipo.
     * @param model modelo del equipo.
     * @param type tipo de equipo tecnológico (definido en {@link EquipmentType}).
     * @param state estado actual del equipo (definido en {@link EquipmentStatus}).
     * @param provider proveedor asociado.
     * @param imagePath ruta de la imagen del equipo.
     * @param os sistema operativo del equipo.
     * @param ramGB cantidad de memoria RAM en GB.
     */
    public TechEquipment(){}
    public TechEquipment(Long id, String serial, String brand, String model,
                         EquipmentType type, EquipmentStatus state, Provider provider,
                         String imagePath, String os, int ramGB) {
    super(id, serial, brand, model, type, state, provider, imagePath);
    this.os = os;
    this.ramGB = ramGB;
    }

    /**
     * Constructor que genera el ID automáticamente (desde {@link Equipment}).
     * 
     * @param serial número de serie del equipo.
     * @param brand marca del equipo.
     * @param model modelo del equipo.
     * @param type tipo de equipo tecnológico.
     * @param state estado actual del equipo.
     * @param provider proveedor asociado.
     * @param imagePath ruta de la imagen del equipo.
     * @param os sistema operativo del equipo.
     * @param ramGB cantidad de memoria RAM en GB.
     */
     public TechEquipment(String serial, String brand, String model, EquipmentType type,
                         EquipmentStatus state, Provider provider, String imagePath,
                         String os, int ramGB) {
        super(serial, brand, model, type, state, provider, imagePath);
        this.os = os;
        this.ramGB = ramGB;
    }

    // ===================== GETTERS Y SETTERS =====================

    /**
     * Obtiene el sistema operativo del equipo.
     * 
     * @return nombre del sistema operativo.
     */
    public String getOs() {
        return os;
    }

    /**
     * Actualiza el sistema operativo del equipo.
     * 
     * @param os nuevo sistema operativo.
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * Obtiene la cantidad de memoria RAM instalada.
     * 
     * @return cantidad de RAM en GB.
     */
    public int getRamGB() {
        return ramGB;
    }

    /**
     * Actualiza la cantidad de memoria RAM del equipo.
     * 
     * @param ramGB nueva cantidad de RAM en GB.
     */
    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

    // ===================== toString =====================

    /**
     * Retorna una representación textual del equipo tecnológico,
     * mostrando el sistema operativo y la memoria RAM.
     * 
     * @return cadena con los datos específicos del equipo tecnológico.
     */
    @Override
    public String toString() {
        return "TechEquipment [os=" + os + ", ramGB=" + ramGB + "]";
    }
}
