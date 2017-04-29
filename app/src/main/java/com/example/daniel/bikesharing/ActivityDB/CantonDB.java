package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Daniel on 11.04.2017.
 */

public class CantonDB  {

    private DatabaseHelper db ;

    public CantonDB(DatabaseHelper db){
        this.db = db;
    }

    public List<Canton> getCantons (){
        List<Canton> cantons = new ArrayList<Canton>();

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

        return cantons;

    }

    public void insertCanton(String name, String picture) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_CANTON_NAME(), name);
        values.put(db.getKEY_CANTON_PICTURE(), picture);

        //insert row
        sqlDB.insert(db.getTABLE_CANTON(), null, values);
    }

    public Canton getCanton(int idCanton) {
        String selectQuery = "SELECT * FROM " + db.getTABLE_CANTON()
                + " WHERE " + db.getKEY_ID()  + " = " + idCanton;

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Canton canton = new Canton();
        canton.setId(c.getInt(c.getColumnIndex(db.getKEY_ID())));
        canton.setName(c.getString(c.getColumnIndex(db.getKEY_CANTON_NAME())));
        canton.setPicture(c.getString(c.getColumnIndex(db.getKEY_CANTON_PICTURE())));

        return canton;
    }
}
