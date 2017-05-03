package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static com.example.daniel.bikesharing.R.string.cantons;

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
    }

    /**
     * Gets the number of bikes per place
     * @param idPlace : Id of the place
     * @return int : The number of bikes
     */
    public int getNbBikes(int idPlace) {
        int nbBikes = 0;

        String selectQuery = "SELECT COUNT(*) nb FROM " + db.getTABLE_BIKE()
                + " WHERE " + db.getKEY_PLACEID() + " = " + idPlace;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null) {
            nbBikes = c.getInt(c.getColumnIndex("nb"));

            c.close();
        }

        return nbBikes;
    }
}
