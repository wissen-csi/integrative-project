package com.prototype.services;

import java.util.List;

import com.prototype.model.daos.PersonDAO;
import com.prototype.model.entities.Person;
import com.prototype.model.enums.Role;
import com.prototype.model.helpers.IoManager;

/**
 * Service class that handles business logic related to {@link Person} entities.
 * <p>
 * This class acts as an intermediary between the presentation layer and the
 * data access layer, performing validations and ensuring data integrity before
 * interacting with {@link PersonDAO}.
 */
public class PersonService {

    /** Data access object for Person entities. */
    private final PersonDAO personDAO;

    /**
     * Creates a new instance of {@code PersonService}.
     * Initializes the internal {@link PersonDAO}.
     */
    public PersonService() {
        this.personDAO = new PersonDAO();
    }

    /**
     * Retrieves all registered persons.
     *
     * @return a list of all {@link Person} entities.
     */
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    /**
     * Creates and saves a new {@link Person} in the system.
     * <p>
     * Performs validation for required fields before persisting.
     *
     * @param fullName the full name of the person; must not be blank.
     * @param document the identification document; must not be blank.
     * @param role     the role assigned to the person; must not be null.
     * @return the created {@link Person} instance.
     * @throws IllegalArgumentException if any validation fails.
     */
    public Person createPerson(String fullName, String document, Role role) {

        // Validaciones genéricas
        IoManager.requireNotBlank(fullName, "Nombre completo");
        IoManager.requireNotBlank(document, "Documento");
        IoManager.requireNotNull(role, "Rol de la persona");

        Person person = new Person();
        person.setFullName(fullName);
        person.setDocument(document);
        person.setRole(role);

        return personDAO.save(person);
    }

    /**
     * Finds a {@link Person} by its unique ID.
     *
     * @param id the identifier of the person; must not be null.
     * @return the found {@link Person}.
     * @throws IllegalArgumentException if the ID is null.
     * @throws javax.persistence.EntityNotFoundException if no person is found with the given ID.
     */
    public Person findById(Long id) {

        IoManager.requireNotNull(id, "ID de la persona");

        Person person = personDAO.findById(id);

        IoManager.requireExists(person, "Person", id);

        return person;
    }

    /**
     * Updates an existing {@link Person}'s information.
     *
     * @param id           the ID of the person to update; must not be null.
     * @param newFullName  new full name; must not be blank.
     * @param newDocument  new identification document; must not be blank.
     * @param newRole      new role; must not be null.
     * @return the updated {@link Person}.
     * @throws IllegalArgumentException if any validation fails.
     * @throws javax.persistence.EntityNotFoundException if the person does not exist.
     */
    public Person updatePerson(Long id, String newFullName, String newDocument, Role newRole) {

        IoManager.requireNotNull(id, "ID de la persona");

        Person person = personDAO.findById(id);
        IoManager.requireExists(person, "Person", id);

        // Validaciones de los nuevos datos
        IoManager.requireNotBlank(newFullName, "Nombre completo");
        IoManager.requireNotBlank(newDocument, "Documento");
        IoManager.requireNotNull(newRole, "Rol de la persona");

        person.setFullName(newFullName);
        person.setDocument(newDocument);
        person.setRole(newRole);

        return personDAO.update(person);
    }

    /**
     * Deletes a {@link Person} by its ID.
     *
     * @param id the ID of the person to delete; must not be null.
     * @throws IllegalArgumentException if the ID is null.
     * @throws javax.persistence.EntityNotFoundException if no person exists with the given ID.
     */
    public void deletePerson(Long id) {

        IoManager.requireNotNull(id, "ID de la persona");

        Person person = personDAO.findById(id);
        IoManager.requireExists(person, "Person", id);

        personDAO.delete(id);
    }

}
