package com.app.jin09.a108callcentre;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MakeRequest{
    private static final String TAG = MakeRequest.class.getSimpleName();

    class DistanceData{
        public String time;
        public Integer seconds;
    }

    public static String getJsonFromUrl(String link){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {

            URL url = new URL(link);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
            Log.d("jsonString",jsonString);

            return jsonString;
        }
        catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        }  finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static String getJsonFromType(String type, String lat, String lon) {

        String BASE_URL;

        if(type.equals("MEDICAL EMERGENCY")){
            BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankBy=distance&keyword=ambulance&key=AIzaSyD-Tp_QBh58mlcEmSaeD4ii48X5wHWU7sI&location="+lat+","+lon;
        }
        else if(type.equals("CRIME EMERGENCY")){
            BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankBy=distance&keyword=police&key=AIzaSyD-Tp_QBh58mlcEmSaeD4ii48X5wHWU7sI&location="+lat+","+lon;
        }
        else{
            BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankBy=distance&keyword=fire%20station&key=AIzaSyD-Tp_QBh58mlcEmSaeD4ii48X5wHWU7sI&location="+lat+","+lon;
        }
        Log.d(TAG, BASE_URL);

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {

            URL url = new URL(BASE_URL);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
            Log.d("jsonString",jsonString);

            return jsonString;
        }
        catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        }  finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static String getstringFromPlaceId(String placeID){
        String BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyD-Tp_QBh58mlcEmSaeD4ii48X5wHWU7sI&placeid="+placeID;
        Log.d(TAG, BASE_URL);

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {

            URL url = new URL(BASE_URL);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
            Log.d("jsonString",jsonString);

            return jsonString;
        }
        catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        }  finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static String getDistanceData(String slat,String slng,String dlat, String dlng){
        String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/" +
                "json?units=metric&key=AIzaSyCs_p6e1Od8lpXiEnTa2H9QxkhLOZxmefQ&" +
                "origins="+slat+","+slng+"&destinations="+dlat+","+dlng;
        Log.d(TAG, BASE_URL);

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {

            URL url = new URL(BASE_URL);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
            Log.d("jsonString",jsonString);

            return jsonString;
        }
        catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        }  finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static String getRegisteredData(String lat, String lng){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {
            String link = "http://backend-108.appspot.com/registeredplacescompute?lat="+lat+"&lng="+lng;
            Log.d(TAG, link);
            URL url = new URL(link);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
            Log.d("jsonString",jsonString);

            return jsonString;
        }
        catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        }  finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }
}

