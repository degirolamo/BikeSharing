package com.example.daniel.bikesharing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

import static com.example.daniel.bikesharing.R.id.listPlaces;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewRents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new DatabaseHelper(getApplicationContext());

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCantons(v);
            }
        });

        RentDB rentDB = new RentDB(db);
        List<Rent> rents = rentDB.getRentsByPerson(/*getIntent().getIntExtra("idTown", 0)*/ 2);
        listViewRents = (ListView) findViewById(R.id.listRents);

        RentAdapter adapter = new RentAdapter(this, rents);
        listViewRents.setAdapter(adapter);
        listViewRents.setOnItemClickListener(adapter);
    }

    public void displayCantons(View v) {
        Intent i = new Intent(getApplicationContext(), CantonActivity.class);
        startActivity(i);
    }
}
