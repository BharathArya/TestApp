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

    String url = "http://muslimsalat.com/london/daily.json?key=dd6bc23f9b9671d19711e79cb573302e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        try {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                showMyAddress(location);
            }

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    showMyAddress(location);
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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

            // Also declare a private method



        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        fajrText = (TextView) findViewById(R.id.fajrStartTime);
        dhuhrText = (TextView) findViewById(R.id.dhuhrStartTime);
        asrText = (TextView) findViewById(R.id.asrStartTime);
        maghribText = (TextView) findViewById(R.id.maghribStartTime);
        ishaText = (TextView) findViewById(R.id.ishaStartTime);

        PrayerTimings prayerTimings = new PrayerTimings();
        prayerTimings.sendjsonrequest(url,rq,fajrText,dhuhrText,asrText,maghribText,ishaText,getApplicationContext());

    }

    private void showMyAddress(Location location) {
        Toast.makeText(this, "Accessing showMyAddress function..", Toast.LENGTH_LONG).show();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> myList;
        try {
            myList = myLocation.getFromLocation(latitude, longitude, 1);
            if(myList.size() == 1) {
                //String loc;
                //TextView loactionTextView = (TextView) findViewById(R.id.locationTextView);
                //loc = myList.get(0).getAdminArea();
                //loactionTextView.setText(loc);
                Toast.makeText(this, myList.get(0).toString(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}