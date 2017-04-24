package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static com.example.daniel.bikesharing.R.string.cantons;

/**
 * Created by pedro on 23.04.2017.
 */

public class PersonDB {
    DatabaseHelper db;

    public PersonDB(DatabaseHelper db) {
        this.db = db;
    }

    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<Person>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_PERSON();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                int idTown = c.getInt(c.getColumnIndex(db.getKEY_PERSON_TOWNID()));
                String email = c.getString(c.getColumnIndex(db.getKEY_EMAIL()));
                String password =  c.getString(c.getColumnIndex(db.getKEY_PASSWORD()));
                String firstname =  c.getString(c.getColumnIndex(db.getKEY_FIRSTNAME()));
                String lastname =  c.getString(c.getColumnIndex(db.getKEY_LASTNAME()));
                String address =  c.getString(c.getColumnIndex(db.getKEY_ADDRESS()));
                int isAdmin = c.getInt(c.getColumnIndex(db.getKEY_ISADMIN()));
                Person p = new Person(id, idTown, email, password, firstname, lastname, address, isAdmin);

                // adding to canton list
                persons.add(p);
            } while (c.moveToNext());
        }

        return persons;
    }

    public void insertPerson(int idTown, String email, String password, String firstname, String lastname, String address, int isAdmin) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PERSON_TOWNID(), idTown);
        values.put(db.getKEY_EMAIL(), email);
        values.put(db.getKEY_PASSWORD(), password);
        values.put(db.getKEY_FIRSTNAME(), firstname);
        values.put(db.getKEY_LASTNAME(), lastname);
        values.put(db.getKEY_ADDRESS(), address);
        values.put(db.getKEY_ISADMIN(), isAdmin);

        //insert row
        sqlDB.insert(db.getTABLE_PERSON(), null, values);
    }
}
