package com.example.pedro.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Rent.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Rent object
 */

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long idBike;
    private long idPerson;
    private String beginDate;
    private String endDate;

    public Rent() {

    }

    public Rent(long idBike, long idPerson, String beginDate, String endDate) {
        this.idBike = idBike;
        this.idPerson = idPerson;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    //getters

    public long getId() { return id; }

    public long getIdBike() {
        return idBike;
    }

    public long getIdPerson() {
        return idPerson;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    //setters

    public void setId(long id) { this.id = id; }

    public void setIdBike(long idBike) {
        this.idBike = idBike;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
