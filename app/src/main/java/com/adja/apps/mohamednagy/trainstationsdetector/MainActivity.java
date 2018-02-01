package com.adja.apps.mohamednagy.trainstationsdetector;


import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GeofenceUtility mGeofenceUtility;
    private List<Geofence> mGeofenceList;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGeofenceUtility = new GeofenceUtility(this);
        mGeofenceList = new ArrayList<>();

        mGeofenceList.add(mGeofenceUtility.create(
                GeofenceArea.FAISL_STATION.getLatitude(),
                GeofenceArea.FAISL_STATION.getLongitude(),
                GeofenceArea.FAISL_STATION.getName()));

        mGeofenceList.add(mGeofenceUtility.create(
                GeofenceArea.CAIRO_UNIVERSITY_STATION.getLatitude(),
                GeofenceArea.CAIRO_UNIVERSITY_STATION.getLongitude(),
                GeofenceArea.CAIRO_UNIVERSITY_STATION.getName()));

        mGeofenceUtility.addingSupport(mGeofenceList);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionHandle.REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    assert mGeofenceList != null;
                    assert mGeofenceUtility != null;

                    mGeofenceUtility.addingSupport(mGeofenceList);
                }
        }
    }

}
