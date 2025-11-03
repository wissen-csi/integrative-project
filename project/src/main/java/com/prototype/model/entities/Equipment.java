package com.prototype.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.prototype.model.apis.ApiCloudinary;
import com.prototype.model.config.UUIDGenerator;
import com.prototype.model.enums.EquipmentStatus;
import com.prototype.model.enums.EquipmentType;


/**
 * La clase Equipment representa un objeto de tipo equipo.
 * Esta clase será reconocida como la clase padre, que tiene
 * por hijas a las clases TechEquipment y BiomedicalEquipment.
 * 
 * Adicional a este tipo de clase se le conoce como POJO que
 * significa Plain Old Java Object, ya que solo posee atributos
 * constructores, getters and setters y el método toString().
 * 
 * @author Salomón Valero Bejarano
 */
@Entity
@Table(name="equipments")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Equipment {
    protected static ApiCloudinary apiCloudinary = new ApiCloudinary();
    /**
     * Attributes
     * 
     * Los atributos serán privados, porque solo se pueden
     * modificar dentro de la clase. Si los intentamos actualizar
     * una vez creada la instancia, debemos utilizar métodos
     * setter, por ejemplo: El atributo serial es privado por lo 
     * tanto, solo se puede actualizar dentro del código de
     * la clase, o utilizando la función setSerial(), enviando
     * como parámetro el nuevo valor.
     * 
     *  */ 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serial;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;   
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentStatus state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_provider",nullable = true)
    private Provider provider;
    @Column(nullable = false)
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_persona",nullable = true)
    private Person person;
    @Column(name="code_qr")
    private String codeQR;

    /**
     *  Constructors 
     * 
     * Los constructores permiten crear una instancia u objeto
     * de la clase, por defecto se genera el constructor vacío,
     * pero podemos personalizarlo asignando valores por defecto
     * dentro del mismo.
     * 
     * También podemos hacer sobrecarga de constructores, para
     * aceptar ciertos valores al momento de crear la instancia
     * y asignarlos como valor inicial (inicializar) los atributos
     * de la clase.
     * 
     * */ 

     /** 
      * Contructor vacío de equipment

       @ params - Sin parámetros 
    
       */
    public Equipment(){}
    public Equipment(Long id,String serial, String brand, String model, EquipmentType type, 
    EquipmentStatus state, Provider provider, String imagePath) {
        this.id = id;
        this.serial = serial;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.state = state;
        this.provider = provider;
        this.imagePath = imagePath;
        this.codeQR = UUIDGenerator.generate();
    }
    /**
     * Contructor con parámetros de equipment
     * 
     * @param serial - registro serial del equipo
     * @param brand - marca del equipo
     * @param model - modelo del equipo
     * @param type -
     * @param state -
     * @param provider - Id del proovedor
     * @param imagePath - Ruta de la imagen cargada
     */

    public Equipment(String serial, String brand, String model, EquipmentType type, 
    EquipmentStatus state, Provider provider, String imagePath) {
        this.id = null;
        this.serial = serial;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.state = state;
        this.provider = provider;
        this.imagePath = imagePath;
        this.codeQR = UUIDGenerator.generate();
    }


    /**getters and setters:
     * 
     * Los getters permiten obtener el valor de un atributo,
     * Los setters permiten actualizar el valor de un atributo,
     * 
     * Ambos son muy útiles cuando trabajamos con atributos
     * cuya visibilidad sea private o protected.
     */

    /**
     * Método get que retorna el valor del Serial
     * 
     *  @return - String con el valor del serial
      */

        public Long getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    /** 
     * Método set que actualiza o modifica el valor almacenado
     * 
     * @param serial - String con el nuevo valor del serial.
     *  */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     *  Método get que retorna el valor del Brand.
     *  
     *  @return - String con el valor del Brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Método set que actualiza o modifica el valor almacenado  
     * @param brand - String con el nuevo valor del Brand.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     *  Método get que retorna el valor del Model.
     *  
     *  @return - String con el valor del Model
     */
    public String getModel() {
        return model;
    }

    /**
     * Método set que actualiza o modifica el valor almacenado
     * @param model - String con el nuevo valor del Model.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Método get que retorna el valor del Type.
     * @return - String con el valor del Type
     */
    public EquipmentType getType() {
        return type;
    }

    /**
     * Método set que actualiza o modifica el valor almacenado.
     * @param type - String con el nuevo valor del Type.
     */
    public void setType(EquipmentType type) {
        this.type = type;
    }

    /**
     * Método get que retorna el valor del State.
     * @return - String con el valor del State.
     */
    public EquipmentStatus getState() {
        return state;
    }

    /** 
     * Método set que actualiza o modifica el valor almacenado.
     * @param state - String con el nuevo valor del State.
     */
    public void setState(EquipmentStatus state) {
        this.state = state;
    }

    /**
     * Método get que retorna el valor del Provider.
     * @return - String con el valor del Provider.
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Método set que actualiza o modifica el valor almacenado.
     * @param provider - String con el nuevo valor del Provider.
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Método get que retorna el valor del ImagePath.
     * @return
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Método set que actualiza o modifica el valor almacenado.
     * @param imagePath - String con el nuevo valor del ImagePath.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /** 
     * El metodo toString() es un método que está definido
     * por java para mostrar el identificador de la ubicación 
     * de memoria en la que se encuentra una instancia u objeto.
     * 
     * Usamos el decorador @Override para sobreescribir la
     * funcionalidad original del método para lograr que
     * se pueda mostrar el contenido de la instancia de una manera
     * más clara al momento de leerlo.
     */

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", provider='" + provider + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

}
