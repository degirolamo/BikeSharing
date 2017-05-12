package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.CantonDB;
import com.example.daniel.bikesharing.ActivityDB.TownDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.townApi.model.Town;
import com.example.pedro.myapplication.backend.townApi.TownApi;
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

public class TownAsyncTask extends AsyncTask<Void, Void, List<Town>> {
    private static TownApi townApi = null;
    private static final String TAG = TownAsyncTask.class.getName();
    private Town town;
    private DatabaseHelper db;
    private SettingsActivity settingsActivity = null;
    private MainActivity mainActivity = null;

    public TownAsyncTask(DatabaseHelper db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    public TownAsyncTask(Town town, DatabaseHelper db, SettingsActivity settingsActivity) {
        this.town = town;
        this.db = db;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected List<Town> doInBackground(Void... params) {
        if (townApi == null) {
            TownApi.Builder builder = new TownApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            townApi = builder.build();
        }

        try {
            if (town != null) {
                townApi.insert(town).execute();
                Log.i(TAG, "insert town");
            }
            return townApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Town>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Town> result) {

        if (result != null) {
            TownDB townDB = new TownDB(db);
            townDB.cloudToSqlTown(result);
        }

        if(mainActivity != null) {
            mainActivity.townOK = true;
            mainActivity.check();
        }

        if(settingsActivity != null) {
            settingsActivity.townOK = true;
            settingsActivity.check();
        }
    }
}
