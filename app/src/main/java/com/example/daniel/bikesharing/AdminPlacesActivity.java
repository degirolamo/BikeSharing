package com.example.daniel.bikesharing;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.Objects.PlaceAdapter;

import java.util.List;

/**
 * @project BikeSharing
 * @package ObjectDB
 * @class AddPlaceActivity.java
 * @date 29.04.2017
 * @authors Daniel De Girolamo & Pedro Gil Ferreira
 * @description Activity used to add places
 */

public class AdminPlacesActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_places);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAdminPlaces);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());
        PlaceDB placeDB = new PlaceDB(db);
        List<Place> places = placeDB.getPlaces();
        listViewPlaces = (ListView) findViewById(R.id.listAdminPlaces);

        PlaceAdapter adapter = new PlaceAdapter(this, places);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
}
