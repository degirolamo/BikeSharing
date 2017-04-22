package com.example.daniel.bikesharing;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class PlaceActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        db = new DatabaseHelper(getApplicationContext());

        PlaceDB placeDB = new PlaceDB(db);
        List<Place> places = placeDB.getPlacesByTown(getIntent().getIntExtra("idTown", 0));
        listViewPlaces = (ListView) findViewById(R.id.listPlaces);

        PlaceAdapter adapter = new PlaceAdapter(this, places);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(adapter);
    }
}
