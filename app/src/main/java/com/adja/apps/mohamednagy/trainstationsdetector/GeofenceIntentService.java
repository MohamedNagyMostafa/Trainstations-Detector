package com.adja.apps.mohamednagy.trainstationsdetector;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by Mohamed Nagy on 2/1/2018.
 */

public class GeofenceIntentService extends IntentService {
    private static final String GEOFENCE_SERVICE = "geofence service";
    public static final int SERVICE_ID = 0x1;

    public GeofenceIntentService() {
        super(GEOFENCE_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        assert geofencingEvent != null;

        if(geofencingEvent.hasError()){
            Log.e(GEOFENCE_SERVICE, GEOFENCE_SERVICE +  geofencingEvent.getErrorCode());

            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        Geofence geofenceTrigger = geofencingEvent.getTriggeringGeofences().get(0);

        switch (geofenceTransition){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                    Log.e(GEOFENCE_SERVICE + " Enter", geofenceTrigger.getRequestId());
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Log.e(GEOFENCE_SERVICE + " Exit", geofenceTrigger.getRequestId());
                break;
            default:
                Log.e(GEOFENCE_SERVICE, "Invalid type");
        }
    }
}
