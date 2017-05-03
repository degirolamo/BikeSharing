package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class EditPlaceActivity.java
 * Date 29.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to edit places
 */

public class EditPlaceActivity extends AppCompatActivity {

    private PlaceDB placeDB;
    private Place place;
    private EditText txtName;
    private EditText txtPicture;
    private EditText txtNbPlaces;
    private EditText txtAddress;
    private int idPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAddPlace);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.EditPlace);
        setSupportActionBar(toolbar);

        txtName = (EditText) findViewById(R.id.txtAddPlaceName);
        txtPicture = (EditText) findViewById(R.id.txtAddPlacePicture);
        txtNbPlaces = (EditText) findViewById(R.id.txtAddPlaceNbPlaces);
        txtAddress = (EditText) findViewById(R.id.txtAddPlaceAddress);
        idPlace = getIntent().getIntExtra("idPlace", 0);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        placeDB = new PlaceDB(db);
        place = placeDB.getPlace(idPlace);

        txtName.setText(place.getName());
        txtPicture.setText(place.getPicture());
        txtNbPlaces.setText(String.valueOf(place.getNbPlaces()));
        txtAddress.setText(place.getAddress());

        Button btnValidate = (Button) findViewById(R.id.btnAddPlaceValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtName.getText().toString().equals("") && !txtNbPlaces.getText().toString().equals("")
                        && !txtAddress.getText().toString().equals("")) {
                    if (!placeDB.isExistingPlace(place.getId(), txtName.getText().toString(), txtAddress.getText().toString())) {
                        placeDB.updatePlace(idPlace, txtName.getText().toString(), txtPicture.getText().toString(),
                                Integer.parseInt(txtNbPlaces.getText().toString()), txtAddress.getText().toString(), place.getIdTown());
                        finish();
                        overridePendingTransition(0, 0);
                        Intent i = new Intent(getApplicationContext(), InfosPlaceActivity.class);
                        i.putExtra("idPlace", idPlace);
                        i.putExtra("parentClass", getIntent().getStringExtra("parentClass"));
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Une place avec ce nom ou cette adresse existe déjà.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Le nom, le nombre de place et l'adresse ne peuvent pas être vides",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), InfosPlaceActivity.class);
        i.putExtra("idPlace", idPlace);
        i.putExtra("parentClass", getIntent().getStringExtra("parentClass"));
        startActivity(i);
        finish();
    }

    public boolean canContinue(String name, String address) {
        boolean nameExists = false;
        boolean addressExists = false;
        boolean canContinue = true;

        if(place.getName().equals(name))
            nameExists = true;
        if(place.getAddress().equals(address))
            addressExists = true;
        if(nameExists && addressExists)
            canContinue = true;

        return canContinue;
    }
}
