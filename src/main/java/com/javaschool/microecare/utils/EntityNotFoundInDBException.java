package com.javaschool.microecare.utils;

import javax.persistence.EntityNotFoundException;

public class EntityNotFoundInDBException extends EntityNotFoundException {
    int id;
    String entityName;

    public EntityNotFoundInDBException(String message) {
        super(message);
    }

    public EntityNotFoundInDBException() {
        super();
    }

    public EntityNotFoundInDBException(int id, String entityName) {
        super(String.format("%s with id %d isn't found", entityName, id));
        this.id = id;
        this.entityName = entityName;
    }

    public int getId() {
        return id;
    }

    public String getEntityName() {
        return entityName;
    }
}
