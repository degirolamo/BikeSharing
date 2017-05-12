package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.CantonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.ObjectDB.TownAsyncTask;
import com.example.daniel.bikesharing.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Project BikeSharing
 * Package ActivityDB
 * Class CantonDB.java
 * Date 11.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Canton object
 */

public class CantonDB  {

    private DatabaseHelper db ;

    public CantonDB(DatabaseHelper db){
        this.db = db;
    }

    /**
     * Gets the list of the cantons
     * @return List<Canton> : The list of cantons
     */
    public List<Canton> getCantons (){
        List<Canton> cantons = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_CANTON();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                String name = c.getString(c.getColumnIndex(db.getKEY_CANTON_NAME()));
                String picture =  c.getString(c.getColumnIndex(db.getKEY_CANTON_PICTURE()));
                Canton ct = new Canton(id, name, picture);

                // adding to canton list
                cantons.add(ct);
            } while (c.moveToNext());
        }

        c.close();

        return cantons;

    }

    /**
     * Inserts a new canton in the database
     * @param name : The name of the canton
     * @param picture : The picture of the canton
     */
    public void insertCanton(String name, String picture) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTON_NAME(), name);
        values.put(db.getKEY_CANTON_PICTURE(), picture);

        //insert row
        sqlDB.insert(db.getTABLE_CANTON(), null, values);
    }

    /**
     * Gets a canton by its id
     * @param idCanton : The id of the canton
     * @return Canton : The canton found
     */
    public Canton getCanton(int idCanton) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_CANTON()
                + " WHERE " + db.getKEY_ID()  + " = " + idCanton;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        Canton canton = null;
        if(c != null) {
            c.moveToFirst();

            canton = new Canton();
            canton.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
            canton.setName(c.getString(c.getColumnIndex(db.getKEY_CANTON_NAME())));
            canton.setPicture(c.getString(c.getColumnIndex(db.getKEY_CANTON_PICTURE())));

            c.close();
        }

        return canton;
    }

    public void sqlToCloudCanton(SettingsActivity settingsActivity){
        List<Canton> cantons = getCantons();
        for (Canton c : cantons) {
            com.example.pedro.myapplication.backend.cantonApi.model.Canton canton = new com.example.pedro.myapplication.backend.cantonApi.model.Canton();
            canton.setId((long) c.getId());
            canton.setName(c.getName());
            canton.setPicture(c.getPicture());
            new CantonAsyncTask(canton, db, settingsActivity).execute();
        }
        Log.e("debugCloud","all canton data saved");
    }

    public void cloudToSqlCanton(List<com.example.pedro.myapplication.backend.cantonApi.model.Canton> items){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(db.getTABLE_CANTON(), null, null);

        for (com.example.pedro.myapplication.backend.cantonApi.model.Canton c : items) {
            ContentValues values = new ContentValues();
            values.put(db.getKEY_ID(), c.getId());
            values.put(db.getKEY_CANTON_NAME(), c.getName());
            values.put(db.getKEY_CANTON_PICTURE(), c.getPicture());

            sqlDB.insert(db.getTABLE_CANTON(), null, values);
        }
        sqlDB.close();
        Log.e("debugCloud","all canton data got");
    }
}
