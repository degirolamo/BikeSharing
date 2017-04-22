package com.example.daniel.bikesharing;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

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
        canton.insertCanton("Argovie", "ag.png");
        canton.insertCanton("Appenzell Rhodes-Intérieures", "ai.png");
        canton.insertCanton("Appenzell Rhodes-Extérieures", "ar.png");
        canton.insertCanton("Berne", "be.png");
        canton.insertCanton("Bâle-Campagne", "bl.png");
        canton.insertCanton("Bâle-Ville", "bs.png");
        canton.insertCanton("Fribourg", "fr.png");
        canton.insertCanton("Genève", "ge.png");
        canton.insertCanton("Glaris", "gl.png");
        canton.insertCanton("Grisons", "gr.png");
        canton.insertCanton("Jura", "ju.png");
        canton.insertCanton("Lucerne", "lu.png");
        canton.insertCanton("Neuchâtel", "ne.png");
        canton.insertCanton("Nidwald", "nw.png");
        canton.insertCanton("Obwald", "ow.png");
        canton.insertCanton("Saint-Gall", "sg.png");
        canton.insertCanton("Schaffhouse", "sh.png");
        canton.insertCanton("Soleure", "so.png");
        canton.insertCanton("Schwytz", "sz.png");
        canton.insertCanton("Thurgovie", "tg.png");
        canton.insertCanton("Tessin", "ti.png");
        canton.insertCanton("Uri", "ur.png");
        canton.insertCanton("Valais", "vs.png");
        canton.insertCanton("Vaud", "vd.png");
        canton.insertCanton("Zoug", "zg.png");
        canton.insertCanton("Zurich", "zh.png");

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

        PlaceDB placeDB = new PlaceDB(db);
        placeDB.insertPlace("Gare CFF", "hhj", 1);
        placeDB.insertPlace("Gare AOMC", "", 1);
        placeDB.insertPlace("Vieux-Pont", "", 1);
        placeDB.insertPlace("Piscine", "", 1);
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

//        String can = cantons.get(1).getPicture().substring(0, 2);
//        Log.e("PICTURE", can);

        Intent i = new Intent(getApplicationContext(), CantonActivity.class);
        startActivity(i);
    }
}
