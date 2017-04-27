package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.example.daniel.bikesharing.R.string.places;

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
        place.setNbPlaces(c.getInt(c.getColumnIndex(db.getKEY_PLACE_NBPLACES())));
        place.setAddress(c.getString(c.getColumnIndex(db.getKEY_PLACE_ADDRESS())));
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
                int nbPlaces = c.getInt(c.getColumnIndex(db.getKEY_PLACE_NBPLACES()));
                String address =  c.getString(c.getColumnIndex(db.getKEY_PLACE_ADDRESS()));
                Place place = new Place(id, name, picture, nbPlaces, address, idTown);

                // adding to place list
                places.add(place);
            } while (c.moveToNext());
        }

        return places;
    }

    public void insertPlace(String name, String picture, int nbPlaces, String address, int idTown) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_PLACE_NAME(), name);
        values.put(db.getKEY_PLACE_PICTURE(), picture);
        values.put(db.getKEY_PLACE_NBPLACES(), nbPlaces);
        values.put(db.getKEY_PLACE_ADDRESS(), address);
        values.put(db.getKEY_PLACE_TOWNID(), idTown);

        //insert row
        sqlDB.insert(db.getTABLE_PLACE(), null, values);
    }

    public Place getPlaceByBike(int idBike) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_PLACE()
                + " AS p INNER JOIN " + db.getTABLE_BIKE() + " AS b ON p." + db.getKEY_ID() + " = " + db.getKEY_PLACEID()
                + " WHERE b." + db.getKEY_ID() + " = " + idBike;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Place place = new Place();
        place.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        place.setName(c.getString(c.getColumnIndex(db.getKEY_PLACE_NAME())));
        place.setPicture(c.getString(c.getColumnIndex(db.getKEY_PLACE_PICTURE())));
        place.setNbPlaces(c.getInt(c.getColumnIndex(db.getKEY_PLACE_NBPLACES())));
        place.setAddress(c.getString(c.getColumnIndex(db.getKEY_PLACE_ADDRESS())));
        place.setIdTown(c.getInt(c.getColumnIndex(db.getKEY_PLACE_TOWNID())));

        return place;
    }

    public List<Integer> getNbRentsByPerson(int idPerson) {
        //Returns for each places, the number of times they appears in bikeDB
        List<Integer> listNbRents = new ArrayList<>();

        String selectQuery = "SELECT COUNT(*) nb, idPlace FROM " + db.getTABLE_BIKE()
                + " b INNER JOIN " + db.getTABLE_RENT() + " r ON b.id = " + db.getKEY_BIKEID()
                + " WHERE " + db.getKEY_PERSONID() + " = " + idPerson
                + " GROUP BY " + db.getKEY_PLACEID()
                + " ORDER BY COUNT(*) DESC"
                + " LIMIT 3";

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int idPlace = c.getInt(c.getColumnIndex(db.getKEY_PLACEID()));

                // adding to place list
                listNbRents.add(idPlace);
            } while (c.moveToNext());
        }

        return listNbRents;
    }
}
