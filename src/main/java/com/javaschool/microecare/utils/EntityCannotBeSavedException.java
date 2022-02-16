package com.javaschool.microecare.utils;

import org.springframework.dao.DataIntegrityViolationException;

//TODO: подумать, куда положить этот эксепшн

public class EntityCannotBeSavedException extends DataIntegrityViolationException {
    private String entityName;


    public EntityCannotBeSavedException(String msg) {
        super(msg);
    }

    public EntityCannotBeSavedException(String entityName, String msg) {
        this(msg);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
