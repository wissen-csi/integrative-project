package com.prototype.model.helpers;

/**
 * Utilidad que centraliza los mensajes estándar utilizados en operaciones
 * CRUD dentro de la aplicación.
 *
 * <p>Esta clase proporciona constantes para mensajes comunes de creación,
 * actualización y eliminación, permitiendo mantener uniformidad en la
 * retroalimentación entregada al usuario o al sistema.</p>
 *
 * <p>La clase no debe instanciarse, ya que sólo contiene constantes.</p>
 */
public class OperationMessageManager {

    /** Mensaje estándar para indicar una creación exitosa. */
    public static final String CREATED_SUCCESS = "Se creó correctamente: ";

    /** Mensaje estándar para indicar una actualización exitosa. */
    public static final String UPDATED_SUCCESS = "Se actualizó correctamente: ";

    /** Mensaje estándar para indicar una eliminación exitosa. */
    public static final String DELETED_SUCCESS = "Se eliminó correctamente: ";
}
