package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.R.id.btnSearch;
import static com.example.daniel.bikesharing.R.id.btnValidate;
import static com.example.daniel.bikesharing.R.id.txtName;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class AddPlaceActivity.java
 * Date 29.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to add places
 */

public class AddPlaceActivity extends AppCompatActivity {

    private PlaceDB placeDB;
    private EditText txtName;
    private EditText txtPicture;
    private EditText txtNbPlaces;
    private EditText txtAddress;
    private int idTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAddPlace);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.AddPlace);
        setSupportActionBar(toolbar);

        txtName = (EditText) findViewById(R.id.txtAddPlaceName);
        txtPicture = (EditText) findViewById(R.id.txtAddPlacePicture);
        txtNbPlaces = (EditText) findViewById(R.id.txtAddPlaceNbPlaces);
        txtAddress = (EditText) findViewById(R.id.txtAddPlaceAddress);
        idTown = getIntent().getIntExtra("idTown", 0);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        placeDB = new PlaceDB(db);

        Button btnValidate = (Button) findViewById(R.id.btnAddPlaceValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!txtName.getText().toString().equals("") && !txtNbPlaces.getText().toString().equals("")
                    && !txtAddress.getText().toString().equals("")) {
                if(!placeDB.isExistingPlace(0, txtName.getText().toString(), txtAddress.getText().toString())) {
                    placeDB.insertPlace(txtName.getText().toString(), txtPicture.getText().toString(),
                            Integer.parseInt(txtNbPlaces.getText().toString()), txtAddress.getText().toString(), idTown);
                    finish();
                    overridePendingTransition(0, 0);
                    Intent i = new Intent(getApplicationContext(), PlaceActivity.class);
                    i.putExtra("idTown", idTown);
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
}
