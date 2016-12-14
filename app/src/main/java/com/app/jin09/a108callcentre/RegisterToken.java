package com.app.jin09.a108callcentre;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by gautam on 20-11-2016.
 */

public class RegisterToken extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token",params[0])
                .build();

        Request request = new Request.Builder()
                .url("https://backend-108.appspot.com/register")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("token request success", params[0]);
        return null;
    }
}
