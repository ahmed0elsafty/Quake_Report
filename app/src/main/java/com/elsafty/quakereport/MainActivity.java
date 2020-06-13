package com.elsafty.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;

import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ListItem>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;

//    private static final String USGS_REQUEST_URL =
//            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=magnitude&minmag=5&limit=10";
private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    private static final String LOG_TAG = MainActivity.class.getName();

    ListItemAdapter mListItemAdapter;
    TextView emptyView;
    View loadingSpinner;
    ImageView root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListItemAdapter = new ListItemAdapter(MainActivity.this, R.layout.list_item, new ArrayList<ListItem>());
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mListItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem currentEarthquake = (ListItem) mListItemAdapter.getItem(i);
                Uri webpage = Uri.parse(currentEarthquake.getmURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        //EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        // task.execute(USGS_REQUEST_URL);
        root=findViewById(R.id.root);
        root.setBackgroundResource(R.drawable.background);


        emptyView = (TextView)findViewById(R.id.empty);
        loadingSpinner=findViewById(R.id.loading_spinner);
        listView.setEmptyView(emptyView);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, MainActivity.this);
        }
        else {
            root.setBackgroundResource(R.drawable.background);
            emptyView.setText(R.string.no_internet_connection);
            loadingSpinner.setVisibility(View.GONE);
            root.setVisibility(View.GONE);
        }


    }

    @NonNull
    @Override
    public Loader<List<ListItem>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "200");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<ListItem>> loader, List<ListItem> listItems) {

        mListItemAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (listItems != null && !listItems.isEmpty()) {
            mListItemAdapter.addAll(listItems);
        }
        emptyView.setText(R.string.No_Earthquakes);
        loadingSpinner.setVisibility(View.GONE);
        root.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ListItem>> loader) {


        mListItemAdapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

