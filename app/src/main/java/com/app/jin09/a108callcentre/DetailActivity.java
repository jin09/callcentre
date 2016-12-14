package com.app.jin09.a108callcentre;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by gautam on 18-11-2016.
 */

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private String type;
    private String lat;
    private String lon;
    private Intent i;
    public PlacesBean placesbean;
    public String user_type;
    public String user_name;
    public String user_phone;
    public String user_sms_number;
    public String user_injured;
    public String user_lat;
    public String user_lng;
    public String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Log.d(TAG, "Inside OnCreate()...");
        i = getIntent();
        type = i.getStringExtra("type");
        lat = i.getStringExtra("lat");
        lon = i.getStringExtra("longi");
        user_type = i.getStringExtra("user_type");
        user_name = i.getStringExtra("user_name");
        user_phone = i.getStringExtra("user_phone");
        user_sms_number = i.getStringExtra("user_sms_number");
        user_injured = i.getStringExtra("user_injured");
        user_lat = i.getStringExtra("user_lat");
        user_lng = i.getStringExtra("user_lng");
        address = i.getStringExtra("address");
        new getData().execute(type,lat,lon);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"Inside onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Inside onPause()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Inside onResume()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"Inside onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside onDestroy()");
    }

    class getData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String jsonString = MakeRequest.getJsonFromType(params[0],params[1],params[2]);
            Log.d(TAG,jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG,s);
            placesbean = new PlacesBean(s, user_lat, user_lng);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.detail_activity_linear_layout);
            if(fragment == null){
                fragment = new PlacesListFragment();
                findViewById(R.id.detail_activity_loadingJSON).setVisibility(View.GONE);
                fm.beginTransaction()
                        .add(R.id.detail_activity_linear_layout,fragment)
                        .commit();
            }
        }
    }
}
