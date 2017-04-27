package com.example.daniel.bikesharing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        db = new DatabaseHelper(getApplicationContext());
        CantonDB cantonDB = new CantonDB(db);
        List<Canton> cantons = cantonDB.getCantons();
        listViewCantons = (ListView) findViewById(R.id.listCantons);

        CantonAdapter adapter = new CantonAdapter(this, cantons);
        listViewCantons.setAdapter(adapter);
        listViewCantons.setOnItemClickListener(adapter);
    }
}
