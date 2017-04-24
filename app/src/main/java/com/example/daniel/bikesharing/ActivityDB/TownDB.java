package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.daniel.bikesharing.R.string.cantons;

/**
 * Created by pedro on 19.04.2017.
 */

public class TownDB {

    private DatabaseHelper db ;

    public TownDB(DatabaseHelper db){
        this.db = db;
    }

    public List<Town> getTowns (){
        List<Town> towns = new ArrayList<Town>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_TOWN();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                String name = c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME()));
                int npa =  c.getInt(c.getColumnIndex(db.getKEY_NPA()));
                int idCanton = c.getInt(c.getColumnIndex(db.getKEY_CANTONID()));
                Town t = new Town(id, name, npa, idCanton);

                // adding to canton list
                towns.add(t);
            } while (c.moveToNext());
        }

        return towns;

    }

    public void insertTown(int idCanton, String name, int npa) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTONID(), idCanton);
        values.put(db.getKEY_TOWN_NAME(), name);
        values.put(db.getKEY_NPA(), npa);

        //insert row
        sqlDB.insert(db.getTABLE_TOWN(), null, values);
    }

    public List<Town> getTownsByCanton(int idCanton) {
        List<Town> towns = new ArrayList<Town>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_TOWN()
                + " WHERE " + db.getKEY_CANTONID() + " = " + idCanton;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                String name = c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME()));
                int npa =  c.getInt(c.getColumnIndex(db.getKEY_NPA()));
                Town t = new Town(id, name, npa, idCanton);

                // adding to canton list
                towns.add(t);
            } while (c.moveToNext());
        }

        return towns;
    }

    public Town getTown(int idTown) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_TOWN()
                + " WHERE " + db.getKEY_ID() + " = " + idTown;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Town town = new Town();
        town.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        town.setName(c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME())));
        town.setNpa(c.getInt(c.getColumnIndex(db.getKEY_NPA())));
        town.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));

        return town;
    }
}
