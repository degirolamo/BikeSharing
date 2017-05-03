package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;
import com.example.daniel.bikesharing.ObjectDB.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;
import static com.example.daniel.bikesharing.R.id.spinEditProfileCanton;
import static com.example.daniel.bikesharing.R.id.start;
import static com.example.daniel.bikesharing.R.id.txtName;
import static com.example.daniel.bikesharing.R.id.txtPassword;
import static com.example.daniel.bikesharing.R.string.cantons;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class EditProfileActivity.java
 * Date 02.05.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to edit the profile
 */

public class EditProfileActivity extends AppCompatActivity implements Serializable {

    PersonDB personDB;
    EditText txtEmail;
    EditText txtFirstname;
    EditText txtLastname;
    Spinner spinCanton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolEditProfile);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.edit);
        setSupportActionBar(toolbar);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        CantonDB cantonDB = new CantonDB(db);
        personDB = new PersonDB(db);
        List<Canton> cantons = cantonDB.getCantons();
        List<String> cantonsNames = new ArrayList<>();
        for (Canton canton : cantons) {
            cantonsNames.add(canton.getName());
        }

        spinCanton = (Spinner) findViewById(R.id.spinEditProfileCanton);
        spinCanton.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, cantonsNames));
        spinCanton.setSelection(USER_CONNECTED.getIdCanton() - 1);

        txtEmail = (EditText) findViewById(R.id.txtEditProfileEmail);
        txtLastname = (EditText) findViewById(R.id.txtEditProfileLastname);
        txtFirstname = (EditText) findViewById(R.id.txtEditProfileFirstname);

        txtEmail.setText(USER_CONNECTED.getEmail());
        txtLastname.setText(USER_CONNECTED.getLastname());
        txtFirstname.setText(USER_CONNECTED.getFirstname());

        Button btnValidate = (Button) findViewById(R.id.btnEditProfileValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtLastname.getText().toString().equals("") && !txtFirstname.getText().toString().equals("") &&
                        !txtEmail.getText().toString().equals("")) {
                    if(!personDB.isEmailExisting(USER_CONNECTED.getId(), txtEmail.getText().toString())) {
                        personDB.updatePerson(USER_CONNECTED.getId(), spinCanton.getSelectedItemPosition() + 1, txtEmail.getText().toString(),
                                txtFirstname.getText().toString(), txtLastname.getText().toString());
                        USER_CONNECTED = personDB.getPerson(USER_CONNECTED.getId());
                        finish();
                        overridePendingTransition(0, 0);
                        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                        i.putExtra("idPerson", USER_CONNECTED.getId());
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    } else
                        Toast.makeText(getApplicationContext(), R.string.errorMailExisting, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.errorProfilEmptyFields, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
