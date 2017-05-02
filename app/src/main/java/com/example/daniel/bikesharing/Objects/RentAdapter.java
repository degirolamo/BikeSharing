package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.InfosPlaceActivity;
import com.example.daniel.bikesharing.ObjectDB.Bike;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Rent;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.R;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.example.daniel.bikesharing.R.id.listRents;

/**
 * Created by pedro on 23.04.2017.
 */

public class RentAdapter  extends BaseAdapter implements AdapterView.OnItemClickListener {
    private DatabaseHelper db;
    private List<Place> listPlaces;
    private Activity activity;
    private static LayoutInflater inflater = null;
    private TownDB townDB;
    private Town town;
    private Button btnPlace;
    private Button btnTown;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_cantonxml
    public RentAdapter(Activity activity, List<Place> listPlaces) {
        this.listPlaces = listPlaces;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHelper(activity.getApplicationContext());
        townDB = new TownDB(db);
    }

    //returns the list's number of items
    @Override
    public int getCount() {
        return listPlaces.size();
    }

    //returns an item depending on its position
    @Override
    public Object getItem(int position) {
        return listPlaces.get(position);
    }

    //returns item's id depending on its position
    @Override
    public long getItemId(int position) {
        return listPlaces.indexOf(getItem(position));
    }

    //returns the view of an item of the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        town = townDB.getTown(listPlaces.get(position).getIdTown());

        if(convertView == null)
            view = inflater.inflate(R.layout.list_rent, null);

        btnPlace = (Button) view.findViewById(R.id.btnPlace);
        btnTown = (Button) view.findViewById(R.id.btnTown);

        //setting all values in listview
        btnPlace.setText(listPlaces.get(position).getName());
        btnTown.setText(town.getNpa() + " " + town.getName());

        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity.getApplicationContext(), InfosPlaceActivity.class);
        i.putExtra("idPlace", listPlaces.get(position).getId());
        i.putExtra("parentClass", "SearchActivity");
        activity.startActivity(i);
        activity.finish();
    }
}
