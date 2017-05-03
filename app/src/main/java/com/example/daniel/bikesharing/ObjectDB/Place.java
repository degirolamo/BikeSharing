package com.example.daniel.bikesharing.ObjectDB;

/**
 * @project BikeSharing
 * @package ObjectDB
 * @class Place.java
 * @date 11.04.2017
 * @authors Daniel De Girolamo & Pedro Gil Ferreira
 * @description Class containing the Place object
 */

public class Place {
    private int id;
    private String name;
    private String picture;
    private int nbPlaces;
    private String address;
    private int idTown;

    // contructors

    public Place() {

    }

    public  Place(String name, String picture, int nbPlaces, String address, int idTown) {
        this.name = name;
        this.picture = picture;
        this.nbPlaces = nbPlaces;
        this.address = address;
        this.idTown = idTown;
    }

    public  Place(int id, String name, String picture, int nbPlaces, String address, int idTown){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.nbPlaces = nbPlaces;
        this.idTown = idTown;
        this.address = address;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public int getNbPlaces() { return nbPlaces; }

    public String getAddress() { return address; }

    public int getIdTown() {
        return idTown;
    }

    // setters

    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setAddress(String address) { this.address = address; }

    public void setIdTown(int idTown) {
        this.idTown = idTown;
    }
}
