package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Rent;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * @project BikeSharing
 * @package ActivityDB
 * @class RentDB.java
 * @date 23.04.2017
 * @authors Daniel De Girolamo & Pedro Gil Ferreira
 * @description Class containing the methods related with the Rent object
 */

public class RentDB {
    DatabaseHelper db;

    public RentDB(DatabaseHelper db) {
        this.db = db;
    }

    public void insertRent(int idBike, int idPerson, String beginDate, String endDate) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_BIKEID(), idBike);
        values.put(db.getKEY_PERSONID(), idPerson);
        values.put(db.getKEY_BEGINDATE(), beginDate);
        values.put(db.getKEY_ENDDATE(), endDate);

        //insert row
        sqlDB.insert(db.getTABLE_RENT(), null, values);
    }

    public List<Rent> getRentsByPerson(int idPerson) {
        List<Rent> rents = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_RENT()
                + " WHERE " + db.getKEY_PERSONID() + " = " + idPerson
                + " ORDER BY " + db.getKEY_ENDDATE() + " DESC LIMIT 5";

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int idBike = c.getInt(c.getColumnIndex(db.getKEY_BIKEID()));
                String beginDate = c.getString(c.getColumnIndex(db.getKEY_BEGINDATE()));
                String endDate =  c.getString(c.getColumnIndex(db.getKEY_ENDDATE()));
                Rent r = new Rent(idBike, idPerson, beginDate, endDate);

                // adding to canton list
                rents.add(r);
            } while (c.moveToNext());
        }

        return rents;
    }


}
