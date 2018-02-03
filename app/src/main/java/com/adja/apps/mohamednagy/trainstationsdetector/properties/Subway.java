package com.adja.apps.mohamednagy.trainstationsdetector.properties;

import android.graphics.Color;

/**
 * Created by Mohamed Nagy on 2/3/2018.
 */

public abstract class Subway {

    private Station mCurrentStation;
    private Station mNextStation;
    private Long mPreviousDuration;

    public Subway(){}

    public void setCurrentStation(Station mCurrentStation) {
        this.mCurrentStation = mCurrentStation;
    }

    public void setNextStation(Station mNextStation) {
        this.mNextStation = mNextStation;
    }

    public Station getCurrentStation() {
        return mCurrentStation;
    }

    public Station getNextStation() {
        return mNextStation;
    }

    public void setPreviousDuration(long previousDuration){
        mPreviousDuration = previousDuration;
    }

    public Long getPreviousDuration() {
        return mPreviousDuration;
    }

    enum State{
        ON_MOVING("On Moving", Color.GREEN), ON_STATION("On Station", Color.RED);

        public int color;
        public String name;

        State(String name, int colorId){
            color = colorId;
        }
    }

    public abstract void onMove(Subway subway);
    public abstract void onStation(Subway subway);
}
