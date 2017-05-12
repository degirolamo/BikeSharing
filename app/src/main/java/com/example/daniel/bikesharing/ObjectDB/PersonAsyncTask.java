package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by pedro on 10.05.2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.personApi.model.Person;
import com.example.pedro.myapplication.backend.personApi.PersonApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.settingsActivity;
import static com.example.daniel.bikesharing.R.menu.settings;

public class PersonAsyncTask extends AsyncTask<Void, Void, List<Person>> {
    private static PersonApi personApi = null;
    private static final String TAG = PersonAsyncTask.class.getName();
    private Person person;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public PersonAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public PersonAsyncTask(Person person, DatabaseHelper db, SettingsActivity settingsActivity) {
        this.person = person;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Person> doInBackground(Void... params) {

        if (personApi == null) {
            PersonApi.Builder builder = new PersonApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://bikesharing-167113.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            personApi = builder.build();
        }

        try {
            if (person != null) {
                personApi.insert(person).execute();
                Log.i(TAG, "insert person");
            }
            return personApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Person>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Person> result) {

        if (result != null) {
            PersonDB personDB = new PersonDB(db);
            personDB.cloudToSqlPerson(result);
        }

        if(mainActivity != null) {
            mainActivity.personOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.personOK = true;
            settingsActivity.check();
        }
    }
}
