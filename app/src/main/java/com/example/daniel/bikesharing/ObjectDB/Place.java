package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by Daniel on 11.04.2017.
 */

public class Place {
    private int id;
    private String name;
    private String picture;
    private int idTown;

    // contructors

    public Place() {

    }

    public  Place(String name, String picture, int idTown) {
        this.name = name;
        this.picture = picture;
        this.idTown = idTown;
    }

    public  Place(int id, String name, String picture, int idTown){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.idTown = idTown;
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

    public void setIdTown(int idTown) {
        this.idTown = idTown;
    }
}
