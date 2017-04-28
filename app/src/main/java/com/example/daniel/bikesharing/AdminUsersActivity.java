package com.example.daniel.bikesharing;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.Objects.CantonAdapter;
import com.example.daniel.bikesharing.Objects.PersonAdapter;

import java.util.List;

import static com.example.daniel.bikesharing.R.string.places;

public class AdminUsersActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAdminUsers);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());
        PersonDB personDB = new PersonDB(db);
        List<Person> persons = personDB.getPersons();
        listViewPersons = (ListView) findViewById(R.id.listUsers);

        PersonAdapter adapter = new PersonAdapter(this, persons);
        listViewPersons.setAdapter(adapter);
        listViewPersons.setOnItemClickListener(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

}
