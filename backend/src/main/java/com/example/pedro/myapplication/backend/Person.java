package com.example.pedro.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Project BikeSharing
 * Package ObjectDB
 * Class Person.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the Person object
 */

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long idCanton;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int isAdmin;

    // contructors

    public Person() {

    }

    public Person(long id, long idCanton, String email, String password, String firstname, String lastname, int isAdmin) {
        this.id = id;
        this.idCanton = idCanton;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isAdmin = isAdmin;
    }

    public Person(long idCanton, String email, String password, String firstname, String lastname, int isAdmin) {
        this.idCanton = idCanton;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isAdmin = isAdmin;
    }

    // getters

    public long getId() {
        return id;
    }

    public long getIdCanton() {
        return idCanton;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAdmin() {
        return isAdmin;
    }


    // setters

    public void setId(long id) { this.id = id; }

    public void setIdCanton(long idCanton) {
        this.idCanton = idCanton;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
