package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by pedro on 23.04.2017.
 */

public class RentAdapter  extends BaseAdapter implements AdapterView.OnItemClickListener {
    private DatabaseHelper db;
    private List<Rent> listRents;
    private Activity activity;
    private static LayoutInflater inflater = null;
    private PlaceDB placeDB;
    private Place place;
    private TownDB townDB;
    private Town town;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_cantonxml
    public RentAdapter(Activity activity, List<Rent> listRents) {
        this.listRents = listRents;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHelper(activity.getApplicationContext());
        placeDB = new PlaceDB(db);
        townDB = new TownDB(db);
    }

    //returns the list's number of items
    @Override
    public int getCount() {
        return listRents.size();
    }

    //returns an item depending on its position
    @Override
    public Object getItem(int position) {
        return listRents.get(position);
    }

    //returns item's id depending on its position
    @Override
    public long getItemId(int position) {
        return listRents.indexOf(getItem(position));
    }

    //returns the view of an item of the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        place = placeDB.getPlaceByBike(listRents.get(position).getIdBike());
        Log.e("PLACE", place.getId() + "");
        town = townDB.getTown(place.getIdTown());

        if(convertView == null)
            view = inflater.inflate(R.layout.list_rent, null);

        TextView txtPlace = (TextView) view.findViewById(R.id.txtPlace);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);

        //setting all values in listview
        Date dateD = new Date();
        Date dateF = new Date();
        String strDateD;
        String strDateF;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            dateD = formatter.parse(listRents.get(position).getBeginDate());
            dateF = formatter.parse(listRents.get(position).getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        strDateD = formatter.format(dateD);
        strDateF = formatter.format(dateF);

        txtPlace.setText(place.getName());
        txtDate.setText(town.getNpa() + " " + town.getName() + " - " + strDateF);

        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity.getApplicationContext(), InfosPlaceActivity.class);
        place = placeDB.getPlaceByBike(listRents.get(position).getIdBike());
        i.putExtra("idPlace", place.getId());
        activity.startActivity(i);
    }
}
