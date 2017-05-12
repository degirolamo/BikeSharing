package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.RentDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.cantonApi.CantonApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.pedro.myapplication.backend.cantonApi.model.Canton;
import com.google.api.client.util.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.settingsActivity;

/**
 * Created by pedro on 10.05.2017.
 */

public class CantonAsyncTask extends AsyncTask<Void, Void, List<Canton>> {
    private static CantonApi cantonApi = null;
    private static final String TAG = CantonAsyncTask.class.getName();
    private Canton canton;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public CantonAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public CantonAsyncTask(Canton canton, DatabaseHelper db, SettingsActivity settingsActivity) {
        this.canton = canton;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Canton> doInBackground(Void... params) {

        if (cantonApi == null) {
            // Only do this once
            CantonApi.Builder builder = new CantonApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            cantonApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (canton != null) {
                cantonApi.insert(canton).execute();
                Log.i(TAG, "insert canton");
            }
            // and for instance return the list of all employees
            return cantonApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Canton> result) {

        if (result != null) {
            CantonDB cantonDB = new CantonDB(db);
            cantonDB.cloudToSqlCanton(result);
        }

        if(mainActivity != null) {
            mainActivity.cantonOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.cantonOK = true;
            settingsActivity.check();
        }
    }
}