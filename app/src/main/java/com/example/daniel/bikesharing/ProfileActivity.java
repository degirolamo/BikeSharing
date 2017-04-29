package com.example.daniel.bikesharing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.Objects.RentAdapter;
import com.example.daniel.bikesharing.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView txtName;
    TextView txtFirstname;
    TextView txtEmail;
    TextView txtCanton;
    TextView txtIsAdmin;
    ListView listViewRents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(getApplicationContext());
        PersonDB personDB = new PersonDB(db);
        Person person = personDB.getPerson(getIntent().getIntExtra("idPerson", 0));

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

        //Places most rent
//        PlaceDB placeDB = new PlaceDB(db);
//        List<Place> places = new ArrayList<>();
//        List<Integer> nbPlaces = placeDB.getNbRentsByPerson(person.getId());
//        for (int nbPlace : nbPlaces) {
//            places.add(placeDB.getPlace(nbPlace));
//        }
//        listViewRents = (ListView) findViewById(R.id.listProfileRents);
//
//        RentAdapter adapter = new RentAdapter(this, places);
//        listViewRents.setAdapter(adapter);
//        listViewRents.setOnItemClickListener(adapter);
    }
}
