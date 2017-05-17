package com.example.daniel.bikesharing.ObjectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pedro.myapplication.backend.placeApi.PlaceApi;
import com.example.pedro.myapplication.backend.townApi.TownApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by pedro on 17.05.2017.
 */

public class TownDeleteAsyncTask extends AsyncTask<Void, Void, Integer> {
    private static TownApi townApi = null;
    private static final String TAG = TownDeleteAsyncTask.class.getName();
    private int id;

    public TownDeleteAsyncTask(int id)
    {
        this.id = id;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        if(townApi == null)
        {
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

        try{
            //DELETE IN CLOUD
            if(id != 0)
            {
                //DELETE IN CLOUD
                townApi.remove((long) id).execute();
            }

            return id;
        }catch (IOException e){
            Log.e(TAG, e.toString());
            return 0;
        }
    }
}
