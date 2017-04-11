package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by Daniel on 11.04.2017.
 */

public class Person {
    private int id;
    private int idTown;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String adress;
    private boolean isAdmin;

    // contructors

    public Person(int id, int idTown, String email, String password, String firstname, String lastname, String adress, boolean isAdmin) {
        this.id = id;
        this.idTown = idTown;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adress = adress;
        this.isAdmin = isAdmin;
    }

    public Person(int idTown, String email, String password, String firstname, String lastname, String adress, boolean isAdmin) {
        this.idTown = idTown;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adress = adress;
        this.isAdmin = isAdmin;
    }

    // getters

    public int getId() {
        return id;
    }

    public int getIdTown() {
        return idTown;
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

    public String getAdress() {
        return adress;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    // setters


    public void setIdTown(int idTown) {
        this.idTown = idTown;
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

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
