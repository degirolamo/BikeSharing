package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnUsers;
    Button btnPlaces;
    Button btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAdminHome);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.administration);
        setSupportActionBar(toolbar);

        btnSearch = (Button) findViewById(R.id.btnAdminSearch);
        btnUsers = (Button) findViewById(R.id.btnAdminUsers);
//        btnPlaces = (Button) findViewById(R.id.btnAdminPlaces);
//        btnStats = (Button) findViewById(R.id.btnAdminStats);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminUsersActivity.class);
                startActivity(i);
            }
        });

//        btnPlaces.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), AdminPlacesActivity.class);
//                startActivity(i);
//            }
//        });
//
//        btnStats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(i);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("EXIT", true);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
}
