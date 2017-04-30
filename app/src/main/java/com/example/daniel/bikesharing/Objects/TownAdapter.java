package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Town;
import com.example.daniel.bikesharing.PlaceActivity;
import com.example.daniel.bikesharing.R;
import com.example.daniel.bikesharing.TownActivity;

import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;
import static com.example.daniel.bikesharing.R.id.btnCanton;
import static com.example.daniel.bikesharing.R.id.imgCanton;
import static com.example.daniel.bikesharing.R.id.listCantons;

/**
 * Created by pedro on 20.04.2017.
 */

public class TownAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<Town> listTowns;
    private Activity activity;
    private static LayoutInflater inflater = null;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_canton.xml
    public TownAdapter(Activity activity, List<Town> listTowns) {
        this.listTowns = listTowns;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //returns the list's number of items
    @Override
    public int getCount() {
        return listTowns.size();
    }

    //returns an item depending on its position
    @Override
    public Object getItem(int position) {
        return listTowns.get(position);
    }

    //returns item's id depending on its position
    @Override
    public long getItemId(int position) {
        return listTowns.indexOf(getItem(position));
    }

    //returns the view of an item of the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
        view = inflater.inflate(R.layout.list_simple, null);

        Button btnText = (Button) view.findViewById(R.id.btnText);

        //setting all values in listview
        btnText.setText(listTowns.get(position).getName());

        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity.getApplicationContext(), PlaceActivity.class);
        i.putExtra("idTown", listTowns.get(position).getId());
        activity.startActivity(i);
    }
}
