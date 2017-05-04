package com.example.daniel.bikesharing.Objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ProfileActivity;
import com.example.daniel.bikesharing.R;

import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package Objects
 * Class PersonAdapter.java
 * Date 25.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Adapter used to display a ListView of persons
 */

public class PersonAdapter extends BaseAdapter {
    private List<Person> listPersons;
    private Activity activity;
    private static LayoutInflater inflater = null;
    private ImageButton btnProfile;
    private ImageButton btnAdmin;
    private ImageButton btnDelete;
    private String message;
    private PersonDB personDB;
    private DatabaseHelper db;

    //We pass the layout to obtain a LayoutInflater
    //to use our list_Usersxml
    public PersonAdapter(Activity activity, List<Person> listPersons) {
        this.listPersons = listPersons;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHelper(activity.getApplicationContext());
        personDB = new PersonDB(db);
    }

    @Override
    public int getCount() { return listPersons.size();}

    @Override
    public Object getItem(int position) {
        return listPersons.get(position);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public long getItemId(int position) {
        return listPersons.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.list_user, null);

        TextView txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        //setting all values in listview
        txtUserName.setText(listPersons.get(position).getFirstname() + " " + listPersons.get(position).getLastname());

        btnProfile = (ImageButton) view.findViewById(R.id.imgProfile);
        btnProfile.setOnClickListener(new DisplayProfile(ProfileActivity.class, position));

        btnAdmin = (ImageButton) view.findViewById(R.id.imgAdmin);
        int isAdmin = 0;
        if (listPersons.get(position).isAdmin() == 1) {
            //noinspection deprecation
            btnAdmin.setColorFilter(view.getResources().getColor(R.color.colorBlack));
            message = activity.getResources().getString(R.string.warningDeleteAdmin) + " " +
                    listPersons.get(position).getFirstname() + " " + listPersons.get(position).getLastname();
        } else {
            //noinspection deprecation
            btnAdmin.setColorFilter(view.getResources().getColor(R.color.colorGrey));
            message = activity.getResources().getString(R.string.warningAddAdmin) + " " +
                    listPersons.get(position).getFirstname() + " " + listPersons.get(position).getLastname();
            isAdmin = 1;
        }
        btnAdmin.setOnClickListener(new SetAdminRights(message, position, isAdmin));


        btnDelete = (ImageButton) view.findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(new DeletePerson(position));
        return view;
    }

    private class DisplayProfile implements View.OnClickListener {
        Class classToDisplay;
        int position;

        private DisplayProfile(Class classToDisplay, int position) {
            this.classToDisplay = classToDisplay;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(activity.getApplicationContext(), classToDisplay);
            i.putExtra("idPerson", listPersons.get(position).getId());
            i.putExtra("parentClass", "AdminUsersActivity");
            activity.startActivity(i);
        }
    }

    private class SetAdminRights implements View.OnClickListener {
        String message;
        int position;
        int isAdmin;

        private SetAdminRights(String message, int position, int isAdmin) {
            this.message = message;
            this.position = position;
            this.isAdmin = isAdmin;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.app_name);
            builder.setMessage(message);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    personDB.setAdminRights(listPersons.get(position).getId(), isAdmin);
                    dialog.dismiss();
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(activity.getIntent());
                    activity.overridePendingTransition(0, 0);
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private class DeletePerson implements View.OnClickListener {
        int position;

        private DeletePerson(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.app_name);
            builder.setMessage(activity.getResources().getString(R.string.warningDeleteUser) + " " +
                listPersons.get(position).getFirstname() + " " + listPersons.get(position).getLastname());
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    personDB.deletePerson(listPersons.get(position).getId());
                    dialog.dismiss();
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(activity.getIntent());
                    activity.overridePendingTransition(0, 0);
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
