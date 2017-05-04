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
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class ProfileActivity.java
 * Date 26.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to display the profile of the user
 */

public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView txtName;
    TextView txtFirstname;
    TextView txtEmail;
    TextView txtCanton;
    TextView txtIsAdmin;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolProfile);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.Profile);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());
        PersonDB personDB = new PersonDB(db);
        person = personDB.getPerson(getIntent().getIntExtra("idPerson", 0));

        txtName = (TextView) findViewById(R.id.txtProfileLastname);
        txtFirstname = (TextView) findViewById(R.id.txtProfileFirstname);
        txtEmail = (TextView) findViewById(R.id.txtProfileEmail);
        txtCanton = (TextView) findViewById(R.id.txtProfileCanton);
        txtIsAdmin = (TextView) findViewById(R.id.txtProfileIsAdmin);

        txtName.setText(person.getLastname());
        txtFirstname.setText(person.getFirstname());
        txtEmail.setText(person.getEmail());
        CantonDB cantonDB = new CantonDB(db);
        txtCanton.setText(String.valueOf(cantonDB.getCanton(person.getIdCanton()).getName()));
        String role;
        if(person.isAdmin() == 1)
            role = "Administrateur";
        else
            role = "Utilisateur";
        txtIsAdmin.setText(role);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(USER_CONNECTED.getId() == person.getId())
            inflater.inflate(R.menu.edit, menu);
        inflater.inflate(R.menu.settings, menu);
        MenuItem itemProfile = menu.findItem(R.id.action_profile);
        itemProfile.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEdit:
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
        finishAffinity();
        if(getIntent().getStringExtra("parentClass").equals("AdminUsersActivity")) {
            Intent i = new Intent(getApplicationContext(), AdminUsersActivity.class);
        } else {
            if(USER_CONNECTED.isAdmin() == 1)
                startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));
            else
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
