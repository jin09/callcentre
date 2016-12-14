package com.app.jin09.a108callcentre;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public BackendDataBean backendDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.SEND_SMS
            }, 10);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        new loadjson().execute();
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

    private void registerToken(String token) {
        new RegisterToken().execute(token);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                    System.exit(0);
                }
                return;
        }
    }

    class loadjson extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String jsonString = MakeRequest.getJsonFromUrl("http://backend-108.appspot.com/getjson");
            Log.d(TAG,jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG,s);
            backendDataBean = new BackendDataBean(s);
            findViewById(R.id.loadingJSON).setVisibility(View.GONE);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.activity_main);
            if(fragment == null){
                fragment = new PendingListFragment();
                fm.beginTransaction()
                        .add(R.id.activity_main,fragment)
                        .commit();
            }
        }
    }
}
