package com.prototype.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;

/**
 * La clase {@code BiomedicalEquipment} representa un equipo biomédico dentro del
 * sistema de inventario del hospital.
 * 
 * <p>
 * Hereda de {@link Equipment} y añade atributos específicos para los equipos de tipo
 * biomédico, tales como la clase de riesgo y el certificado de calibración.
 * </p>
 * 
 * <p>
 * Esta clase permite distinguir los equipos médicos que requieren un control técnico
 * y normativo más estricto, de los equipos tecnológicos u otros tipos.
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@Entity
@Table(name= "biomedical_equiments")
public class BiomedicalEquipment extends Equipment {

    // ===================== ATRIBUTOS =====================

    /**
     * Clase de riesgo del equipo biomédico (según normativa hospitalaria o sanitaria).
     */
    @Column(name = "risk_class")
    private String riskClass;

    /**
     * Certificado de calibración asociado al equipo biomédico.
     */
    @Column(name ="calibration_cert")
    private String calibrationCert;

    // ===================== CONSTRUCTORES =====================

    /**
     * Constructor completo con ID incluido.
     * 
     * @param id identificador único del equipo.
     * @param serial número de serie del equipo.
     * @param brand marca del equipo.
     * @param model modelo del equipo.
     * @param type tipo de equipo (biomédico).
     * @param state estado del equipo (ver {@link EquipmentStatus}).
     * @param provider proveedor asociado.
     * @param imagePath ruta de la imagen del equipo.
     * @param riskClass clase de riesgo asignada.
     * @param calibrationCert número o código del certificado de calibración.
     */

    public BiomedicalEquipment(Long id, String serial, String brand, String model,
                               EquipmentType type, EquipmentStatus state, Provider provider,
                               String imagePath, String riskClass, String calibrationCert) {
        super(id, serial, brand, model, type, state, provider, imagePath);
        this.riskClass = riskClass;
        this.calibrationCert = calibrationCert;
    }

    /**
     * Constructor que genera automáticamente el ID.
     * 
     * @param serial número de serie del equipo.
     * @param brand marca del equipo.
     * @param model modelo del equipo.
     * @param type tipo de equipo biomédico.
     * @param state estado actual del equipo.
     * @param provider proveedor asociado.
     * @param imagePath ruta de la imagen del equipo.
     * @param riskClass clase de riesgo asignada.
     * @param calibrationCert número o código del certificado de calibración.
     */
    public BiomedicalEquipment(String serial, String brand, String model, EquipmentType type,
                               EquipmentStatus state, Provider provider, String imagePath,
                               String riskClass, String calibrationCert) {
        super(serial, brand, model, type, state, provider, imagePath);
        this.riskClass = riskClass;
        this.calibrationCert = calibrationCert;
    }

    // ===================== GETTERS Y SETTERS =====================

    /**
     * Obtiene la clase de riesgo del equipo biomédico.
     * 
     * @return clase de riesgo.
     */
    public String getRiskClass() {
        return riskClass;
    }

    /**
     * Actualiza la clase de riesgo del equipo biomédico.
     * 
     * @param riskClass nueva clase de riesgo.
     */
    public void setRiskClass(String riskClass) {
        this.riskClass = riskClass;
    }

    /**
     * Obtiene el certificado de calibración del equipo.
     * 
     * @return número o código del certificado.
     */
    public String getCalibrationCert() {
        return calibrationCert;
    }

    /**
     * Actualiza el certificado de calibración del equipo.
     * 
     * @param calibrationCert nuevo número o código de certificado.
     */
    public void setCalibrationCert(String calibrationCert) {
        this.calibrationCert = calibrationCert;
    }

    // ===================== toString =====================

    /**
     * Retorna una representación textual del equipo biomédico,
     * mostrando su clase de riesgo y certificado de calibración.
     * 
     * @return cadena con los datos específicos del equipo biomédico.
     */
    @Override
    public String toString() {
        return "BiomedicalEquipment [riskClass=" + riskClass + ", calibrationCert=" + calibrationCert + "]";
    }
}
