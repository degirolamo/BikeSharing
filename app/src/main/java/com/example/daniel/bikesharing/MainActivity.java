package com.example.daniel.bikesharing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.bikesharing.DB.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    // Database Helper pour Ped
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
    }


}
