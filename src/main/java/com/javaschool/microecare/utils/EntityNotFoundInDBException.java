package com.javaschool.microecare.utils;

import javax.persistence.EntityNotFoundException;

public class EntityNotFoundInDBException extends EntityNotFoundException {
    long id;
    String entityName;

    public EntityNotFoundInDBException(String message) {
        super(message);
    }

    public EntityNotFoundInDBException() {
        super();
    }

    public EntityNotFoundInDBException(long id, String entityName) {
        super(String.format("%s with id %d isn't found", entityName, id));
        this.id = id;
        this.entityName = entityName;
    }

    public long getId() {
        return id;
    }

    public String getEntityName() {
        return entityName;
    }
}
