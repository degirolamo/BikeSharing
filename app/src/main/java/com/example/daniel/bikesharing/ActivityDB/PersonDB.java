package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.CantonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.PersonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.daniel.bikesharing.R.string.cantons;

/**
 * Project BikeSharing
 * Package ActivityDB
 * Class PersonDB.java
 * Date 22.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Person object
 */

public class PersonDB {
    private DatabaseHelper db;

    public PersonDB(DatabaseHelper db) {
        this.db = db;
    }

    /**
     * Gets the list of the people
     * @return List<Person> : The list of the people
     */
    public List<Person> getPeople() {
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

    /**
     * Inserts a new person in the database
     * @param idCanton : The canton id
     * @param email : The email address
     * @param password : The password of the person
     * @param firstname : The firstname of the person
     * @param lastname : The lastname of the person
     * @param isAdmin : If the person is admin or not
     */
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

        sqlToCloudPerson(null);
    }

    /**
     * Updates a Person
     * @param id : id of the person to update
     * @param idCanton : id of the canton
     * @param email : new email address
     * @param firstname : new firstname
     * @param lastname : new lastname
     */
    public void updatePerson(int id, int idCanton, String email, String firstname, String lastname) {

        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTONID(), idCanton);
        values.put(db.getKEY_EMAIL(), email);
        values.put(db.getKEY_FIRSTNAME(), firstname);
        values.put(db.getKEY_LASTNAME(), lastname);


        sqlDB.update(db.getTABLE_PERSON(), values, db.getKEY_ID() + " = " + id, null);

        sqlToCloudPerson(null);
    }

    /**
     * Gets a person by its id
     * @param idPerson : The id of the person
     * @return Person : The person found
     */
    public Person getPerson(int idPerson) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON()
                + " WHERE " + db.getKEY_ID() + " = " + idPerson;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        Person person = null;

        if(c != null) {
            c.moveToFirst();

            person = new Person();
            person.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
            person.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));
            person.setEmail(c.getString(c.getColumnIndex(db.getKEY_EMAIL())));
            person.setPassword(c.getString(c.getColumnIndex(db.getKEY_PASSWORD())));
            person.setFirstname(c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME())));
            person.setLastname(c.getString(c.getColumnIndex(db.getKEY_LASTNAME())));
            person.setAdmin(c.getInt(c.getColumnIndex(db.getKEY_ISADMIN())));

            c.close();
        }

        return person;
    }

    /**
     * Gets a person by its email address
     * @param email : the email address of the user
     * @return The person found
     */
    public Person getPerson(String email) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON()
                + " WHERE " + db.getKEY_EMAIL() + " = '" + email + "'";

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        Person person = null;

        if (c != null) {
            c.moveToFirst();

            person = new Person();
            person.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
            person.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));
            person.setEmail(c.getString(c.getColumnIndex(db.getKEY_EMAIL())));
            person.setPassword(c.getString(c.getColumnIndex(db.getKEY_PASSWORD())));
            person.setFirstname(c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME())));
            person.setLastname(c.getString(c.getColumnIndex(db.getKEY_LASTNAME())));
            person.setAdmin(c.getInt(c.getColumnIndex(db.getKEY_ISADMIN())));

            c.close();
        }

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
                Log.e("LOGIN", c.getString(c.getColumnIndex(db.getKEY_EMAIL())));
                if(c.getString(c.getColumnIndex(db.getKEY_EMAIL())).equals(email))
                    existing = true;
            } while (c.moveToNext());
        }

        c.close();

        return existing;
    }

    public void setAdminRights(int idPerson, int isAdmin) {
        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_ISADMIN(), isAdmin);


        sqlDB.update(db.getTABLE_PERSON(), values, db.getKEY_ID() + " = " + idPerson, null);
        sqlToCloudPerson(null);
    }

    public void deletePerson(int idPerson) {
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        sqlDB.delete(db.getTABLE_PERSON(), db.getKEY_ID() + " = " + idPerson, null);
        sqlToCloudPerson(null);
    }

    public void sqlToCloudPerson(SettingsActivity settingsActivity){
        List<Person> people = getPeople();
        for (Person p : people) {
            com.example.pedro.myapplication.backend.personApi.model.Person person = new com.example.pedro.myapplication.backend.personApi.model.Person();
            person.setId((long) p.getId());
            person.setIdCanton((long) p.getIdCanton());
            person.setEmail(p.getEmail());
            person.setPassword(p.getPassword());
            person.setFirstname(p.getFirstname());
            person.setLastname(p.getLastname());
            person.setAdmin(p.getAdmin());
            new PersonAsyncTask(person, db, settingsActivity).execute();
        }
        Log.e("debugCloud","all person data saved");
    }

    public void cloudToSqlPerson(List<com.example.pedro.myapplication.backend.personApi.model.Person> items){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(db.getTABLE_PERSON(), null, null);

        for (com.example.pedro.myapplication.backend.personApi.model.Person p : items) {
            ContentValues values = new ContentValues();
            values.put(db.getKEY_ID(), p.getId());
            values.put(db.getKEY_CANTONID(), p.getIdCanton());
            values.put(db.getKEY_EMAIL(), p.getEmail());
            values.put(db.getKEY_PASSWORD(), p.getPassword());
            values.put(db.getKEY_FIRSTNAME(), p.getFirstname());
            values.put(db.getKEY_LASTNAME(), p.getLastname());
            values.put(db.getKEY_ISADMIN(), p.getAdmin());

            sqlDB.insert(db.getTABLE_PERSON(), null, values);
        }
        sqlDB.close();
        Log.e("debugCloud","all person data got");
    }
}