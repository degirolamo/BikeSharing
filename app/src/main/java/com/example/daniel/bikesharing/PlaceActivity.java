package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.Objects.PlaceAdapter;
import com.example.daniel.bikesharing.Objects.TownAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class AdminUsersActivity.java
 * Date 22.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to display the list of the places
 */

public class PlaceActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private PlaceDB placeDB;
    private List<Place> places;
    private TownDB townDB;
    private Town town;
    private int idTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        db = new DatabaseHelper(getApplicationContext());
        idTown = getIntent().getIntExtra("idTown", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolPlaces);
        toolbar.setTitleTextColor(Color.WHITE);
        townDB = new TownDB(db);
        town = townDB.getTown(idTown);
        toolbar.setTitle(town.getName());
        setSupportActionBar(toolbar);
        ListView listViewPlaces;

        placeDB = new PlaceDB(db);
        places = placeDB.getPlacesByTown(idTown);
        listViewPlaces = (ListView) findViewById(R.id.listPlaces);

        PlaceAdapter adapter = new PlaceAdapter(this, places);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(adapter);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabPlaceAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPlaceActivity.class);
                i.putExtra("idTown", idTown);
                startActivity(i);
            }
        });

        if(USER_CONNECTED.getAdmin() == 0)
            fabAdd.hide();

        Button btnCanton = (Button) findViewById(R.id.btnPlaceCanton);
        btnCanton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CantonActivity.class));
                finish();
            }
        });

        Button btnTown = (Button) findViewById(R.id.btnPlaceTown);
        btnTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TownActivity.class);
                i.putExtra("idCanton", town.getIdCanton());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(USER_CONNECTED.getAdmin() == 1) {
            inflater.inflate(R.menu.edit, menu);
            inflater.inflate(R.menu.delete, menu);
        }
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEdit:
                Intent i = new Intent(getApplicationContext(), EditTownActivity.class);
                i.putExtra("idTown", idTown);
                startActivity(i);
                finish();
                return true;
            case R.id.btnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.warningDeleteTown + " " + town.getName() + " " + R.string.warningDeleteTown2);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlaceDB placeDB = new PlaceDB(db);
                        for (Place place : places) {
                            placeDB.deletePlace(place.getId());
                        }
                        townDB.deleteTown(idTown);
                        dialog.dismiss();
                        finishAffinity();
                        finish();
                        overridePendingTransition(0, 0);
                        TownActivity townActivity = new TownActivity();
                        Intent i = new Intent(getApplicationContext(), townActivity.getClass());
                        i.putExtra("idCanton", town.getIdCanton());
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
                return true;
            case R.id.action_settings:
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("idPerson", USER_CONNECTED.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_logout:
                builder = new AlertDialog.Builder(PlaceActivity.this);
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
                alert = builder.create();
                alert.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), TownActivity.class);
        TownDB townDB = new TownDB(db);
        i.putExtra("idCanton", townDB.getTown(idTown).getIdCanton());
        startActivity(i);
    }
}
