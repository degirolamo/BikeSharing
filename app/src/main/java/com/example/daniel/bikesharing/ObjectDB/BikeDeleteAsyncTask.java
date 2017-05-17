package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.bikesharing.ActivityDB.PersonDB;
import com.example.daniel.bikesharing.DB.DatabaseHelper;
import com.example.daniel.bikesharing.MainActivity;
import com.example.daniel.bikesharing.SettingsActivity;
import com.example.pedro.myapplication.backend.bikeApi.BikeApi;
import com.example.pedro.myapplication.backend.personApi.PersonApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro on 17.05.2017.
 */

public class BikeDeleteAsyncTask extends AsyncTask<Void, Void, Integer> {
    private static BikeApi bikeApi = null;
    private static final String TAG = BikeDeleteAsyncTask.class.getName();
    private int id;

    public BikeDeleteAsyncTask(int id)
    {
        this.id = id;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        if(bikeApi == null)
        {
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
            //DELETE IN CLOUD
            if(id != 0)
            {
                //DELETE IN CLOUD
                bikeApi.remove((long) id).execute();
            }

            return id;
        }catch (IOException e){
            Log.e(TAG, e.toString());
            return 0;
        }
    }
}
