package com.app.jin09.a108callcentre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by gautam on 17-11-2016.
 */

public class BackendDataBean {
    ArrayList<Requests> requests;

    public ArrayList<Requests> getallrequests() {
        return requests;
    }

    public BackendDataBean(String json){
        requests = new ArrayList<Requests>();
        try {
            JSONObject jrequests =  new JSONObject(json);
            JSONArray jrequestarray = jrequests.getJSONArray("list");
            for(int i=0;i<jrequestarray.length();i++){
                requests.add(new Requests(jrequestarray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class Requests{
        String injured;
        String phone;
        String name;
        String smsnumber;
        String latitude;
        String pending;
        String type;
        String longitude;
        String address;
        public Requests(JSONObject object) {
            try {
                injured = object.getString("injured");
                phone = object.getString("phone");
                name = object.getString("name");
                smsnumber = object.getString("smsnumber");
                latitude = object.getString("latitude");
                longitude = object.getString("longitude");
                pending = object.getString("pending");
                type = object.getString("type");
                address = object.getString("address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
