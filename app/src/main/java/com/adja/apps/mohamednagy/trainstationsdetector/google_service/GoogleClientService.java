package com.adja.apps.mohamednagy.trainstationsdetector.google_service;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Mohamed Nagy on 2/2/2018.
 */

public class GoogleClientService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private GoogleClientCallback mGoogleClientCallback;

    private static class GoogleClientObject{
        private static final GoogleClientService GOOGLE_CLIENT_SERVICE = new GoogleClientService();
    }

    private GoogleClientService(){
    }

    public static GoogleClientService getInstance(){
        return GoogleClientObject.GOOGLE_CLIENT_SERVICE;
    }

    public GoogleClientService build(@NonNull GoogleClientCallback googleClientCallback, @NonNull Context context){
        mGoogleClientCallback = googleClientCallback;
        if(mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        return this;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleClientCallback.onConnected();
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