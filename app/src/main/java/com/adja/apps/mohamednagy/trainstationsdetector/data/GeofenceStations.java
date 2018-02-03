package com.adja.apps.mohamednagy.trainstationsdetector.data;


import com.adja.apps.mohamednagy.trainstationsdetector.properties.Station;

/**
 * Created by Mohamed Nagy on 2/1/2018.
 */

public class GeofenceStations {

    public static final int FAISL_STATION_ID = 0;
    public static final int CAIRO_UNIVERSITY_STATION_ID = 1;

    private static final Station[] SUBWAY_STATIONS = new Station[]{
                new Station(30.0143063, 31.1870751, "Faisl Station", FAISL_STATION_ID),
                new Station(31.1870751,30.0143063, "Cairo University Station", CAIRO_UNIVERSITY_STATION_ID)
    };


    public static Station getStationFromId(int id){
        return SUBWAY_STATIONS[id];
    }

    public static Station getNextStationFromId(int id){
        int nextStationId = id + 1;
        if(nextStationId < SUBWAY_STATIONS.length)
            return SUBWAY_STATIONS[id + 1];

        return null;
    }
}
