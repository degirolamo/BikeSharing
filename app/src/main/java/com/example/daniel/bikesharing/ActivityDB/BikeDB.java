package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.BikeAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.BikeDeleteAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.PlaceAsyncTask;
import com.example.daniel.bikesharing.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.settingsActivity;
import static com.example.daniel.bikesharing.R.menu.settings;
import static com.example.daniel.bikesharing.R.string.cantons;
import static com.example.daniel.bikesharing.R.string.places;

/**
 * Project BikeSharing
 * Package ActivityDB
 * Class BikeDB.java
 * Date 22.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Bike object
 */

public class BikeDB {
    private DatabaseHelper db;

    public BikeDB(DatabaseHelper db){
        this.db = db;
    }

    public List<Bike> getBikes() {
        List<Bike> bikes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_BIKE();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                int idPlace = c.getInt(c.getColumnIndex(db.getKEY_PLACEID()));
                Bike bike = new Bike(id, idPlace);

                // adding to canton list
                bikes.add(bike);
            } while (c.moveToNext());
        }

        c.close();

        return bikes;
    }

    /**
     * Inserts a bike into a place
     * @param idPlace Id of the place
     */
    public void insertBike(int idPlace) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PLACEID(), idPlace);

        //insert row
        sqlDB.insert(db.getTABLE_BIKE(), null, values);
        sqlToCloudBike(null);
    }

    /**
     * Gets the number of bikes per place
     * @param idPlace : Id of the place
     * @return int : The number of bikes
     */
    public List<Bike> getBikesByPlace(int idPlace) {
        List<Bike> bikes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_BIKE()
                + " WHERE " + db.getKEY_PLACEID() + " = " + idPlace;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                Bike bike = new Bike(id, idPlace);

                // adding to canton list
                bikes.add(bike);
            } while (c.moveToNext());
        }

        return bikes;
    }

    public void sqlToCloudBike(SettingsActivity settingsActivity){
        List<Bike> bikes = getBikes();
        for (Bike b : bikes) {
            com.example.pedro.myapplication.backend.bikeApi.model.Bike bike = new com.example.pedro.myapplication.backend.bikeApi.model.Bike();
            bike.setId((long) b.getId());
            bike.setIdPlace((long) b.getIdPlace());
            new BikeAsyncTask(bike, db, settingsActivity).execute();
        }
        Log.e("debugCloud","all bike data saved");
    }

    public void cloudToSqlBike(List<com.example.pedro.myapplication.backend.bikeApi.model.Bike> items){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(db.getTABLE_BIKE(), null, null);

        for (com.example.pedro.myapplication.backend.bikeApi.model.Bike b : items) {
            ContentValues values = new ContentValues();
            values.put(db.getKEY_ID(), b.getId());
            values.put(db.getKEY_PLACEID(), b.getIdPlace());
            sqlDB.insert(db.getTABLE_BIKE(), null, values);
        }
        sqlDB.close();
        Log.e("debugCloud","all bike data got");
    }

    public void deleteFromCloudBike(int id) {
        new BikeDeleteAsyncTask(id).execute();
    }
}
