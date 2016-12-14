package com.app.jin09.a108callcentre;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class AllDetailActivity extends AppCompatActivity {
    private static final String TAG = AllDetailActivity.class.getSimpleName();
    private String placeDetailJson;
    private String shortestDistanceJson;
    private String placePhoneNumber = "";
    private String shortestTime;
    private TextView type;
    private TextView name;
    private Button phone;
    private Button sms;
    private TextView lat;
    private TextView lng;
    private TextView address;
    private TextView name_place;
    private TextView phone_place;
    private TextView address_place;
    private TextView shortest_time;
    private Button injured;
    private Button user_mssg;
    private Button place_call;
    private Button place_mssg;
    public String user_type;
    public String user_name;
    public String user_phone;
    public String user_sms_number;
    public String user_injured;
    public String user_lat;
    public String user_lng;
    public String user_address;
    public String place_name;
    public String place_address;
    public String place_ID;
    public String place_lat;
    public String place_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_detail);
        Log.d(TAG, "Oncreate()");
        user_type = getIntent().getStringExtra("user_type");
        user_name = getIntent().getStringExtra("user_name");
        user_phone = getIntent().getStringExtra("user_phone");
        user_sms_number = getIntent().getStringExtra("user_sms_number");
        user_injured = getIntent().getStringExtra("user_injured");
        user_lat = getIntent().getStringExtra("user_lat");
        user_lng = getIntent().getStringExtra("user_lng");
        user_address = getIntent().getStringExtra("address");
        place_name = getIntent().getStringExtra("place_name");
        place_address = getIntent().getStringExtra("place_address");
        place_ID = getIntent().getStringExtra("place_id");
        place_lat = getIntent().getStringExtra("place_lat");
        place_lng = getIntent().getStringExtra("place_lng");
        type = (TextView) findViewById(R.id.type1);
        name = (TextView) findViewById(R.id.name1);
        phone = (Button) findViewById(R.id.phone1);
        sms = (Button) findViewById(R.id.sms1);
        lat = (TextView) findViewById(R.id.lat1);
        lng = (TextView) findViewById(R.id.lng1);
        address = (TextView) findViewById(R.id.address1);
        name_place = (TextView) findViewById(R.id.name_place1);
        phone_place = (TextView) findViewById(R.id.phone_place1);
        address_place = (TextView) findViewById(R.id.address_place1);
        shortest_time = (TextView) findViewById(R.id.shortest_time1);
        injured = (Button) findViewById(R.id.injured1);
        user_mssg = (Button) findViewById(R.id.sms_user1);
        place_call = (Button) findViewById(R.id.call_place1);
        place_mssg = (Button) findViewById(R.id.sms_place1);
        if(!place_ID.equals("")) {
            try {
                placeDetailJson = new fetchPlaceData().execute(place_ID).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            shortestDistanceJson = new fetchDistanceData().execute(user_lat, user_lng, place_lat, place_lng).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(!place_ID.equals("")) {
            placePhoneNumber = Utility.getPhoneNumberFromString(placeDetailJson);
        }
        shortestTime = Utility.getShortestTimeFromString(shortestDistanceJson);
        findViewById(R.id.progressBar1).setVisibility(View.GONE);


        type.setText(user_type);
        name.setText(user_name);
        phone.setText(user_phone);
        sms.setText(user_sms_number);
        lat.setText(user_lat);
        lng.setText(user_lng);
        address.setText(user_address);
        name_place.setText(place_name);
        phone_place.setText(placePhoneNumber);
        address_place.setText(place_address);
        shortest_time.setText(shortestTime);
        injured.setText(user_injured);

        if (user_injured.equals("0")) {
            injured.setEnabled(false);
        }

        if(user_type.equals("MEDICAL EMERGENCY")){
            injured.setEnabled(false);
        }

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user_phone));
                startActivity(intent);
            }
        });

        if(sms.getText().toString().equals("[NO]--[DIRECT]")){
            sms.setEnabled(false);
        }
        else {
            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user_sms_number));
                    startActivity(intent);
                }
            });
        }
        if(!placePhoneNumber.equals("")) {
            place_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + placePhoneNumber));
                    startActivity(intent);
                }
            });
        }
        else {
            place_call.setEnabled(false);
        }

        user_mssg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent sentPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED"), 0);

                // For when the SMS has been sent
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_RADIO_OFF:
                                Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_SENT"));

                // For when the SMS has been delivered
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                                break;
                            case Activity.RESULT_CANCELED:
                                Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_DELIVERED"));
               // Intent intent = new Intent();
            //    intent.setAction(Intent.ACTION_SEND);
              //  intent.setType("text/plain");
               // intent.putExtra(Intent.EXTRA_TEXT,
                String sms = "Type of Service: " + user_type +
                        "\n" + "Service place: " + place_name + "\n" + "Address: " + place_address
                        + "\n" + "Phone: " + placePhoneNumber + "\n" +
                        "Estimated Time: " + shortestTime;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(user_phone,null, sms, sentPendingIntent, deliveredPendingIntent);
               // startActivity(intent);
            }
        });

        place_mssg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Name: "+user_name+"\n"+"Phone: "+user_phone
                +"\n"+ "Sms Number: " + user_sms_number + "\n" + "Address: " + user_address+"\n"
                + "Exact location: "+"http://maps.google.com/maps?q=loc:"+user_lat+","+user_lng+'\n'
                + "Estimated Time: " + shortestTime + "\nInjuries: " + user_injured);
                startActivity(intent);
            }
        });

        if(injured.isEnabled()){
            injured.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("user_type","MEDICAL EMERGENCY");
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("user_phone", user_phone);
                    intent.putExtra("user_sms_number", user_sms_number);
                    intent.putExtra("user_injured", user_injured);
                    intent.putExtra("user_lat",user_lat);
                    intent.putExtra("user_lng", user_lng);
                    intent.putExtra("address", user_address);
                    intent.putExtra("type","MEDICAL EMERGENCY");
                    intent.putExtra("lat",user_lat);
                    intent.putExtra("longi",user_lng);
                    startActivity(intent);
                }
            });
        }
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

    class fetchPlaceData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String jsonstring = MakeRequest.getstringFromPlaceId(params[0]);
            Log.d(TAG,jsonstring);
            return jsonstring;
        }
    }

    class fetchDistanceData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String jsonString = MakeRequest.getDistanceData(params[0],params[1],params[2],params[3]);
            Log.d(TAG,jsonString);
            return jsonString;
        }
    }
}
