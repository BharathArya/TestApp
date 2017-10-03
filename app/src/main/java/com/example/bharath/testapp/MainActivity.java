package com.example.bharath.testapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RequestQueue rq;

    TextView fajrText, dhuhrText, asrText, maghribText, ishaText;

    String loc, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        final GetMyLocation getMyLocation = new GetMyLocation();

        try {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
                loc = getMyLocation.showMyAddress(location, getApplicationContext());
                locationTextView.setText(loc);
            }

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
                   loc = getMyLocation.showMyAddress(location, getApplicationContext());
                    locationTextView.setText(loc);
                }

                public void onProviderDisabled(String arg0) {
                    // TODO Auto-generated method stub

                }

                public void onProviderEnabled(String arg0) {
                    // TODO Auto-generated method stub

                }

                public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                    // TODO Auto-generated method stub

                }
            };

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        fajrText = (TextView) findViewById(R.id.fajrStartTime);
        dhuhrText = (TextView) findViewById(R.id.dhuhrStartTime);
        asrText = (TextView) findViewById(R.id.asrStartTime);
        maghribText = (TextView) findViewById(R.id.maghribStartTime);
        ishaText = (TextView) findViewById(R.id.ishaStartTime);

        url = "http://muslimsalat.com/"+loc.toLowerCase()+"/daily.json?key=dd6bc23f9b9671d19711e79cb573302e";

        PrayerTimings prayerTimings = new PrayerTimings();
        prayerTimings.sendjsonrequest(url, rq, fajrText,dhuhrText,asrText,maghribText,ishaText,getApplicationContext());

    }

}