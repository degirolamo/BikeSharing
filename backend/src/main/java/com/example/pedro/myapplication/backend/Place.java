package com.example.pedro.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Place.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Place object
 */

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private String picture;
    private int nbPlaces;
    private String address;
    private long idTown;

    // contructors

    public Place() {

    }

    public  Place(String name, String picture, int nbPlaces, String address, long idTown) {
        this.name = name;
        this.picture = picture;
        this.nbPlaces = nbPlaces;
        this.address = address;
        this.idTown = idTown;
    }

    public  Place(long id, String name, String picture, int nbPlaces, String address, long idTown){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.nbPlaces = nbPlaces;
        this.idTown = idTown;
        this.address = address;
    }

    // getters

    public long getId() {
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

    public long getIdTown() {
        return idTown;
    }

    // setters

    public void setId(long id) { this.id = id; }

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

    public void setIdTown(long idTown) {
        this.idTown = idTown;
    }
}
