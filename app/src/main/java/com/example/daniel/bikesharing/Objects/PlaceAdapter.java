package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.daniel.bikesharing.InfosPlaceActivity;
import com.example.daniel.bikesharing.ObjectDB.Place;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.PlaceActivity;
import com.example.daniel.bikesharing.R;

import java.util.List;

import static com.example.daniel.bikesharing.R.id.listTowns;

/**
 * Created by pedro on 20.04.2017.
 */

public class PlaceAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<Place> listPlaces;
    private Activity activity;
    private static LayoutInflater inflater = null;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_canton.xml
    public PlaceAdapter(Activity activity, List<Place> listPlaces) {
        this.listPlaces = listPlaces;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        if(convertView == null)
            view = inflater.inflate(R.layout.list_simple, null);

        Button btnText = (Button) view.findViewById(R.id.btnText);

        //setting all values in listview
        btnText.setText(listPlaces.get(position).getName());

        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity.getApplicationContext(), InfosPlaceActivity.class);
        i.putExtra("idPlace", listPlaces.get(position).getId());
        activity.startActivity(i);
    }
}
