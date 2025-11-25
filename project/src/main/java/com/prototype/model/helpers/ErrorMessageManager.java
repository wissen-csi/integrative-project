package com.prototype.model.helpers;

/**
 * Utility class that provides standardized error messages used throughout
 * the application.
 *
 * <p>
 * This class centralizes common validation and exception messages, ensuring
 * consistency across different layers of the system.
 * All fields are {@code public static final}, making them accessible
 * without creating an instance of this class.
 * </p>
 *
 * @author Jorge Forero
 * @version 1.0
 */
public class ErrorMessageManager {

    /** Message indicating that a required field was not provided. */
    public static final String FIELD_REQUIRED = "El campo es obligatorio: ";

    /** Generic message used when an unexpected system error occurs. */
    public static final String UNEXPECTED_ERROR = "Ha ocurrido un error inesperado.";

    /** Message used when a field contains an invalid format. */
    public static final String INVALID_FORMAT = "El formato es inválido para el campo: ";

    /** Message indicating that an ID value is required but missing. */
    public static final String ID_REQUIRED = "El ID es obligatorio.";

    /** Message shown when no record exists for the specified ID. */
    public static final String ID_NOT_FOUND = "No existe un registro asociado a ese ID";

}
