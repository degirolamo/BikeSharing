package com.example.pedro.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Town.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Town object
 */

@Entity
public class Town {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private int npa;
    private long idCanton;

    // contructors

    public Town() {

    }

    public  Town(String name, int npa, long idCanton) {
        this.name = name;
        this.npa = npa;
        this.idCanton = idCanton;
    }

    public  Town(long id, String name, int npa, long idCanton){
        this.id = id;
        this.name = name;
        this.npa = npa;
        this.idCanton = idCanton;
    }

    @Override
    public String toString() {
        return getNpa() + " " + getName();
    }

    // getters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNpa() {
        return npa;
    }

    public long getIdCanton() {
        return idCanton;
    }

    // setters

    public void setId(long id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setNpa(int npa) {
        this.npa = npa;
    }

    public void setIdCanton(long idCanton) {
        this.idCanton = idCanton;
    }
}

