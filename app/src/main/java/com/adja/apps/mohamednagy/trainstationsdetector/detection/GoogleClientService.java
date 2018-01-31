package com.adja.apps.mohamednagy.trainstationsdetector.detection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Mohamed Nagy on 1/31/2018.
 */

public class GoogleClientService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    private static class GoogleClientObject{
        private static final GoogleClientService GOOGLE_CLIENT_SERVICE = new GoogleClientService();
    }

    private GoogleClientService(){
        if(mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    public GoogleClientService getInstance(@NonNull Context context){
        mContext = context;
        return GoogleClientObject.GOOGLE_CLIENT_SERVICE;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void googleApiClientConnect(){
        assert mGoogleApiClient != null;
        mGoogleApiClient.connect();
    }

    public void googleApiClientDisconnect(){
        assert mGoogleApiClient != null;
        mGoogleApiClient.disconnect();
    }
}
