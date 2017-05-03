package com.example.daniel.bikesharing.ObjectDB;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Canton.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Canton object
 */

public class Canton {
    private int id;
    private String name;
    private String picture;

    // contructors

    public Canton() {

    }

    public  Canton(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public  Canton(int id, String name, String picture){
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return getName();
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

    // setters

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
