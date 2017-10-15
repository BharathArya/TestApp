package com.example.bharath.testapp;


import java.util.Date;

public interface ServerCallback{
    void onSuccess(Date fajrTime, Date dhuhrTime, Date asrTime, Date maghribTime, Date ishaTime);
}
