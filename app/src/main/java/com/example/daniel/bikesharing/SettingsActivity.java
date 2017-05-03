package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class SettingsActivity.java
 * Date 29.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity to display the settings of the application
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolSettings);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);

        Button btnLanguage = (Button) findViewById(R.id.btnLanguage);
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLanguage(v);
            }
        });
    }

    public void displayLanguage(View v) {
        Intent i = new Intent(getApplicationContext(), LanguageActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        MenuItem itemSettings = menu.findItem(R.id.action_settings);
        itemSettings.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("idPerson", USER_CONNECTED.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.action_logout);
                builder.setMessage(R.string.warningLogout);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        USER_CONNECTED = null;
                        IS_CONNECTED = 0;
                        dialog.dismiss();
                        finishAffinity();
                        finish();
                        overridePendingTransition(0, 0);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i;
        if(USER_CONNECTED.isAdmin() == 1)
            i = new Intent(getApplicationContext(), AdminHomeActivity.class);
        else
            i = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(i);
        finish();
    }
}
