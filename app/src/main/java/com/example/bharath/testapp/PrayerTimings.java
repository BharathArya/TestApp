package com.example.bharath.testapp;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bharath on 03-10-2017.
 */

public class PrayerTimings {

    String fajr, dhuhr, asr, maghrib, isha;

    public void sendjsonrequest(String url, RequestQueue rq, final TextView fajrText, final TextView dhuhrText,
                                final TextView asrText, final TextView maghribText, final TextView ishaText, final Context context)
    {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("items");
                    JSONObject object = array.getJSONObject(0);
                    fajr = object.getString("fajr");
                    dhuhr = object.getString("dhuhr");
                    asr = object.getString("asr");
                    maghrib = object.getString("maghrib");
                    isha = object.getString("isha");

                    fajrText.setText(fajr);
                    dhuhrText.setText(dhuhr);
                    asrText.setText(asr);
                    maghribText.setText(maghrib);
                    ishaText.setText(isha);

                    Log.d("TAG",fajr+dhuhr+asr+maghrib+isha);

                } catch (JSONException e) {
                    Log.d("TAG", e.toString());
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("TAG",error.getMessage());
                        Toast.makeText(context, error.getMessage().toString() ,Toast.LENGTH_LONG).show();
                    }
                });

        rq.add(request);
    }

    public  boolean check(boolean isChecked){
        isChecked = true;
        return isChecked;
    }

}
