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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.R;
import com.example.daniel.bikesharing.TownActivity;

import java.util.List;

import static com.example.daniel.bikesharing.R.id.listUsers;
import static com.example.daniel.bikesharing.R.id.textView;

/**
 * Created by Daniel on 25.04.2017.
 */

public class PersonAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<Person> listPersons;
    private Activity activity;
    private static LayoutInflater inflater = null;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_Usersxml
    public PersonAdapter(Activity activity, List<Person> listPersons) {
        this.listPersons = listPersons;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() { return listPersons.size();}

    @Override
    public Object getItem(int position) {
        return listPersons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listPersons.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.list_user, null);

        TextView txtuser = (TextView) view.findViewById(R.id.txtuser);

        //setting all values in listview
        txtuser.setText(listPersons.get(position).getFirstname() + " " + listPersons.get(position).getLastname());
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

     //   activity.startActivity(i);
    }
}
