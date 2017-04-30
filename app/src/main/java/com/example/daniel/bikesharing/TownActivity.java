package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Created by pedro on 19.04.2017.
 */

public class TownActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolTowns);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ListView listViewTowns;
        DatabaseHelper db;

        db = new DatabaseHelper(getApplicationContext());

        TownDB townDB = new TownDB(db);
        List<Town> towns = townDB.getTownsByCanton(getIntent().getIntExtra("idCanton", 0));
        listViewTowns = (ListView) findViewById(R.id.listTowns);

        TownAdapter adapter = new TownAdapter(this, towns);
        listViewTowns.setAdapter(adapter);
        listViewTowns.setOnItemClickListener(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
}
