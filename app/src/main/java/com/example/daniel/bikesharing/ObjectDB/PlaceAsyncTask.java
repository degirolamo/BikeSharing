package com.example.daniel.bikesharing.ObjectDB;

/**
 * Created by pedro on 10.05.2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.PlaceDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.placeApi.model.Place;
import com.example.pedro.myapplication.backend.placeApi.PlaceApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.settingsActivity;

public class PlaceAsyncTask extends AsyncTask<Void, Void, List<Place>> {
    private static PlaceApi placeApi = null;
    private static final String TAG = PlaceAsyncTask.class.getName();
    private Place place;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public PlaceAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public PlaceAsyncTask(Place place, DatabaseHelper db, SettingsActivity settingsActivity) {
        this.place = place;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Place> doInBackground(Void... params) {

        if (placeApi == null) {
            PlaceApi.Builder builder = new PlaceApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            placeApi = builder.build();
        }

        try {
            if (place != null) {
                placeApi.insert(place).execute();
                Log.i(TAG, "insert place");
            }
            return placeApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Place> result) {

        if (result != null) {
            PlaceDB placeDB = new PlaceDB(db);
            placeDB.cloudToSqlPlace(result);
        }

        if(mainActivity != null) {
            mainActivity.placeOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.placeOK = true;
            settingsActivity.check();
        }
    }
}
