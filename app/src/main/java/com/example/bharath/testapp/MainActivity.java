package com.example.bharath.testapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.Format;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    RequestQueue rq;

    TextView fajrText, dhuhrText, asrText, maghribText, ishaText;

    String loc, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Setting the view to activity_main

        rq = Volley.newRequestQueue(this); //Creating a new volley request


        final GetMyLocation getMyLocation = new GetMyLocation();

        try {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                loc = getMyLocation.showMyAddress(location, getApplicationContext());
            }

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    loc = getMyLocation.showMyAddress(location, getApplicationContext());
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

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
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

        final TextView res = (TextView)findViewById(R.id.time);

        url = "http://muslimsalat.com/"+loc+"/daily.json?key=dd6bc23f9b9671d19711e79cb573302e";

        PrayerTimings prayerTimings = new PrayerTimings();


        //Toast.makeText(this, res.getText().toString() ,Toast.LENGTH_LONG).show();

        Calendar testDate = Calendar.getInstance();
        Format format = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        String currentTime = format.format(testDate.getTime());

        currentTime = currentTime.substring(0,Math.min(currentTime.length(),4));



        final AudioManager audioManager = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);

        fajrText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        });

        dhuhrText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        });

        asrText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });

        final String finalCurrentTime = currentTime;
        prayerTimings.sendjsonrequest(url, rq, fajrText,dhuhrText,asrText,maghribText,ishaText,getApplicationContext(), new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                res.setText(result.toString());
                if(finalCurrentTime.equals(res.getText().toString())){
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
            }
        });


    }

}