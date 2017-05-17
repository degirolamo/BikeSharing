package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pedro.myapplication.backend.personApi.PersonApi;
import com.example.pedro.myapplication.backend.placeApi.PlaceApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by pedro on 17.05.2017.
 */

public class PlaceDeleteAsyncTask extends AsyncTask<Void, Void, Integer> {
    private static PlaceApi placeApi = null;
    private static final String TAG = PlaceDeleteAsyncTask.class.getName();
    private int id;

    public PlaceDeleteAsyncTask(int id)
    {
        this.id = id;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        if(placeApi == null)
        {
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

        try{
            //DELETE IN CLOUD
            if(id != 0)
            {
                //DELETE IN CLOUD
                placeApi.remove((long) id).execute();
            }

            return id;
        }catch (IOException e){
            Log.e(TAG, e.toString());
            return 0;
        }
    }
}