package com.example.daniel.bikesharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.Objects.CantonAdapter;
import com.example.daniel.bikesharing.Objects.TownAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro on 19.04.2017.
 */

public class TownActivity extends AppCompatActivity {
    DatabaseHelper db;
    ListView listViewTowns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        db = new DatabaseHelper(getApplicationContext());

        TownDB townDB = new TownDB(db);
        List<Town> towns = townDB.getTownsByCanton(getIntent().getIntExtra("idCanton", 0));
        listViewTowns = (ListView) findViewById(R.id.listTowns);

        TownAdapter adapter = new TownAdapter(this, towns);
        listViewTowns.setAdapter(adapter);
        listViewTowns.setOnItemClickListener(adapter);
    }
}
