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
import static com.example.daniel.bikesharing.R.id.txtEmail;

public class RegisterActivity extends AppCompatActivity {

    PersonDB personDB;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPasswordConfirm;
    EditText txtFirstname;
    EditText txtLastname;
    Spinner spinCanton;

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

        spinCanton = (Spinner) findViewById(R.id.spinRegisterCanton);
        spinCanton.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, cantonsNames));

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
                    if(!personDB.isEmailExisting(0, txtEmail.getText().toString())) {
                        if (txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())) {
                            personDB.insertPerson(spinCanton.getSelectedItemPosition() + 1, txtEmail.getText().toString(),
                                    txtPassword.getText().toString(), txtFirstname.getText().toString(), txtLastname.getText().toString(), 0);

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            i.putExtra("email", txtEmail.getText().toString());
                            startActivity(i);
                        } else
                            Toast.makeText(getApplicationContext(), R.string.errorConfirmPass, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), R.string.errorAccountExisting, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.errorProfilEmptyFields, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
