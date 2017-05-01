package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Place;

import static android.R.attr.id;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;
import static com.example.daniel.bikesharing.R.id.btnEdit;
import static com.example.daniel.bikesharing.R.id.listCantons;

/**
 * Created by pedro on 22.04.2017.
 */

public class InfosPlaceActivity extends AppCompatActivity {

    private PlaceDB placeDB;
    private Place place;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        if(USER_CONNECTED.isAdmin() == 1) {
            inflater.inflate(R.menu.edit, menu);
            inflater.inflate(R.menu.delete, menu);
        }
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_infos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolInfosPlace);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.InfoPlace);
        setSupportActionBar(toolbar);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        placeDB = new PlaceDB(db);
        place = placeDB.getPlace(getIntent().getIntExtra("idPlace", 0));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEdit:
                Intent i = new Intent(getApplicationContext(), EditPlaceActivity.class);
                i.putExtra("idPlace", place.getId());
                startActivity(i);
                return true;
            case R.id.btnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(InfosPlaceActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Etes-vous sûr de vouloir supprimer la place " + place.getName());
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        placeDB.deletePlace(place.getId());
                        dialog.dismiss();
                        finish();
                        overridePendingTransition(0, 0);
                        PlaceActivity placeActivity = new PlaceActivity();
                        Intent i = new Intent(getApplicationContext(), placeActivity.getClass());
                        i.putExtra("idTown", place.getIdTown());
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
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
