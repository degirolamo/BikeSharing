package com.example.daniel.bikesharing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;

import static com.example.daniel.bikesharing.R.id.btnLogin;

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

        db = new DatabaseHelper(getApplicationContext());
        personDB = new PersonDB(db);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnValidate = (Button) findViewById(R.id.btnValidate);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if(personDB.getPassword(email).equals(password)) {
                    if (personDB.isAdmin(email) == 1) {
                        //The user is administrator
                        Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(i);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Email et/ou mot de passe incorrect(s) !", Toast.LENGTH_LONG).show();
            }
        });
    }
}
