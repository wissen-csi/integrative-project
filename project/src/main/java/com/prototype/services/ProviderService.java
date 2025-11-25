package com.prototype.services;

import java.util.List;

import com.prototype.model.daos.ProviderDAO;
import com.prototype.model.entities.Provider;
import com.prototype.model.helpers.IoManager;

/**
 * Service class that manages business logic related to {@link Provider} entities.
 * <p>
 * This class performs validations and coordinates communication with the
 * {@link ProviderDAO} to ensure safe and consistent data operations.
 */
public class ProviderService {

    /** DAO instance used to access provider data. */
    private ProviderDAO providerDAO;

    /**
     * Constructs a new {@code ProviderService} and initializes its DAO dependency.
     */
    public ProviderService() {
        this.providerDAO = new ProviderDAO();
    }

    /**
     * Retrieves all registered providers.
     *
     * @return a list of all {@link Provider} entities.
     */
    public List<Provider> findAll() {
        return providerDAO.findAll();
    }

    /**
     * Creates and saves a new {@link Provider}.
     * <p>
     * Validates all required fields before persisting the new entity.
     *
     * @param name          the provider's name; must not be blank.
     * @param taxId         the tax identification number; must not be blank.
     * @param contactEmail  the provider's email; must be valid.
     * @param address       the provider's physical address; must not be blank.
     * @return the newly created {@link Provider}.
     * @throws IllegalArgumentException if any parameter does not pass validation.
     */
    public Provider createProvider(String name, String taxId, String contactEmail, String address) {
        // Validación genérica
        IoManager.requireNotBlank(name, "Nombre");
        IoManager.requireNotBlank(taxId, "Tax ID");
        IoManager.requireValidEmail(contactEmail);
        IoManager.requireNotBlank(address, "Dirección");

        Provider provider = new Provider();
        provider.setName(name);
        provider.setTaxId(taxId);
        provider.setContactEmail(contactEmail);
        provider.setAddress(address);

        return this.providerDAO.save(provider);
    }

    /**
     * Finds a {@link Provider} by its unique identifier.
     *
     * @param id the provider's ID; must not be null.
     * @return the found {@link Provider}.
     * @throws IllegalArgumentException if the ID is null.
     * @throws javax.persistence.EntityNotFoundException if the provider does not exist.
     */
    public Provider findById(Long id) {

        IoManager.requireNotNull(id, "ID");

        Provider provider = providerDAO.findById(id);

        IoManager.requireExists(provider, "Provider", id);

        return provider;
    }

    /**
     * Updates an existing {@link Provider}'s information.
     *
     * @param id          the provider's ID; must not be null.
     * @param newName     new provider name; must not be blank.
     * @param newTaxId    new tax ID; must not be blank.
     * @param newEmail    new email; must be valid.
     * @param newAddress  new address; must not be blank.
     * @return the updated {@link Provider}.
     * @throws IllegalArgumentException if validations fail.
     * @throws javax.persistence.EntityNotFoundException if the provider does not exist.
     */
    public Provider updateProvider(Long id, String newName, String newTaxId, String newEmail, String newAddress) {
        IoManager.requireNotNull(id, "ID");

        Provider provider = providerDAO.findById(id);

        IoManager.requireExists(provider, "Provider", id);

        // Validaciones genéricas
        IoManager.requireNotBlank(newName, "Nombre");
        IoManager.requireNotBlank(newTaxId, "Tax ID");
        IoManager.requireValidEmail(newEmail);
        IoManager.requireNotBlank(newAddress, "Dirección");

        provider.setName(newName);
        provider.setTaxId(newTaxId);
        provider.setContactEmail(newEmail);
        provider.setAddress(newAddress);

        return providerDAO.update(provider);
    }

    /**
     * Removes a {@link Provider} by its ID.
     *
     * @param id the ID of the provider to remove; must not be null.
     * @return the removed {@link Provider} instance.
     * @throws IllegalArgumentException if the ID is null.
     * @throws javax.persistence.EntityNotFoundException if no provider exists with the given ID.
     */
    public Provider removeProvider(Long id) {

        IoManager.requireNotNull(id, "ID");

        Provider provider = providerDAO.findById(id);

        IoManager.requireExists(provider, "Provider", id);

        return providerDAO.delete(id);
    }

}

