package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Rent;
import com.example.daniel.bikesharing.Objects.CantonAdapter;
import com.example.daniel.bikesharing.Objects.PlaceAdapter;
import com.example.daniel.bikesharing.Objects.RentAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;
import static com.example.daniel.bikesharing.R.id.listPlaces;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewRents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolSearch);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCantons(v);
            }
        });

        PlaceDB placeDB = new PlaceDB(db);
        List<Place> places = new ArrayList<>();
        List<Integer> nbPlaces = placeDB.getNbRentsByPerson(2);
        for (int nbPlace : nbPlaces) {
            places.add(placeDB.getPlace(nbPlace));
        }
        listViewRents = (ListView) findViewById(R.id.listRents);

        RentAdapter adapter = new RentAdapter(this, places);
        listViewRents.setAdapter(adapter);
        listViewRents.setOnItemClickListener(adapter);
    }

    public void displayCantons(View v) {
        Intent i = new Intent(getApplicationContext(), CantonActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(USER_CONNECTED.isAdmin() == 0) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("EXIT", true);
            finish();
        } else
            super.onBackPressed();
    }
}
