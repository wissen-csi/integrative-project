package com.prototype.model.helpers;

/**
 * Utility class providing common validation methods for input data.
 *
 * <p>
 * This class centralizes validation logic used across the application to
 * ensure consistency and reduce code duplication.
 * It performs checks for null values, empty fields, email format validation,
 * and entity existence verification.
 * </p>
 *
 * <p>
 * All methods are static, allowing them to be used without instantiating
 * the class.
 * </p>
 *
 * @author Jorge Forero
 * @version 1.0
 */
public class IoManager {

    /**
     * Validates that a text value is neither {@code null} nor blank.
     *
     * @param fieldValue the value to validate
     * @param fieldName  the name of the field used in error messages
     * @throws IllegalArgumentException if the value is null or blank
     */
    public static void requireNotBlank(String fieldValue, String fieldName) {
        if (fieldValue == null || fieldValue.isBlank()) {
            throw new IllegalArgumentException(
                    ErrorMessageManager.FIELD_REQUIRED + fieldName);
        }
    }

    /**
     * Validates that an object is not {@code null}.
     *
     * @param value     the object to check
     * @param fieldName the name of the validated field
     * @throws IllegalArgumentException if the object is null
     */
    public static void requireNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(
                    ErrorMessageManager.FIELD_REQUIRED + fieldName);
        }
    }

    /**
     * Performs a basic validation of an email address.
     *
     * <p>
     * This method checks that the email is not blank and contains the
     * characters {@code @} and {@code .}, ensuring a minimal valid structure.
     * </p>
     *
     * @param email the email address to validate
     * @throws IllegalArgumentException if the email is null, blank,
     *                                  or does not match a basic format
     */
    public static void requireValidEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    ErrorMessageManager.FIELD_REQUIRED + "Email");
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException(
                    ErrorMessageManager.INVALID_FORMAT + "Email");
        }
    }

    /**
     * Validates that an entity exists in the database.
     *
     * <p>
     * This method is typically used after performing a lookup by ID.
     * </p>
     *
     * @param entity    the entity returned from the database
     * @param fieldName the name of the field being validated
     * @param id        the identifier used in the lookup
     * @throws IllegalArgumentException if the entity is {@code null}
     */
    public static void requireExists(Object entity, String fieldName, Object id) {
        if (entity == null) {
            throw new IllegalArgumentException(
                    ErrorMessageManager.ID_NOT_FOUND + id);
        }
    }

}
