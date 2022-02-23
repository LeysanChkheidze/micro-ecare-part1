package com.javaschool.microecare.commonentitymanagement.dao;

import org.springframework.dao.DataIntegrityViolationException;


/**
 * The type EntityCannotBeSavedException which extends DataIntegrityViolationException and additionally has a field to store entity name.
 */
public class EntityCannotBeSavedException extends DataIntegrityViolationException {
    private String entityName;


    /**
     * Instantiates a new Entity cannot be saved exception.
     *
     * @param msg the exception message
     */
    public EntityCannotBeSavedException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Entity cannot be saved exception.
     *
     * @param entityName the entity name for which exception was thrown in human-readable form
     * @param msg        the exception message
     */
    public EntityCannotBeSavedException(String entityName, String msg) {
        this(msg);
        this.entityName = entityName;
    }

    /**
     * Gets entity name.
     *
     * @return the entity name
     */
    public String getEntityName() {
        return entityName;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
