package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by pedro on 19.04.2017.
 */

public class PlaceDB {
    private DatabaseHelper db ;

    public PlaceDB(DatabaseHelper db){
        this.db = db;
    }

    public Place getPlace(int idPlace){
        String selectQuery = "SELECT * FROM " + db.getTABLE_PLACE()
                + " WHERE " + db.getKEY_ID() + " = " + idPlace;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Place place = new Place();
        place.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        place.setName(c.getString(c.getColumnIndex(db.getKEY_PLACE_NAME())));
        place.setPicture(c.getString(c.getColumnIndex(db.getKEY_PLACE_PICTURE())));
        place.setIdTown(c.getInt(c.getColumnIndex(db.getKEY_PLACE_TOWNID())));

        return place;
    }

    public List<Place> getPlacesByTown (int idTown){
        List<Place> places = new ArrayList<Place>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_PLACE()
                + " WHERE " + db.getKEY_PLACE_TOWNID() + " = " + idTown;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                String name = c.getString(c.getColumnIndex(db.getKEY_PLACE_NAME()));
                String picture =  c.getString(c.getColumnIndex(db.getKEY_PLACE_PICTURE()));
                Place place = new Place(id, name, picture, idTown);

                // adding to canton list
                places.add(place);
            } while (c.moveToNext());
        }

        return places;
    }

    public void insertPlace(String name, String picture, int idTown) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PLACE_NAME(), name);
        values.put(db.getKEY_PLACE_PICTURE(), picture);
        values.put(db.getKEY_PLACE_TOWNID(), idTown);

        //insert row
        sqlDB.insert(db.getTABLE_PLACE(), null, values);
    }
}