package com.example.daniel.bikesharing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.Objects.PersonAdapter;
import com.example.daniel.bikesharing.Objects.PlaceAdapter;

import java.util.List;

public class AdminPlacesActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_place);

        db = new DatabaseHelper(getApplicationContext());
        PlaceDB placeDB = new PlaceDB(db);
        List<Place> places = placeDB.getPlaces();
        listViewPlaces = (ListView) findViewById(R.id.listPlaces);

        PlaceAdapter adapter = new PlaceAdapter(this, places);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(adapter);
    }
}
