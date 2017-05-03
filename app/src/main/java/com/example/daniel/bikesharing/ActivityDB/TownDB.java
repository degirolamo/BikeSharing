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
 * Project BikeSharing
 * Package ActivityDB
 * Class TownDB.java
 * Date 19.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Town object
 */

public class TownDB {

    private DatabaseHelper db ;

    public TownDB(DatabaseHelper db){
        this.db = db;
    }

    /**
     * Inserts a new town
     * @param idCanton : the id of the canton
     * @param name : the name of the town
     * @param npa : the npa of the town
     */
    public void insertTown(int idCanton, String name, int npa) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTONID(), idCanton);
        values.put(db.getKEY_TOWN_NAME(), name);
        values.put(db.getKEY_NPA(), npa);

        //insert row
        sqlDB.insert(db.getTABLE_TOWN(), null, values);
    }

    /**
     * Gets the towns by canton id
     * @param idCanton : The id of the canton
     * @return List<Town> : The list of towns
     */
    public List<Town> getTownsByCanton(int idCanton) {
        List<Town> towns = new ArrayList<>();

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

        c.close();

        return towns;
    }

    /**
     * Gets a town by its id
     * @param idTown : The id of the town
     * @return Town : The town found
     */
    public Town getTown(int idTown) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_TOWN()
                + " WHERE " + db.getKEY_ID() + " = " + idTown;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        Town town = null;
        if(c != null) {
            town = new Town();
            town.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
            town.setName(c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME())));
            town.setNpa(c.getInt(c.getColumnIndex(db.getKEY_NPA())));
            town.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));

            c.close();
        }

        return town;
    }
}
