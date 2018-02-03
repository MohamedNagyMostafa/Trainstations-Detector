package com.adja.apps.mohamednagy.trainstationsdetector.location_service;

import android.content.Context;

import com.adja.apps.mohamednagy.trainstationsdetector.permissions.PermissionHandle;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Mohamed Nagy on 2/2/2018.
 */

public class LocationController extends LocationCallback {

    private LocationRequest mLocationRequest;
    private static final int UPDATE_INTERVAL = 30000;
    private static final int FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    public LocationController(){
        if(mLocationRequest == null)
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setFastestInterval(FASTEST_INTERVAL)
                    .setInterval(UPDATE_INTERVAL);

    }

    public void execute(Context context){
        if(PermissionHandle.checkPermission(PermissionHandle.ACCESS_COARSE_LOCATION_PERMISSION, context) &&
                PermissionHandle.checkPermission(PermissionHandle.ACCESS_FINE_LOCATION_PERMISSION, context)){
            LocationServices.getFusedLocationProviderClient(context)
                    .requestLocationUpdates(mLocationRequest, this, null);
        }
    }
}
