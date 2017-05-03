package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * @project BikeSharing
 * @package ActivityDB
 * @class PersonDB.java
 * @date 23.04.2017
 * @authors Daniel De Girolamo & Pedro Gil Ferreira
 * @description Class containing the methods related with the Person object
 */

public class PersonDB {
    private DatabaseHelper db;

    public PersonDB(DatabaseHelper db) {
        this.db = db;
    }

    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                int idCanton = c.getInt(c.getColumnIndex(db.getKEY_PERSON_CANTONID()));
                String email = c.getString(c.getColumnIndex(db.getKEY_EMAIL()));
                String password =  c.getString(c.getColumnIndex(db.getKEY_PASSWORD()));
                String firstname =  c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME()));
                String lastname =  c.getString(c.getColumnIndex(db.getKEY_LASTNAME()));
                int isAdmin = c.getInt(c.getColumnIndex(db.getKEY_ISADMIN()));
                Person p = new Person(id, idCanton, email, password, firstname, lastname, isAdmin);

                // adding to canton list
                persons.add(p);
            } while (c.moveToNext());
        }

        c.close();
        return persons;
    }

    public void insertPerson(int idCanton, String email, String password, String firstname, String lastname, int isAdmin) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PERSON_CANTONID(), idCanton);
        values.put(db.getKEY_EMAIL(), email);
        values.put(db.getKEY_PASSWORD(), password);
        values.put(db.getKEY_FIRSTNAME(), firstname);
        values.put(db.getKEY_LASTNAME(), lastname);
        values.put(db.getKEY_ISADMIN(), isAdmin);

        //insert row
        sqlDB.insert(db.getTABLE_PERSON(), null, values);
    }

    public void updatePerson(int id, int idCanton, String email, String firstname, String lastname) {

        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTONID(), idCanton);
        values.put(db.getKEY_EMAIL(), email);
        values.put(db.getKEY_FIRSTNAME(), firstname);
        values.put(db.getKEY_LASTNAME(), lastname);


        sqlDB.update(db.getTABLE_PERSON(), values, db.getKEY_ID() + " = " + id, null);
    }

    public Person getPerson(int idPerson) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON()
                + " WHERE " + db.getKEY_ID() + " = " + idPerson;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Person person = new Person();
        person.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        person.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));
        person.setEmail(c.getString(c.getColumnIndex(db.getKEY_EMAIL())));
        person.setPassword(c.getString(c.getColumnIndex(db.getKEY_PASSWORD())));
        person.setFirstname(c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME())));
        person.setLastname(c.getString(c.getColumnIndex(db.getKEY_LASTNAME())));
        person.setAdmin(c.getInt(c.getColumnIndex(db.getKEY_ISADMIN())));

        return person;
    }

    public Person getPerson(String email) {
        String password = "";

        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON()
                + " WHERE " + db.getKEY_EMAIL() + " = '" + email + "'";

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null && c.getCount() >= 1)
            c.moveToFirst();

        Person person = new Person();
        person.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        person.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));
        person.setEmail(c.getString(c.getColumnIndex(db.getKEY_EMAIL())));
        person.setPassword(c.getString(c.getColumnIndex(db.getKEY_PASSWORD())));
        person.setFirstname(c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME())));
        person.setLastname(c.getString(c.getColumnIndex(db.getKEY_LASTNAME())));
        person.setAdmin(c.getInt(c.getColumnIndex(db.getKEY_ISADMIN())));

        return person;
    }

    public boolean isEmailExisting(int id, String email) {
        boolean existing = false;
        String selectQuery = "SELECT " + db.getKEY_EMAIL() + " FROM " + db.getTABLE_PERSON()
                + " WHERE " + db.getKEY_ID() + " != " + id;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getString(c.getColumnIndex(db.getKEY_EMAIL())).equals(email))
                    existing = true;
            } while (c.moveToNext());
        }

        return existing;
    }

    public void setAdminRights(int idPerson, int isAdmin) {
        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_ISADMIN(), isAdmin);


        sqlDB.update(db.getTABLE_PERSON(), values, db.getKEY_ID() + " = " + idPerson, null);
    }

    public void deletePerson(int idPerson) {
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        sqlDB.delete(db.getTABLE_PERSON(), db.getKEY_ID() + " = " + idPerson, null);
    }
}