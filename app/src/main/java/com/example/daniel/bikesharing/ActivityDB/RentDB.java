package com.example.daniel.bikesharing.ActivityDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.PersonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Rent;
import com.example.daniel.bikesharing.ObjectDB.RentAsyncTask;
import com.example.daniel.bikesharing.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.settingsActivity;

/**
 * Project BikeSharing
 * Package ActivityDB
 * Class RentDB.java
 * Date 23.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Class containing the methods related with the Rent object
 */

public class RentDB {
    private DatabaseHelper db;

    public RentDB(DatabaseHelper db) {
        this.db = db;
    }

    /**
     * Inserts a rent
     * @param idBike : The id of the rent
     * @param idPerson : The id of the person
     * @param beginDate : The date of beginning of the rent
     * @param endDate : The date of ending of the rent
     */
    public void insertRent(int idBike, int idPerson, String beginDate, String endDate) {
        SQLiteDatabase sqlDB = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.getKEY_BIKEID(), idBike);
        values.put(db.getKEY_PERSONID(), idPerson);
        values.put(db.getKEY_BEGINDATE(), beginDate);
        values.put(db.getKEY_ENDDATE(), endDate);

        //insert row
        sqlDB.insert(db.getTABLE_RENT(), null, values);
    }

    public List<Rent> getRents() {
        List<Rent> rents = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + db.getTABLE_RENT();

        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor c = sqlDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(db.getKEY_ID()));
                int idBike = c.getInt(c.getColumnIndex(db.getKEY_BIKEID()));
                int idPerson = c.getInt(c.getColumnIndex(db.getKEY_PERSONID()));
                String beginDate = c.getString(c.getColumnIndex(db.getKEY_BEGINDATE()));
                String endDate = c.getString(c.getColumnIndex(db.getKEY_ENDDATE()));
                Rent r = new Rent(id, idBike, idPerson, beginDate, endDate);

                // adding to canton list
                rents.add(r);
            } while (c.moveToNext());
        }

        c.close();
        return rents;
    }

    public void sqlToCloudRent(SettingsActivity settingsActivity){
        List<Rent> rents = getRents();
        for (Rent r : rents) {
            com.example.pedro.myapplication.backend.rentApi.model.Rent rent = new com.example.pedro.myapplication.backend.rentApi.model.Rent();
            rent.setId((long) r.getId());
            rent.setIdBike((long) r.getIdBike());
            rent.setIdPerson((long) r.getIdPerson());
            rent.setBeginDate(r.getBeginDate());
            rent.setEndDate(r.getEndDate());
            new RentAsyncTask(rent, db, settingsActivity).execute();
        }
        Log.e("debugCloud","all rent data saved");
    }

    public void cloudToSqlRent(List<com.example.pedro.myapplication.backend.rentApi.model.Rent> items){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(db.getTABLE_RENT(), null, null);

        for (com.example.pedro.myapplication.backend.rentApi.model.Rent r : items) {
            ContentValues values = new ContentValues();
            values.put(db.getKEY_ID(), r.getId());
            values.put(db.getKEY_BIKEID(), r.getIdBike());
            values.put(db.getKEY_PERSONID(), r.getIdPerson());
            values.put(db.getKEY_BEGINDATE(), r.getBeginDate());
            values.put(db.getKEY_ENDDATE(), r.getEndDate());

            sqlDB.insert(db.getTABLE_RENT(), null, values);
        }
        sqlDB.close();
        Log.e("debugCloud","all rent data got");
    }
}
