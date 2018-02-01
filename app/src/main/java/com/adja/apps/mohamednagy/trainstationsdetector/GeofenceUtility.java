package com.adja.apps.mohamednagy.trainstationsdetector;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Created by Mohamed Nagy on 1/31/2018.
 */

public class GeofenceUtility {

    private static final float DETECTION_RADIUS = 70f;
    private GeofencingClient mGeofencingClient;
    private PendingIntent mGeoPendingIntent;
    private final Context mContext;
    private final String TAG;

    public GeofenceUtility(Context context) {
        mContext = context;
        TAG = mContext.getPackageName();
        mGeofencingClient = new GeofencingClient(mContext);
    }

    public Geofence create(long latitude, long longitude, String id) {
        return new Geofence.Builder()
                .setRequestId(id)
                .setCircularRegion(latitude, longitude, DETECTION_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private GeofencingRequest getRequest(List<Geofence> geofenceList) {
        return new GeofencingRequest.Builder()
                .addGeofences(geofenceList)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }

    private PendingIntent getGeofencePendingIntent() {

        if (mGeoPendingIntent != null) {
            return mGeoPendingIntent;
        }

        Intent geofenceIntent = new Intent(mContext, GeofenceIntentService.class);

        mGeoPendingIntent = PendingIntent.getService(
                mContext,
                GeofenceIntentService.SERVICE_ID,
                geofenceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return mGeoPendingIntent;
    }

    public boolean addingSupport(List<Geofence> geofenceList) {
        // Check for android 6.0 and upper.
        if(PermissionHandle.checkPermission(PermissionHandle.ACCESS_FINE_LOCATION_PERMISSION, mContext)) {
            mGeofencingClient.addGeofences(getRequest(geofenceList), getGeofencePendingIntent())
                    .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "add geo succ");
                        }
                    }).addOnFailureListener((Activity) mContext, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            });

            return true;
        }
        // Do request.
        return false;
    }

    public void removeGeofence(List<Geofence> geofenceList){
        mGeofencingClient.removeGeofences(getGeofencePendingIntent())
                .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "remove geo succ");
                    }
                })
                .addOnFailureListener((Activity) mContext, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "remove geo fail");
                    }
                });
    }


}
