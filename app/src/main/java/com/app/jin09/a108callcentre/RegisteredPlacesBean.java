package com.app.jin09.a108callcentre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by gautam on 20-11-2016.
 */

public class RegisteredPlacesBean {
    ArrayList<RegisteredPlacesBean.Results> results;
    private static final String TAG = PlacesBean.class.getSimpleName();

    public ArrayList<RegisteredPlacesBean.Results> getallresults() {
        return results;
    }

    public RegisteredPlacesBean(String s){
        results = new ArrayList<Results>();
        try{
            JSONObject jrequests =  new JSONObject(s);
            JSONArray jrequestarray = jrequests.getJSONArray("results");
            for(int i=0;i<jrequestarray.length();i++){
                results.add(new Results(jrequestarray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class Results{
        String lat;
        String lng;
        String name;
        String text;
        String addr;
        Integer value;

        public Results(JSONObject jsonObject) {
            try {
                lat = jsonObject.getString("lat");
                lng = jsonObject.getString("lng");
                name = jsonObject.getString("name");
                text = jsonObject.getString("text");
                value = jsonObject.getInt("value");
                addr = jsonObject.getString("addr");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
