package com.example.daniel.bikesharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.Objects.CantonAdapter;

import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class HomeActivity.java
 * Date 26.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to display the home of the application
 */

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(IS_CONNECTED == 1) {
            Intent i;
            if(USER_CONNECTED.getAdmin() == 1)
                i = new Intent(getApplicationContext(), AdminHomeActivity.class);
            else
                i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
            finish();
        }

        db = new DatabaseHelper(getApplicationContext());

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
