package com.example.daniel.bikesharing.ObjectDB;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Rent.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Rent object
 */

public class Rent {
    private int id;
    private int idBike;
    private int idPerson;
    private String beginDate;
    private String endDate;

    public Rent() {

    }

    public Rent(int id, int idBike, int idPerson, String beginDate, String endDate) {
        this.id = id;
        this.idBike = idBike;
        this.idPerson = idPerson;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    //getters

    public int getId() { return id; }

    public int getIdBike() {
        return idBike;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    //setters

    public void setId(int id) { this.id = id; }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
