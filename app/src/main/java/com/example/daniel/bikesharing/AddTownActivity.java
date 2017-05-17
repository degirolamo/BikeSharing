package com.example.daniel.bikesharing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;

import static com.example.daniel.bikesharing.R.id.txtName;

/**
 * Created by pedro on 12.05.2017.
 */

public class AddTownActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtNpa;
    private int idCanton;
    private TownDB townDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolAddTown);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.addTown);
        setSupportActionBar(toolbar);

        txtName = (EditText) findViewById(R.id.txtAddTownName);
        txtNpa = (EditText) findViewById(R.id.txtAddTownNpa);
        idCanton = getIntent().getIntExtra("idCanton", 0);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        townDB = new TownDB(db);

        Button btnValidate = (Button) findViewById(R.id.btnAddPlaceValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtName.getText().toString().equals("") && !txtNpa.getText().toString().equals("")) {
                    if (!townDB.isExistingTown(0, txtName.getText().toString(), Integer.parseInt(txtNpa.getText().toString()))) {
                        townDB.insertTown(idCanton, txtName.getText().toString(), Integer.parseInt(txtNpa.getText().toString()));
                        finish();
                        overridePendingTransition(0, 0);
                        Intent i = new Intent(getApplicationContext(), TownActivity.class);
                        i.putExtra("idCanton", idCanton);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    } else
                        Toast.makeText(getApplicationContext(), R.string.errorTownExisting, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.errorProfilEmptyFields, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
