package com.example.daniel.bikesharing;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Rent;
import com.example.daniel.bikesharing.ObjectDB.Town;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.daniel.bikesharing.R.string.places;
import static com.example.daniel.bikesharing.R.styleable.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static int IS_CONNECTED = 0;
    public static Person USER_CONNECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolMain);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        DatabaseHelper db;
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
        canton.insertCanton("Obwald", "ow");
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

        PlaceDB placeDB = new PlaceDB(db);
        placeDB.insertPlace("Gare CFF", "monthey_gare_cff", 8, "Gare CFF", 1);
        placeDB.insertPlace("Gare AOMC", "", 8, "Gare AOMC", 1);
        placeDB.insertPlace("Vieux-Pont", "", 8, "Route de la Cretta 2", 1);
        placeDB.insertPlace("Piscine", "", 8, "Avenue de l'Europe 115", 1);

        BikeDB bikeDB = new BikeDB(db);
        bikeDB.insertBike(1);
        bikeDB.insertBike(2);
        bikeDB.insertBike(3);
        bikeDB.insertBike(4);
        bikeDB.insertBike(1);
        bikeDB.insertBike(2);
        bikeDB.insertBike(3);
        bikeDB.insertBike(4);

        PersonDB personDB = new PersonDB(db);
        personDB.insertPerson(23, "pedro@pedro.com", "pass", "Pedro", "Ferreira", 1);
        personDB.insertPerson(22, "dan@dan.com", "pass", "Daniel", "De Girolamo", 1);
        personDB.insertPerson(1, "test@test.com", "pass", "Test", "Test", 0);

        RentDB rentDB = new RentDB(db);
        rentDB.insertRent(1, 2, "16-04-2017 02:01:00", "16-04-2017 14:12:24");
        rentDB.insertRent(5, 2, "17-04-2017 03:01:00", "18-04-2017 13:12:24");
        rentDB.insertRent(1, 2, "17-04-2017 04:01:00", "18-04-2017 12:12:24");
        rentDB.insertRent(5, 2, "18-04-2017 05:01:00", "18-04-2017 11:12:24");
        rentDB.insertRent(2, 2, "18-04-2017 06:01:00", "19-04-2017 10:12:24");
        rentDB.insertRent(3, 2, "20-04-2017 07:01:00", "20-04-2017 15:12:24");
        rentDB.insertRent(4, 2, "21-04-2017 08:01:00", "21-04-2017 16:12:24");
        rentDB.insertRent(6, 2, "22-04-2017 09:01:00", "22-04-2017 17:12:24");
        rentDB.insertRent(6, 2, "22-04-2017 09:01:00", "");
        rentDB.insertRent(6, 2, "22-04-2017 09:01:00", "");
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

//        List<Person> persons = personDB.getPersons();
//        for (Person person : persons) {
//            Log.e("PERSONS", "id = " + person.getId() + ", email = " + person.getEmail() + ", firstname = " + person.getFirstname());
//        }
//
//        List<Rent> rents = rentDB.getRentsByPerson(3);
//        for (Rent rent : rents) {
//            Log.e("RENTS", "idBike = " + rent.getIdBike() + ", idPerson = " + rent.getIdPerson() + ", beginDate = " + rent.getBeginDate() + ", endDate = " + rent.getEndDate());
//        }

        if (!getIntent().getBooleanExtra("EXIT", false)) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Log.e("EXIT", getIntent().getBooleanExtra("EXIT", false) + "");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
}
