package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by Daniel on 11.04.2017.
 */

public class Bike {
    private int id;
    private int idPlace;


    //Contructors

    public Bike(int id, int idPlace) {
        this.id = id;
        this.idPlace = idPlace;
    }

    public Bike(int idPlace) {
        this.idPlace = idPlace;
    }

    //getters

    public int getId() {
        return id;
    }

    public int getIdPlace() {
        return idPlace;
    }

    //setters

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }
}
