package com.adja.apps.mohamednagy.trainstationsdetector.network;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.widget.Toast;

/**
 * Created by Mohamed Nagy on 2/6/2018.
 */

public class SendDataLoaderHandler implements LoaderManager.LoaderCallbacks<Void>{

    private final Context CONTEXT;
    private static final String SENDING_DATA_SUCCESS = "Data is sent";
    public static final String DATA_SEND_ID = "sending";


    public SendDataLoaderHandler(Context context){
        CONTEXT = context;
    }

    @Override
    public android.support.v4.content.Loader<Void> onCreateLoader(int id, Bundle args) {
        return new SendDataLoader(
                CONTEXT,
                args.getLong(DATA_SEND_ID)
        );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Void> loader, Void data) {
        Toast.makeText(CONTEXT, SENDING_DATA_SUCCESS, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Void> loader) {

    }

}
