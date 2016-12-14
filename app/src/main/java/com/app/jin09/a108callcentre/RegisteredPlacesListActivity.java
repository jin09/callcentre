package com.app.jin09.a108callcentre;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by gautam on 20-11-2016.
 */

public class RegisteredPlacesListActivity extends AppCompatActivity {

    private static final String TAG = RegisteredPlacesListActivity.class.getSimpleName();
    public RegisteredPlacesBean registeredPlacesBean;
    public String user_lat;
    public String user_lng;
    public String user_type;
    public String user_name;
    public String user_phone;
    public String user_sms;
    public String user_injured;
    public String user_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_places_list_activity);
        Log.d(TAG, "indide OnCreate()...");
        user_lat = getIntent().getStringExtra("user_lat");
        user_lng = getIntent().getStringExtra("user_lng");
        user_type = getIntent().getStringExtra("user_type");
        user_name = getIntent().getStringExtra("user_name");
        user_phone = getIntent().getStringExtra("user_phone");
        user_sms = getIntent().getStringExtra("user_sms");
        user_injured = getIntent().getStringExtra("user_injured");
        user_addr = getIntent().getStringExtra("user_addr");
        new getRegisteredData().execute(user_lat,user_lng);
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

    class getRegisteredData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String s = MakeRequest.getRegisteredData(params[0], params[1]);
            try {
                Log.d(TAG, s);
            }catch (RuntimeException e){
                Log.d(TAG, e.getMessage());
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            registeredPlacesBean = new RegisteredPlacesBean(s);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.registered_places_activity_linear_layout);
            if(fragment == null){
                fragment = new RegisteredPlacesListFragment();
                findViewById(R.id.registered_places_activity_loadingJSON).setVisibility(View.GONE);
                fm.beginTransaction()
                        .add(R.id.registered_places_activity_linear_layout,fragment)
                        .commit();
            }
        }
    }
}
