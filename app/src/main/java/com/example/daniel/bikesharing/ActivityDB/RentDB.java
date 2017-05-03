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
 * Project BikeSharing
 * Package ActivityDB
 * Class RentDB.java
 * Date 23.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Rent object
 */

public class RentDB {
    private DatabaseHelper db;

    public RentDB(DatabaseHelper db) {
        this.db = db;
    }

    /**
     * Inserts a rent
     * @param idBike : The id of the rent
     * @param idPerson : The id of the person
     * @param beginDate : The date of beginning of the rent
     * @param endDate : The date of ending of the rent
     */
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


}
