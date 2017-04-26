package com.example.daniel.bikesharing;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;

import static android.R.attr.id;
import static com.example.daniel.bikesharing.R.id.listCantons;

/**
 * Created by pedro on 22.04.2017.
 */

public class InfosPlaceActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_infos);

        db = new DatabaseHelper(getApplicationContext());

        PlaceDB placeDB = new PlaceDB(db);
        Place place = placeDB.getPlace(getIntent().getIntExtra("idPlace", 0));
        TextView txtName = (TextView) findViewById(R.id.txtPlaceName);
        ImageView imgPicture = (ImageView) findViewById(R.id.imgPlacePicture);
        TextView txtAddress = (TextView) findViewById(R.id.txtPlaceAddress);
        TextView txtDispoBikes = (TextView) findViewById(R.id.txtPlaceDispoBikes);
        TextView txtDispoSlots = (TextView) findViewById(R.id.txtPlaceDispoSlots);

        txtName.setText(place.getName());
        if(place.getPicture() != null) {
            int id = getResources().getIdentifier("com.example.daniel.bikesharing:drawable/" + place.getPicture(), null, null);
            imgPicture.setImageResource(id);
        }
        txtAddress.setText(place.getAddress());
        int nbBikes = new BikeDB(db).getNbBikes(place.getId());
        txtDispoBikes.setText(String.valueOf(nbBikes));
        txtDispoSlots.setText(String.valueOf(place.getNbPlaces() - nbBikes));

        Button btnRent = (Button) findViewById(R.id.btnRent);
        btnRent.setOnClickListener(new DisplayQRCode());
    }

    private class DisplayQRCode implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), QRCodeActivity.class);
            startActivity(i);
        }
    }
}
