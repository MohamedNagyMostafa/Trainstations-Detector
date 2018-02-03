package com.adja.apps.mohamednagy.trainstationsdetector.geofence_sys;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.adja.apps.mohamednagy.trainstationsdetector.DataConnector;
import com.adja.apps.mohamednagy.trainstationsdetector.permissions.PermissionHandle;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Created by Mohamed Nagy on 1/31/2018.
 */

public class GeofenceUtility {

    private static final float DETECTION_RADIUS = 700f;
    private GeofencingClient mGeofencingClient;
    private PendingIntent mGeoPendingIntent;
    private final Context mContext;
    private final String TAG;

    public GeofenceUtility(Context context) {
        mContext = context;
        TAG = mContext.getPackageName();
        mGeofencingClient = LocationServices.getGeofencingClient(mContext);
    }

    public Geofence create(double latitude, double longitude, String id) {
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

    private PendingIntent getGeofencePendingIntent(@Nullable Handler enterStationHandler,
                                                   @Nullable Handler exitStationHandler) {

        if (mGeoPendingIntent != null) {
            return mGeoPendingIntent;
        }

        assert enterStationHandler != null;
        assert exitStationHandler != null;

        Intent geofenceIntent = new Intent(mContext, GeofenceIntentService.class);
        geofenceIntent.putExtra(DataConnector.ENTER_HANDLER_CODE, new Messenger(enterStationHandler));
        geofenceIntent.putExtra(DataConnector.EXIT_HANDLER_CODE, new Messenger(exitStationHandler));

        mGeoPendingIntent = PendingIntent.getService(
                mContext,
                GeofenceIntentService.SERVICE_ID,
                geofenceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return mGeoPendingIntent;
    }

    public boolean addingSupport(final List<Geofence> geofenceList, Handler enterStationHandler, Handler exitStationHandler) {
        // Check for android 6.0 and upper.
        if(PermissionHandle.checkPermission(PermissionHandle.ACCESS_FINE_LOCATION_PERMISSION, mContext) &&
                PermissionHandle.checkPermission(PermissionHandle.ACCESS_COARSE_LOCATION_PERMISSION, mContext)) {
            mGeofencingClient.addGeofences(getRequest(geofenceList), getGeofencePendingIntent(enterStationHandler, exitStationHandler))
                    .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "add geo succ");
                            Log.e("check4","donnnnnnnnnnnnnnnne");
                            Toast.makeText(mContext, " done " + geofenceList.size(), Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener((Activity) mContext, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.toString());
                    Log.e("check3","donnnnnnnnnnnnnnnne");

                }
            });
            Log.e("check1","donnnnnnnnnnnnnnnne");

            return true;
        }
        // Do request.
        return false;
    }

    public void removeGeofence(){
        mGeofencingClient.removeGeofences(getGeofencePendingIntent(null, null))
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
