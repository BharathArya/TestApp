package com.example.bharath.testapp;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bharath on 03-10-2017.
 */

public class GetMyLocation extends MainActivity {

    protected String showMyAddress(Location location, Context context) {
        String loc = null;
        //Toast.makeText(this, "Accessing showMyAddress function..", Toast.LENGTH_LONG).show();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder myLocation = new Geocoder(context, Locale.getDefault());
        List<Address> myList;
        try {
            myList = myLocation.getFromLocation(latitude, longitude, 1);
            if(myList.size() == 1) {


                loc = String.valueOf(myList.get(0).getAdminArea());

                //Toast.makeText(this, myList.get(0).toString(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return loc;
    }

}
