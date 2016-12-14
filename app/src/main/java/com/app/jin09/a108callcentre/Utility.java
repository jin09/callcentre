package com.app.jin09.a108callcentre;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gautam on 19-11-2016.
 */

public class Utility {


    static class DistanceData {
        public String time;
        public Integer seconds;

        public DistanceData(String time, Integer seconds){
            this.time = time;
            this.seconds = seconds;
        }
    }

    public static DistanceData DistanceObj;

    public static String getPhoneNumberFromString(String s){
        try {
            JSONObject placeDetailObject =  new JSONObject(s);
            JSONObject result = placeDetailObject.getJSONObject("result");
            String phone_number = result.getString("international_phone_number");
            return phone_number;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getShortestTimeFromString(String s){
        try {
            JSONObject mapsDataObject = new JSONObject(s);
            JSONObject row1 = mapsDataObject.getJSONArray("rows").getJSONObject(0);
            JSONObject elements = row1.getJSONArray("elements").getJSONObject(0);
            JSONObject duration = elements.getJSONObject("duration");
            String time = duration.getString("text");
            return time;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DistanceData getDistanceObjectFromString(String s){
        try {
            JSONObject mapsDataObject = new JSONObject(s);
            JSONObject row1 = mapsDataObject.getJSONArray("rows").getJSONObject(0);
            JSONObject elements = row1.getJSONArray("elements").getJSONObject(0);
            JSONObject duration = elements.getJSONObject("duration");
            String time = duration.getString("text");
            Integer seconds = duration.getInt("value");
            DistanceObj = new DistanceData(time, seconds);
            return DistanceObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
