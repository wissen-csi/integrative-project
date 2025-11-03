package com.prototype.model.config;
import java.util.UUID;

/**
 * Generador de identificadores únicos basados en el
 * estandar uuid v4 }
 * 
 * @author Salomón Valero Bejarano
 */
public class UUIDGenerator {

     /**
      * Genera un identificador único.
      * 
      * @return Id de tipo string en formato uuid v4
      */

     public static String generate() {
          UUID uuid = UUID.randomUUID();
          return uuid.toString();
     }

     public static UUID parseToUuid(String id) {
          return UUID.fromString(id);
     }

}
