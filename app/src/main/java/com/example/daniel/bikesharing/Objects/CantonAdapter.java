package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.R;
import com.example.daniel.bikesharing.TownActivity;

import java.util.List;

/**
 * Created by pedro on 20.04.2017.
 */

public class CantonAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<Canton> listCantons;
    private Activity activity;
    private static LayoutInflater inflater = null;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_cantonxml
    public CantonAdapter(Activity activity, List<Canton> listCantons) {
        this.listCantons = listCantons;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //returns the list's number of items
    @Override
    public int getCount() {
        return listCantons.size();
    }

    //returns an item depending on its position
    @Override
    public Object getItem(int position) {
        return listCantons.get(position);
    }

    //returns item's id depending on its position
    @Override
    public long getItemId(int position) {
        return listCantons.indexOf(getItem(position));
    }

    //returns the view of an item of the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.list_canton, null);

        ImageView imgCanton = (ImageView) view.findViewById(R.id.imgCanton);
        Button btnCanton = (Button) view.findViewById(R.id.btnCanton);

        //setting all values in listview
        int id = activity.getResources().getIdentifier("com.example.daniel.bikesharing:drawable/" + listCantons.get(position).getPicture(), null, null);
        imgCanton.setImageResource(id);
        btnCanton.setText(listCantons.get(position).getName());

        return view;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity.getApplicationContext(), TownActivity.class);
        i.putExtra("idCanton", listCantons.get(position).getId());
        activity.startActivity(i);
    }
}
