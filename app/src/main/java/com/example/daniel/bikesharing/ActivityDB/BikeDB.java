package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.Canton;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static com.example.daniel.bikesharing.R.string.cantons;

/**
 * Created by pedro on 22.04.2017.
 */

public class BikeDB {
    private DatabaseHelper db;

    public BikeDB(DatabaseHelper db){
        this.db = db;
    }

    public List<Bike> getBikesByPlace (int idPlace){
        List<Bike> bikes = new ArrayList<Bike>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_BIKE()
                + " WHERE " + db.getKEY_PLACEID() + " = " + idPlace;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                int placeId = c.getInt(c.getColumnIndex(db.getKEY_PLACEID()));

                Bike b = new Bike(id, placeId);

                // adding to bike list
                bikes.add(b);
            } while (c.moveToNext());
        }

        return bikes;

    }

    public void insertBike(int idPlace) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PLACEID(), idPlace);

        //insert row
        sqlDB.insert(db.getTABLE_BIKE(), null, values);
    }

    public long getNbBikes() {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        return DatabaseUtils.queryNumEntries(sqlDB, "Bike");
    }
}
