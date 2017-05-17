package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class SettingsActivity.java
 * Date 29.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity to display the settings of the application
 */

public class SettingsActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolSettings);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);

        Button btnLanguage = (Button) findViewById(R.id.btnLanguage);
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLanguage(v);
            }
        });

        Button btnSynchronize = (Button) findViewById(R.id.btnSynchronize);
        btnSynchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(SettingsActivity.this);
                progress.setMessage("Synchronisation avec le cloud...");
                progress.setCancelable(false);
                progress.show();
                cloudToSql();
            }
        });

        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });
    }

    public void cloudToSql() {
        db = new DatabaseHelper(getApplicationContext());
        TownDB townDB = new TownDB(db);
        townDB.sqlToCloudTown(SettingsActivity.this);
        BikeDB bikeDB = new BikeDB(db);
        bikeDB.sqlToCloudBike(SettingsActivity.this);
        PersonDB personDB = new PersonDB(db);
        personDB.sqlToCloudPerson(SettingsActivity.this);
        PlaceDB placeDB = new PlaceDB(db);
        placeDB.sqlToCloudPlace(SettingsActivity.this);
        RentDB rentDB = new RentDB(db);
        rentDB.sqlToCloudRent(SettingsActivity.this);
    }

    public void displayLanguage(View v) {
        Intent i = new Intent(getApplicationContext(), LanguageActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        MenuItem itemSettings = menu.findItem(R.id.action_settings);
        itemSettings.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("idPerson", USER_CONNECTED.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.action_logout);
                builder.setMessage(R.string.warningLogout);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        USER_CONNECTED = null;
                        IS_CONNECTED = 0;
                        dialog.dismiss();
                        finishAffinity();
                        finish();
                        overridePendingTransition(0, 0);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i;
        if(USER_CONNECTED.getAdmin() == 1)
            i = new Intent(getApplicationContext(), AdminHomeActivity.class);
        else
            i = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(i);
        finish();
    }

    public boolean bikeOK = false;
    public boolean cantonOK = false;
    public boolean personOK = false;
    public boolean placeOK = false;
    public boolean rentOK = false;
    public boolean townOK = false;

    public void check() {
        if(bikeOK && personOK &&
                placeOK && rentOK && townOK) {
            progress.dismiss();
        }
    }
}
