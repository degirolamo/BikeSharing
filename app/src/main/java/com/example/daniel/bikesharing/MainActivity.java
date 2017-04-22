package com.example.daniel.bikesharing;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.daniel.bikesharing.R.string.places;

public class MainActivity extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        db.reloadDatabase();

        CantonDB canton = new CantonDB(db);
        canton.insertCanton("Argovie", "ag");
        canton.insertCanton("Appenzell Rhodes-Intérieures", "ai");
        canton.insertCanton("Appenzell Rhodes-Extérieures", "ar");
        canton.insertCanton("Berne", "be");
        canton.insertCanton("Bâle-Campagne", "bl");
        canton.insertCanton("Bâle-Ville", "bs");
        canton.insertCanton("Fribourg", "fr");
        canton.insertCanton("Genève", "ge");
        canton.insertCanton("Glaris", "gl");
        canton.insertCanton("Grisons", "gr");
        canton.insertCanton("Jura", "ju");
        canton.insertCanton("Lucerne", "lu");
        canton.insertCanton("Neuchâtel", "ne");
        canton.insertCanton("Nidwald", "nw");
        canton.insertCanton("Obwald", "ow.");
        canton.insertCanton("Saint-Gall", "sg");
        canton.insertCanton("Schaffhouse", "sh");
        canton.insertCanton("Soleure", "so");
        canton.insertCanton("Schwytz", "sz");
        canton.insertCanton("Thurgovie", "tg");
        canton.insertCanton("Tessin", "ti");
        canton.insertCanton("Uri", "ur");
        canton.insertCanton("Valais", "vs");
        canton.insertCanton("Vaud", "vd");
        canton.insertCanton("Zoug", "zg");
        canton.insertCanton("Zurich", "zh");

        TownDB townDB = new TownDB(db);
        townDB.insertTown(23, "Monthey", 1870);
        townDB.insertTown(23, "Martigny", 1920);
        townDB.insertTown(23, "Sierre", 3960);
        townDB.insertTown(23, "Sion", 1950);
        townDB.insertTown(23, "St-Maurice", 1890);
        townDB.insertTown(24, "Aigle", 1860);
        townDB.insertTown(24, "Vevey", 1800);
        townDB.insertTown(24, "Montreux", 1820);
        townDB.insertTown(8, "Genève", 1200);

        BikeDB bikeDB = new BikeDB(db);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);
        bikeDB.insertBike(1);

        PlaceDB placeDB = new PlaceDB(db);
        placeDB.insertPlace("Gare CFF", "monthey_gare_cff", 8, "Gare CFF", 1);
        placeDB.insertPlace("Gare AOMC", "", 8, "Gare AOMC", 1);
        placeDB.insertPlace("Vieux-Pont", "", 8, "Route de la Cretta 2", 1);
        placeDB.insertPlace("Piscine", "", 8, "Avenue de l'Europe 115", 1);
//
//        List<Canton> cantons = canton.getCantons();
//        for (Canton c : cantons) {
//            Log.e("CANTONS", "id = " + c.getId() + ", name = " + c.getName());
//        }
//
//        List<Town> towns = townDB.getTownsByCanton(24);
//        for (Town town : towns) {
//            Log.e("VILLES", "id = " + town.getId() + ", name = " + town.getName() + ", npa = " + town.getNpa());
//        }
//
//        List<Place> places = placeDB.getPlacesByTown(1);
//        for (Place place : places) {
//            Log.e("PLACES", "id = " + place.getId() + ", name = " + place.getName() + ", idTown = " + place.getIdTown());
//        }
//
//        List<Bike> bikes = bikeDB.getBikesByPlace(1);
//        for (Bike bike : bikes) {
//            Log.e("BIKES", "id = " + bike.getId() + ", idPlace = " + bike.getIdPlace());
//        }

//        String can = cantons.get(1).getPicture().substring(0, 2);
//        Log.e("PICTURE", can);

        Intent i = new Intent(getApplicationContext(), CantonActivity.class);
        startActivity(i);
    }
}
