package com.example.daniel.bikesharing.ActivityDB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11.04.2017.
 */

public class CantonDB  {

   private DatabaseHelper db ;

    public CantonDB(DatabaseHelper db){
        this.db = db;
    }

    public List<Canton> selectCanton (){
        List<Canton> cantons = new ArrayList<Canton>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_CANTON();

        SQLiteDatabase SQLdb = db.getReadableDatabase();
        Cursor c = SQLdb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String name = (c.getString(c.getColumnIndex(db.getKEY_CANTON_NAME())));
                String picture =  (c.getString(c.getColumnIndex(db.getKEY_CANTON_PICTURE())));
                Canton ct = new Canton(name, picture);

                // adding to canton list
                cantons.add(ct);
            } while (c.moveToNext());
        }

        return cantons;

    }
}
