package com.example.pedro.myapplication.backend;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonCreator;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Bike.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Bike object
 */

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long idPlace;

    //Contructors

    public Bike() {

    }

    public Bike(long id, long idPlace) {
        this.id = id;
        this.idPlace = idPlace;
    }

    public Bike(long idPlace) {
        this.idPlace = idPlace;
    }

    //getters

    public long getId() {
        return id;
    }

    public long getIdPlace() {
        return idPlace;
    }

    //setters

    public void setIdPlace(long idPlace) {
        this.idPlace = idPlace;
    }
}
