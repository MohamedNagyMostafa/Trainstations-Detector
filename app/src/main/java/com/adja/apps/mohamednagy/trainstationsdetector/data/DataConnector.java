package com.adja.apps.mohamednagy.trainstationsdetector.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.adja.apps.mohamednagy.trainstationsdetector.data.GeofenceStations;
import com.adja.apps.mohamednagy.trainstationsdetector.properties.Subway;

import java.util.Calendar;

/**
 * Created by Mohamed Nagy on 2/2/2018.
 */

public class DataConnector {
    public static final String ENTER_HANDLER_CODE = "EnHn";
    public static final String EXIT_HANDLER_CODE = "ExHn";
    private static final String TIME_IN_MILLIS_CODE = "Tn";
    private static final String SUBWAY_STATION_CODE = "Sn";

    private Long mEnterStationTimeInMills;
    private Long mExitStationTimeInMills;
    private Handler mEnterStationHandler;
    private Handler mExitStationHandler;

    public DataConnector(final Subway subway){
        mEnterStationHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();

                mEnterStationTimeInMills = reply.getLong(TIME_IN_MILLIS_CODE);

                subway.setCurrentStation(GeofenceStations.getStationFromId(reply.getInt(SUBWAY_STATION_CODE)));
                subway.setNextStation(GeofenceStations.getNextStationFromId(reply.getInt(SUBWAY_STATION_CODE)));

                if(mExitStationTimeInMills != null) {
                    subway.setPreviousDuration(mEnterStationTimeInMills - mExitStationTimeInMills);
                }

                subway.onStation(subway);
            }
        };

        mExitStationHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();
                mExitStationTimeInMills = reply.getLong(TIME_IN_MILLIS_CODE);
                subway.setCurrentStation(GeofenceStations.getStationFromId(reply.getInt(SUBWAY_STATION_CODE)));
                subway.setNextStation(GeofenceStations.getNextStationFromId(reply.getInt(SUBWAY_STATION_CODE)));

                subway.onMove(subway);
            }
        };
    }

    public Handler getEnterStationHandler() {
        return mEnterStationHandler;
    }

    public Handler getExitStationHandler() {
        return mExitStationHandler;
    }

    public static void dataHandler(String handlerCode, Intent intent, String id){
        Bundle bundle = intent.getExtras();

        assert bundle != null;

        Messenger messenger = (Messenger) bundle.get(handlerCode);
        Message message = Message.obtain();
        Bundle newDataStore = new Bundle();

        newDataStore.putLong(TIME_IN_MILLIS_CODE, Calendar.getInstance().getTimeInMillis());
        newDataStore.putInt(SUBWAY_STATION_CODE, Integer.valueOf(id));
        message.setData(newDataStore);

        try{
            assert messenger != null;
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

}
