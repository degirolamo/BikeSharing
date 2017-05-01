package com.example.daniel.bikesharing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.Objects.CantonAdapter;

import java.util.List;

import static android.media.CamcorderProfile.get;

public class CantonActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewCantons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canton);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolCantons);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.cantons);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());
        CantonDB cantonDB = new CantonDB(db);
        List<Canton> cantons = cantonDB.getCantons();
        listViewCantons = (ListView) findViewById(R.id.listCantons);

        CantonAdapter adapter = new CantonAdapter(this, cantons);
        listViewCantons.setAdapter(adapter);
        listViewCantons.setOnItemClickListener(adapter);
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
