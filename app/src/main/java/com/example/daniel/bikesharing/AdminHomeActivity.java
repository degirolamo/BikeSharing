package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
 * Class AdminHomeActivity.java
 * Date 20.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used display the home of the administration section
 */

public class AdminHomeActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnUsers;

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

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
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
                finish();
                return true;
            case R.id.action_profile:
                i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("idPerson", USER_CONNECTED.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
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
}
