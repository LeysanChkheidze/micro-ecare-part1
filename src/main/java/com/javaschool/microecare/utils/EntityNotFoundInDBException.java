package com.javaschool.microecare.utils;

import javax.persistence.EntityNotFoundException;

/**
 * The type EntityNotFoundInDBException extends EntityNotFoundException and additionally stores id of the searched entity and entity name.
 */
public class EntityNotFoundInDBException extends EntityNotFoundException {
    /**
     * The Id.
     */
    long id;
    /**
     * The Entity name.
     */
    String entityName;

    /**
     * Instantiates a new EntityNotFoundInDBException.
     *
     * @param message the exception message
     */
    public EntityNotFoundInDBException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Entity not found in db exception.
     */
    public EntityNotFoundInDBException() {
        super();
    }

    /**
     * Instantiates a new Entity not found in db exception.
     *
     * @param id         the id of entity which wasn't found
     * @param entityName the name of entity which wasn't found
     */
    public EntityNotFoundInDBException(long id, String entityName) {
        super(String.format("%s with id %d isn't found", entityName, id));
        this.id = id;
        this.entityName = entityName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets entity name.
     *
     * @return the entity name
     */
    public String getEntityName() {
        return entityName;
    }
}
