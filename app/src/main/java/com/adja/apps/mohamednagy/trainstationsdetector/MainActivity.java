package com.adja.apps.mohamednagy.trainstationsdetector;


import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adja.apps.mohamednagy.trainstationsdetector.data.DataConnector;
import com.adja.apps.mohamednagy.trainstationsdetector.data.GeofenceStations;
import com.adja.apps.mohamednagy.trainstationsdetector.geofence_sys.GeofenceUtility;
import com.adja.apps.mohamednagy.trainstationsdetector.google_service.GoogleClientCallback;
import com.adja.apps.mohamednagy.trainstationsdetector.google_service.GoogleClientService;
import com.adja.apps.mohamednagy.trainstationsdetector.location_service.LocationController;
import com.adja.apps.mohamednagy.trainstationsdetector.network.SendDataLoader;
import com.adja.apps.mohamednagy.trainstationsdetector.network.SendDataLoaderHandler;
import com.adja.apps.mohamednagy.trainstationsdetector.permissions.PermissionHandle;
import com.adja.apps.mohamednagy.trainstationsdetector.properties.Subway;
import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements GoogleClientCallback{

    private GeofenceUtility mGeofenceUtility;
    private List<Geofence> mGeofenceList;
    private DataConnector mDataConnector;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SendDataLoaderHandler SEND_DATA_LOADER_HANDLER =
                new SendDataLoaderHandler(getApplicationContext());

        final Subway subway = new Subway() {
            @Override
            public void onMove(Subway newSubwayData) {
                Toast.makeText(getBaseContext(), "Leave current station", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStation(Subway newSubwayData) {
                Toast.makeText(getBaseContext(), "arrive to next station duration is " +(int) (newSubwayData.getPreviousDuration()/1000) + " Sec.", Toast.LENGTH_LONG).show();
                long durationData = newSubwayData.getPreviousDuration();
                if(durationData != Subway.NO_DATA)
                    sendDataToR(SEND_DATA_LOADER_HANDLER, newSubwayData.getPreviousDuration());
            }
        };

        mGeofenceUtility = new GeofenceUtility(this);
        mGeofenceList = new ArrayList<>();
        mDataConnector = new DataConnector(subway);

    }

    private void sendDataToR(SendDataLoaderHandler sendDataLoaderHandler, long timeInMills){
        Bundle bundle = new Bundle();

        bundle.putLong(
                SendDataLoaderHandler.DATA_SEND_ID,
                timeInMills);

        getSupportLoaderManager().initLoader(
                SendDataLoader.LOADER_ID, bundle,
                sendDataLoaderHandler);
    }

    private void defineGeofencePlaces(){
        mGeofenceList.add(mGeofenceUtility.create(GeofenceStations.FAISL_STATION_ID));
        mGeofenceList.add(mGeofenceUtility.create(GeofenceStations.CAIRO_UNIVERSITY_STATION_ID));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionHandle.REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    assert mGeofenceList != null;
                    assert mGeofenceUtility != null;

                    mGeofenceUtility.addingSupport(
                            mGeofenceList,
                            mDataConnector.getEnterStationHandler(),
                            mDataConnector.getExitStationHandler());
                }
        }
    }

    @Override
    protected void onStop(){
        mGeofenceUtility.removeGeofence(mDataConnector.getEnterStationHandler(), mDataConnector.getExitStationHandler());
        assert mGeofenceList != null;
        mGeofenceList.clear();
        GoogleClientService.getInstance().googleApiClientDisconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleClientService.getInstance().build(this, this).googleApiClientConnect();
    }

    @Override
    public void onConnected() {
        defineGeofencePlaces();

        boolean result = mGeofenceUtility.addingSupport(
                mGeofenceList,
                mDataConnector.getEnterStationHandler(),
                mDataConnector.getExitStationHandler()
        );
        new LocationController().execute(this);
        if (result)
            PermissionHandle.askPermission(this, PermissionHandle.ACCESS_FINE_LOCATION_PERMISSION,
                    PermissionHandle.ACCESS_COARSE_LOCATION_PERMISSION);
    }

}
