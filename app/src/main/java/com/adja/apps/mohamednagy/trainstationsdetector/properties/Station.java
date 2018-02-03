package com.adja.apps.mohamednagy.trainstationsdetector.properties;

/**
 * Created by Mohamed Nagy on 2/1/2018.
 */

public class Station {
    private double mLatitude;
    private double mLongitude;
    private String mName;
    private int mid;

    public Station(double lat, double log, String name, int id){
        mLatitude = lat;
        mLongitude = log;
        mName = name;
        mid = id;
    }

    public String getAsStringid() {
        return String.valueOf(mid);
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getName() {
        return mName;
    }
}
