package com.example.android.restaurantdiary;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.restaurantdiary.data.RestaurantContract.VisitedRestaurantEntry;
import com.example.android.restaurantdiary.utils.ImageUtils;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitedRestaurantActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Logger tag */
    public static final String LOG_TAG = VisitedRestaurantActivity.class.getSimpleName();

    private VisitedRestaurantCursoryAdapter mCursorAdapter;

    private static final int RESTAURANT_LOADER = 0;

    private String mSentimentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited_restaurant);

        /**
         *  Clicking this button should allow the user to add a restaurant they have visited
         *
         *  In the future this will create an intent for a form activity that allows the user to give information about their restaurant
         * */
        FloatingActionButton fab = findViewById(R.id.fab_visited);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formIntent = new Intent(VisitedRestaurantActivity.this, VisitedRestaurantFormActivity.class);
                startActivity(formIntent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView itemListView = (ListView) findViewById(R.id.list_visited);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new VisitedRestaurantCursoryAdapter(this, null);
        itemListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(VisitedRestaurantActivity.this, VisitedRestaurantFormActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link VisitedRestaurantEntry#CONTENT_URI}.
                Uri currentRestaurantUri =
                        ContentUris.withAppendedId(VisitedRestaurantEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentRestaurantUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(RESTAURANT_LOADER, null, this);

    }

    /**
     * Creates the menu with oncreate.
     *
     * @param menu
     * @return bool on whether it was successful.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }


    /**
     * Logic for when menu is pressed.
     *
     * @param item
     * @return bool on whether it was successful.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertDummyItem();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to insert hardcoded item data into the database. For debugging purposes only.
     */
    private void insertDummyItem() {
        // Fetch dummy image
        Bitmap dummyImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.jakes_pizza);
        // Convert dummy image to bytes so it can be written to db
        byte[] dummyImageInBytes = ImageUtils.getBytes(dummyImage);

        ContentValues values = new ContentValues();
        values.put(VisitedRestaurantEntry.COLUMN_RESTAURANT_NAME, "Jakes Pizza Shack");
        values.put(VisitedRestaurantEntry.COLUMN_RESTAURANT_ADDRESS, "101 Moonbase, Moon");
        values.put(VisitedRestaurantEntry.COLUMN_RESTAURANT_NOTE, "It was too good I died");
        values.put(VisitedRestaurantEntry.COLUMN_RESTAURANT_PHONE, "123-456-7890");
        values.put(VisitedRestaurantEntry.COLUMN_RESTAURANT_IMAGE, dummyImageInBytes);
        Uri newUri = getContentResolver().insert(VisitedRestaurantEntry.CONTENT_URI, values);
        AskWatsonTask task = new AskWatsonTask();
        task.execute("Its wasz to gud I died");
        Log.d(LOG_TAG, "Successfully inserted dummy data.");
    }

    /**
     * Initializes the cursor with the activity.
     *
     * @param i
     * @param bundle
     * @return cursor
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                VisitedRestaurantEntry._ID,
                VisitedRestaurantEntry.COLUMN_RESTAURANT_NAME,
                VisitedRestaurantEntry.COLUMN_RESTAURANT_ADDRESS,
                VisitedRestaurantEntry.COLUMN_RESTAURANT_NOTE,
                VisitedRestaurantEntry.COLUMN_RESTAURANT_PHONE,
                VisitedRestaurantEntry.COLUMN_RESTAURANT_IMAGE };

        return new CursorLoader(this,
                VisitedRestaurantEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    /*
     * Stubs for methods to be implemented later. Will need for views.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link RestaurantCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    /*
     * Stubs for methods to be implemented later. Will need for views.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    private class AskWatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textsToAnalyse) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //textView.setText("what is happening inside a thread - we are running Watson AlchemyAPI");
                }
            });

            NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                    NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                    "9aff14e2-5530-40e8-98f9-fdb8a25e0e7c",
                    "AZeTU8O1UOSt"
            );

            String text =textsToAnalyse[0];

            SentimentOptions sentiment = new SentimentOptions.Builder()
                    .build();

            Features features = new Features.Builder()
                    .sentiment(sentiment)
                    .build();

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .text(text)
                    .features(features)
                    .build();

            AnalysisResults response = service
                    .analyze(parameters)
                    .execute();

            return response.toString();

        }

        //setting the value of UI outside of the thread
        @Override
        protected void onPostExecute(String result) {
            Log.e(LOG_TAG, "*******************"+result+"*************************");
            mSentimentTemp = result;
        }
    }
}
