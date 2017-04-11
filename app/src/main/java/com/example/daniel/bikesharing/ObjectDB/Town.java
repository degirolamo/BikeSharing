package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by Daniel on 11.04.2017.
 */

public class Town {
    private int id;
    private String name;
    private int npa;
    private int idCanton;

    // contructors

    public  Town(String name, int npa, int idCanton) {
        this.name = name;
        this.npa = npa;
        this.idCanton = idCanton;
    }

    public  Town(int id, String name, int npa, int idCanton){
        this.id = id;
        this.name = name;
        this.npa = npa;
        this.idCanton = idCanton;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNpa() {
        return npa;
    }

    public int getIdCanton() {
        return idCanton;
    }
// setters

    public void setName(String name) {
        this.name = name;
    }

    public void setNpa(int npa) {
        this.npa = npa;
    }

    public void setIdCanton(int idCanton) {
        this.idCanton = idCanton;
    }
}

