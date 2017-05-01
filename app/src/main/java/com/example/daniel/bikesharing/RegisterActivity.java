package com.example.daniel.bikesharing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.ObjectDB.Canton;
import com.example.daniel.bikesharing.ObjectDB.Person;

import java.util.ArrayList;
import java.util.List;

import static com.example.daniel.bikesharing.R.id.btnValidate;

public class RegisterActivity extends AppCompatActivity {

    PersonDB personDB;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPasswordConfirm;
    EditText txtFirstname;
    EditText txtLastname;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolRegister);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.s_enregistrer);
        setSupportActionBar(toolbar);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        CantonDB cantonDB = new CantonDB(db);
        personDB = new PersonDB(db);
        List<Canton> cantons = cantonDB.getCantons();
        List<String> cantonsNames = new ArrayList<>();
        for (Canton canton : cantons) {
            cantonsNames.add(canton.getName());
        }

        spinner = (Spinner) findViewById(R.id.spinRegisterTown);
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, cantonsNames));

        txtEmail = (EditText) findViewById(R.id.txtRegisterEmail);
        txtPassword = (EditText) findViewById(R.id.txtRegisterPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtRegisterConfPassword);
        txtFirstname = (EditText) findViewById(R.id.txtRegisterFirstname);
        txtLastname = (EditText) findViewById(R.id.txtRegisterLastname);

        Button btnValidate = (Button) findViewById(R.id.btnRegisterValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtLastname.getText().toString().equals("") && !txtFirstname.getText().toString().equals("") &&
                        !txtEmail.getText().toString().equals("") && !txtPassword.getText().toString().equals("")) {
                    if(!personDB.isEmailExisting(txtEmail.getText().toString())) {
                        if (txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())) {
                            personDB.insertPerson(spinner.getSelectedItemPosition(), txtEmail.getText().toString(),
                                    txtPassword.getText().toString(), txtFirstname.getText().toString(), txtLastname.getText().toString(), 0);

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        } else
                            Toast.makeText(getApplicationContext(), "La confirmation de mot de passe ne correspond pas.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Compte déjà existant", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Certains champs sont vides.", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
