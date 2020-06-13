package com.elsafty.quakereport;

public class ListItem {
    //    private GregorianCalendar mDate;
    long mDate;
    private String mCity, mURL;
    private float mMagnitude;

    public ListItem(float mMagnitude, String mCity, long mDate, String mURL) {
        this.mMagnitude = mMagnitude;
        this.mCity = mCity;
        this.mDate = mDate;
        this.mURL = mURL;
    }

    public String getmURL() {
        return mURL;
    }

    public float getmMagnitude() {
        return mMagnitude;
    }

    public String getmCity() {
        return mCity;
    }

    public long getmDate() {
        return mDate;
    }
}
