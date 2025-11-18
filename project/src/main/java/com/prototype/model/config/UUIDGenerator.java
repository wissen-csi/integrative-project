package com.prototype.model.config;

import java.util.UUID;

/**
 * Generator of unique identifiers based on the
 * uuid v4 estandar }
 * 
 * @author Salomón Valero Bejarano, David Camilo Preciado
 */
public class UUIDGenerator {

     /**
      * Generate a unique identifier.
      * 
      * @return String ID in uuid v4 format
      */

     public static String generate() {
          UUID uuid = UUID.randomUUID();
          return uuid.toString();
     }

     public static UUID parseToUuid(String id) {
          return UUID.fromString(id);
     }

}
