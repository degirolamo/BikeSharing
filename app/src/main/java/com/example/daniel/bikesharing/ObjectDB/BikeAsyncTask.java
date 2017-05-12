package com.example.daniel.bikesharing.ObjectDB;

import com.example.daniel.bikesharing.ActivityDB.BikeDB;
import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.HomeActivity;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.bikeApi.BikeApi;
import com.example.pedro.myapplication.backend.cantonApi.CantonApi;
import com.google.api.client.extensions.android.http.AndroidHttp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.pedro.myapplication.backend.bikeApi.model.Bike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.settingsActivity;

public class BikeAsyncTask extends AsyncTask<Void, Void, List<Bike>> {
    private static BikeApi bikeApi = null;
    private static final String TAG = BikeAsyncTask.class.getName();
    private Bike bike;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public BikeAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public BikeAsyncTask(Bike bike, DatabaseHelper db, SettingsActivity settingsActivity){
        this.bike = bike;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Bike> doInBackground(Void... params) {

        if(bikeApi == null){
            // Only do this once
            BikeApi.Builder builder = new BikeApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            bikeApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(bike != null){
                bikeApi.insert(bike).execute();
                Log.i(TAG, "insert bike");
            }
            // and for instance return the list of all employees
            return bikeApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Bike> result){

        if(result != null) {
            BikeDB bikeDB = new BikeDB(db);
            bikeDB.cloudToSqlBike(result);
        }

        if(mainActivity != null) {
            mainActivity.bikeOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.bikeOK = true;
            settingsActivity.check();
        }
    }
}
