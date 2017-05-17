package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.BikeAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.CantonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.PersonAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.PlaceAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Rent;
import com.example.daniel.bikesharing.ObjectDB.RentAsyncTask;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.ObjectDB.TownAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.daniel.bikesharing.R.string.places;
import static com.example.daniel.bikesharing.R.styleable.MenuItem;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class MainActivity.java
 * Date 29.03.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Main Activity of the application
 */

public class MainActivity extends AppCompatActivity {

    public static int IS_CONNECTED = 0;
    public static Person USER_CONNECTED;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db;
        db = new DatabaseHelper(getApplicationContext());
        db.reloadDatabase();

        CantonDB cantonDB = new CantonDB(db);
        cantonDB.insertCanton("Argovie", "ag");
        cantonDB.insertCanton("Appenzell Rhodes-Intérieures", "ai");
        cantonDB.insertCanton("Appenzell Rhodes-Extérieures", "ar");
        cantonDB.insertCanton("Berne", "be");
        cantonDB.insertCanton("Bâle-Campagne", "bl");
        cantonDB.insertCanton("Bâle-Ville", "bs");
        cantonDB.insertCanton("Fribourg", "fr");
        cantonDB.insertCanton("Genève", "ge");
        cantonDB.insertCanton("Glaris", "gl");
        cantonDB.insertCanton("Grisons", "gr");
        cantonDB.insertCanton("Jura", "ju");
        cantonDB.insertCanton("Lucerne", "lu");
        cantonDB.insertCanton("Neuchâtel", "ne");
        cantonDB.insertCanton("Nidwald", "nw");
        cantonDB.insertCanton("Obwald", "ow");
        cantonDB.insertCanton("Saint-Gall", "sg");
        cantonDB.insertCanton("Schaffhouse", "sh");
        cantonDB.insertCanton("Soleure", "so");
        cantonDB.insertCanton("Schwytz", "sz");
        cantonDB.insertCanton("Thurgovie", "tg");
        cantonDB.insertCanton("Tessin", "ti");
        cantonDB.insertCanton("Uri", "ur");
        cantonDB.insertCanton("Valais", "vs");
        cantonDB.insertCanton("Vaud", "vd");
        cantonDB.insertCanton("Zoug", "zg");
        cantonDB.insertCanton("Zurich", "zh");

//        TownDB townDB = new TownDB(db);
//        townDB.insertTown(23, "Monthey", 1870);
//        townDB.insertTown(23, "Martigny", 1920);
//        townDB.insertTown(23, "Sierre", 3960);
//        townDB.insertTown(23, "Sion", 1950);
//        townDB.insertTown(23, "St-Maurice", 1890);
//        townDB.insertTown(24, "Aigle", 1860);
//        townDB.insertTown(24, "Vevey", 1800);
//        townDB.insertTown(24, "Montreux", 1820);
//        townDB.insertTown(8, "Genève", 1200);

//        PlaceDB placeDB = new PlaceDB(db);
//        placeDB.insertPlace("Gare CFF", "monthey_gare_cff", 8, "Gare CFF", 1);
//        placeDB.insertPlace("Gare AOMC", "", 8, "Gare AOMC", 1);
//        placeDB.insertPlace("Vieux-Pont", "", 8, "Route de la Cretta 2", 1);
//        placeDB.insertPlace("Piscine", "", 8, "Avenue de l'Europe 115", 1);

//        BikeDB bikeDB = new BikeDB(db);
//        bikeDB.insertBike(1);
//        bikeDB.insertBike(2);
//        bikeDB.insertBike(3);
//        bikeDB.insertBike(4);
//        bikeDB.insertBike(1);
//        bikeDB.insertBike(2);
//        bikeDB.insertBike(3);
//        bikeDB.insertBike(4);

//        PersonDB personDB = new PersonDB(db);
//        personDB.insertPerson(23, "pedro@pedro.com", "pass", "Pedro", "Pedro", 1);
//        personDB.insertPerson(22, "dan@dan.com", "pass", "Daniel", "Daniel", 1);
//        personDB.insertPerson(1, "test@test.com", "pass", "Test", "Test", 0);

//        RentDB rentDB = new RentDB(db);
//        rentDB.insertRent(1, 1, "16-04-2017 02:01:00", "16-04-2017 14:12:24");
//        rentDB.insertRent(5, 1, "17-04-2017 03:01:00", "18-04-2017 13:12:24");
//        rentDB.insertRent(1, 1, "17-04-2017 04:01:00", "18-04-2017 12:12:24");
//        rentDB.insertRent(5, 1, "18-04-2017 05:01:00", "18-04-2017 11:12:24");
//        rentDB.insertRent(2, 1, "18-04-2017 06:01:00", "19-04-2017 10:12:24");
//        rentDB.insertRent(3, 1, "20-04-2017 07:01:00", "20-04-2017 15:12:24");
//        rentDB.insertRent(4, 3, "21-04-2017 08:01:00", "21-04-2017 16:12:24");
//        rentDB.insertRent(6, 1, "22-04-2017 09:01:00", "22-04-2017 17:12:24");
//        rentDB.insertRent(6, 1, "22-04-2017 09:01:00", "");
//        rentDB.insertRent(7, 3, "22-04-2017 09:01:00", "");

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        } else {
            progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Synchronisation avec le cloud...");
            progress.setCancelable(false);
            progress.show();

            new BikeAsyncTask(db, MainActivity.this).execute();
            new PersonAsyncTask(db, MainActivity.this).execute();
            new PlaceAsyncTask(db, MainActivity.this).execute();
            new RentAsyncTask(db, MainActivity.this).execute();
            new TownAsyncTask(db, MainActivity.this).execute();
        }

//        bikeDB.sqlToCloudBike();
//        cantonDB.sqlToCloudCanton();
//        personDB.sqlToCloudPerson();
//        placeDB.sqlToCloudPlace();
//        rentDB.sqlToCloudRent();
//        townDB.sqlToCloudTown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean bikeOK = false;
    public boolean cantonOK = false;
    public boolean personOK = false;
    public boolean placeOK = false;
    public boolean rentOK = false;
    public boolean townOK = false;

    public void check() {
        if (bikeOK && personOK &&
                placeOK && rentOK && townOK) {
            progress.dismiss();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}
