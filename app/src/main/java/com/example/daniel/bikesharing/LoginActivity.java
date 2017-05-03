package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Person;

import static com.example.daniel.bikesharing.MainActivity.IS_CONNECTED;
import static com.example.daniel.bikesharing.MainActivity.USER_CONNECTED;

/**
 * Project BikeSharing
 * Package com.example.daniel.bikesharing
 * Class LoginActivity.java
 * Date 20.04.2017
 * Authors Daniel De Girolamo & Pedro Gil Ferreira
 * Description Activity used to display the login page
 */

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnValidate;
    private PersonDB personDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolLogin);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.login);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());
        personDB = new PersonDB(db);

        if(IS_CONNECTED == 1) {
            Intent i;
            if(USER_CONNECTED.isAdmin() == 1)
                i = new Intent(getApplicationContext(), AdminHomeActivity.class);
            else
                i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
            finish();
        }

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnValidate = (Button) findViewById(R.id.btnValidate);

        if(getIntent().getStringExtra("email") != null)
            txtEmail.setText(getIntent().getStringExtra("email"));

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if(personDB.isEmailExisting(0, email)) {
                    Person person = personDB.getPerson(email);
                    if(person.getPassword().equals(password)) {
                        Intent i;
                        if (person.isAdmin() == 1)
                            //The user is administrator
                            i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                        else
                            i = new Intent(getApplicationContext(), SearchActivity.class);
                        finish();
                        startActivity(i);
                        IS_CONNECTED = 1;
                        USER_CONNECTED = person;
                    }
                    else
                        Toast.makeText(getApplicationContext(), R.string.errorPassMail, Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),  R.string.errorPassMail, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
