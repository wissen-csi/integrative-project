package com.prototype.model.config;

import java.util.UUID;

/**
 * Utility class for generating and handling unique identifiers using the
 * UUID v4 standard. Provides methods for creating random UUID strings and
 * converting string values back into UUID objects.
 */
public class UUIDGenerator {

     /**
      * Generates a unique identifier following the UUID v4 standard.
      *
      * @return a randomly generated UUID in string format
      */
     public static String generate() {
          UUID uuid = UUID.randomUUID();
          return uuid.toString();
     }

     /**
      * Converts a string representation of a UUID into a {@link UUID} object.
      *
      * @param id the string value to convert
      * @return the corresponding {@link UUID} instance
      * @throws IllegalArgumentException if the string is not a valid UUID format
      */
     public static UUID parseToUuid(String id) {
          return UUID.fromString(id);
     }

}
