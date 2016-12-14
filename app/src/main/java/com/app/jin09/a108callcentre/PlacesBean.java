package com.app.jin09.a108callcentre;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by gautam on 18-11-2016.
 */

public class PlacesBean {
    ArrayList<Results> results;
    private static final String TAG = PlacesBean.class.getSimpleName();
    public String user_lat;
    public  String user_lng;


    public ArrayList<Results> getallresults() {
        return results;
    }

    public PlacesBean(String json, String user_lat, String user_lng){
        this.user_lat = user_lat;
        this.user_lng = user_lng;
        results = new ArrayList<Results>();
        try{
            JSONObject jrequests =  new JSONObject(json);
            JSONArray jrequestarray = jrequests.getJSONArray("results");
            for(int i=0;i<jrequestarray.length();i++){
                results.add(new Results(jrequestarray.getJSONObject(i), user_lat, user_lng));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class Results{
        String lat;
        String lon;
        String iconlink;
        String id;
        String name;
        String placeID;
        String reference;
        String address;
        String shortestDistance;
        Integer seconds;
        Utility.DistanceData distanceDataobj;

        public Results(JSONObject object, String user_lat, String user_lng){
            try {
                lat = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
                lon = object.getJSONObject("geometry").getJSONObject("location").getString("lng");
                iconlink = object.getString("icon");
                id = object.getString("id");
                name = object.getString("name");
                placeID = object.getString("place_id");
                reference = object.getString("reference");
                address = object.getString("vicinity");
                String distanceString = new fetchDistanceData().execute(user_lat, user_lng, lat, lon).get();
                distanceDataobj = Utility.getDistanceObjectFromString(distanceString);
                shortestDistance = distanceDataobj.time;
                seconds = distanceDataobj.seconds;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class fetchDistanceData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonString = MakeRequest.getDistanceData(params[0],params[1],params[2],params[3]);
            Log.d(TAG,jsonString);
            return jsonString;
        }
    }
}
