package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.placeApi.PlaceApi;
import com.example.pedro.myapplication.backend.rentApi.RentApi;
import com.example.pedro.myapplication.backend.rentApi.model.Rent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.settingsActivity;

/**
 * Created by pedro on 10.05.2017.
 */

public class RentAsyncTask extends AsyncTask<Void, Void, List<Rent>> {
    private static RentApi rentApi = null;
    private static final String TAG = RentAsyncTask.class.getName();
    private Rent rent;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public RentAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public RentAsyncTask(Rent rent, DatabaseHelper db, SettingsActivity settingsActivity) {
        this.rent = rent;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Rent> doInBackground(Void... params) {
        if (rentApi == null) {
            RentApi.Builder builder = new RentApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            rentApi = builder.build();
        }

        try {
            if (rent != null) {
                rentApi.insert(rent).execute();
                Log.i(TAG, "insert rent");
            }
            return rentApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Rent>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Rent> result) {

        if (result != null) {
            RentDB rentDB = new RentDB(db);
            rentDB.cloudToSqlRent(result);
        }

        if(mainActivity != null) {
            mainActivity.rentOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.rentOK = true;
            settingsActivity.check();
        }
    }
}
