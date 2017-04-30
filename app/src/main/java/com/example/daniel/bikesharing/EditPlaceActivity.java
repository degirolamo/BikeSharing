package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;

/**
 * Created by pedro on 29.04.2017.
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
                placeDB.updatePlace(idPlace, txtName.getText().toString(), txtPicture.getText().toString(),
                        Integer.parseInt(txtNbPlaces.getText().toString()), txtAddress.getText().toString(), place.getIdTown());
                finish();
                overridePendingTransition(0, 0);
                Intent i = new Intent(getApplicationContext(), InfosPlaceActivity.class);
                i.putExtra("idPlace", idPlace);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });
    }
}
