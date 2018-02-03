package com.adja.apps.mohamednagy.trainstationsdetector.geofence_sys;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.adja.apps.mohamednagy.trainstationsdetector.DataConnector;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Calendar;

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
        Toast.makeText(this,"error",Toast.LENGTH_LONG).show();

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        assert geofencingEvent != null;

        if(geofencingEvent.hasError()){
            Log.e(GEOFENCE_SERVICE, GEOFENCE_SERVICE +  geofencingEvent.getErrorCode());
            return;
        }
        Log.e("beko","bekoooooooooooo");
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        Geofence geofenceTrigger = geofencingEvent.getTriggeringGeofences().get(0);

        switch (geofenceTransition){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.e(GEOFENCE_SERVICE + " Enter", geofenceTrigger.getRequestId());
                DataConnector.dataHandler(DataConnector.ENTER_HANDLER_CODE, intent);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                DataConnector.dataHandler(DataConnector.EXIT_HANDLER_CODE, intent);
                break;
            default:
                Log.e(GEOFENCE_SERVICE, "Invalid type");
        }
    }


}
