package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.ObjectDB.TownAsyncTask;
import com.example.daniel.bikesharing.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.settingsActivity;
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
        sqlToCloudTown(null);
    }

    public boolean isExistingTown(int id, String name, int npa) {
        boolean existing = false;
        String selectQuery = "SELECT " + db.getKEY_TOWN_NAME() + ", " + db.getKEY_NPA()
                + " FROM " + db.getTABLE_TOWN() + " WHERE id != " + id;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME())).equals(name)
                        && c.getInt(c.getColumnIndex(db.getKEY_NPA())) == npa)
                    existing = true;
            } while (c.moveToNext());
        }

        c.close();

        return existing;
    }

    /**
     * Gets the towns
     * @return List<Town> : The list of towns
     */
    public List<Town> getTowns() {
        List<Town> towns = new ArrayList<>();

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

        c.close();

        return towns;
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
            c.moveToFirst();

            town = new Town();
            town.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
            town.setName(c.getString(c.getColumnIndex(db.getKEY_TOWN_NAME())));
            town.setNpa(c.getInt(c.getColumnIndex(db.getKEY_NPA())));
            town.setIdCanton(c.getInt(c.getColumnIndex(db.getKEY_CANTONID())));

            c.close();
        }

        return town;
    }

    public boolean isExistingPlace(int id, String name, String address) {
        boolean existing = false;
        String selectQuery = "SELECT " + db.getKEY_PLACE_NAME() + ", " + db.getKEY_PLACE_ADDRESS()
                + " FROM " + db.getTABLE_PLACE() + " WHERE id != " + id;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getString(c.getColumnIndex(db.getKEY_PLACE_NAME())).equals(name)
                        || c.getString(c.getColumnIndex(db.getKEY_PLACE_ADDRESS())).equals(address))
                    existing = true;
            } while (c.moveToNext());
        }

        c.close();

        return existing;
    }

    public void sqlToCloudTown(SettingsActivity settingsActivity){
        List<Town> towns = getTowns();
        for (Town t : towns) {
            com.example.pedro.myapplication.backend.townApi.model.Town town = new com.example.pedro.myapplication.backend.townApi.model.Town();
            town.setId((long) t.getId());
            town.setName(t.getName());
            town.setNpa(t.getNpa());
            town.setIdCanton((long) t.getIdCanton());
            new TownAsyncTask(town, db, settingsActivity).execute();
        }
        Log.e("debugCloud","all town data saved");
    }

    public void cloudToSqlTown(List<com.example.pedro.myapplication.backend.townApi.model.Town> items){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(db.getTABLE_TOWN(), null, null);

        for (com.example.pedro.myapplication.backend.townApi.model.Town t : items) {
            ContentValues values = new ContentValues();
            values.put(db.getKEY_ID(), t.getId());
            values.put(db.getKEY_CANTONID(), t.getIdCanton());
            values.put(db.getKEY_TOWN_NAME(), t.getName());
            values.put(db.getKEY_NPA(), t.getNpa());

            sqlDB.insert(db.getTABLE_TOWN(), null, values);
        }
        sqlDB.close();
        Log.e("debugCloud","all town data got");
    }
}
