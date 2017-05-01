package com.example.daniel.bikesharing;

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

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

public class PlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolPlaces);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.places);
        setSupportActionBar(toolbar);

        DatabaseHelper db;
        ListView listViewPlaces;

        db = new DatabaseHelper(getApplicationContext());

        final PlaceDB placeDB = new PlaceDB(db);
        List<Place> places = placeDB.getPlacesByTown(getIntent().getIntExtra("idTown", 0));
        listViewPlaces = (ListView) findViewById(R.id.listPlaces);

        PlaceAdapter adapter = new PlaceAdapter(this, places);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(adapter);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabPlaceAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPlaceActivity.class);
                i.putExtra("idTown", getIntent().getIntExtra("idTown", 0));
                startActivity(i);
            }
        });
        if(USER_CONNECTED.isAdmin() == 0) {
            fabAdd.hide();
        }
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
}
