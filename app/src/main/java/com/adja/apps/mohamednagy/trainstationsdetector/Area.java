package com.adja.apps.mohamednagy.trainstationsdetector;

/**
 * Created by Mohamed Nagy on 2/1/2018.
 */

public class Area {
    private double mLatitude;
    private double mLongitude;
    private String mName;

    public Area(double lat, double log, String name){
        mLatitude = lat;
        mLongitude = log;
        mName = name;
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
