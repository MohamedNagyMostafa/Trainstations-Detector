package com.adja.apps.mohamednagy.trainstationsdetector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Mohamed Nagy on 2/2/2018.
 */

public class DataConnector {
    public static final String ENTER_HANDLER_CODE = "EnHn";
    public static final String EXIT_HANDLER_CODE = "ExHn";

    private Long mEnterStationTimeInMills;
    private Long mExitStationTimeInMills;
    private Handler mEnterStationHandler;
    private Handler mExitStationHandler;

    private Long mDurationBetweenStationsInMills;

    public DataConnector(){
        mEnterStationHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();
                mEnterStationTimeInMills = reply.getLong(ENTER_HANDLER_CODE);
                Log.e("work","working");
                if(mExitStationTimeInMills != null) {
                    mDurationBetweenStationsInMills = mEnterStationTimeInMills - mExitStationTimeInMills;
                    Log.e("dasdasd","sdasdadsadsadsad " + mDurationBetweenStationsInMills);
                }

            }
        };

        mExitStationHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();
                mExitStationTimeInMills = reply.getLong(EXIT_HANDLER_CODE);
            }
        };

    }

    public Handler getEnterStationHandler() {
        return mEnterStationHandler;
    }

    public Handler getExitStationHandler() {
        return mExitStationHandler;
    }

    public static void dataHandler(String handlerCode, Intent intent){
        Bundle bundle = intent.getExtras();

        assert bundle != null;

        Messenger messenger = (Messenger) bundle.get(handlerCode);
        Message message = Message.obtain();
        Bundle newDataStore = new Bundle();

        newDataStore.putLong(handlerCode, Calendar.getInstance().getTimeInMillis());
        message.setData(newDataStore);

        try{
            assert messenger != null;
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

}
