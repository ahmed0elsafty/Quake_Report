package com.elsafty.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class EarthquakeLoader extends AsyncTaskLoader<List<ListItem>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    String mUrl;


    public EarthquakeLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<ListItem> loadInBackground() {
        Log.e(LOG_TAG,"this is  load in background");

        if (mUrl == null) {
            return null;
        }
        List<ListItem> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }

}
