package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Class AddPlaceActivity.java
 * Date 14.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to display the list of the cantons
 */

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
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("idPerson", USER_CONNECTED.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(CantonActivity.this);
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
