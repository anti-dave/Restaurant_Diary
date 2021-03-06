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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.restaurantdiary.data.RestaurantContract.ProspectiveRestaurantEntry;
import com.example.android.restaurantdiary.utils.ImageUtils;

public class ProspectiveRestaurantActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Logger tag */
    public static final String LOG_TAG = ProspectiveRestaurantActivity.class.getSimpleName();

    private ProspectiveRestaurantCursoryAdapter mCursorAdapter;

    // not sure if the loader should be the same as visited loader
    private static final int RESTAURANT_LOADER = 1;

    /**
     * It is the onCreate method for activity.
     *
     * @param savedInstanceState state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospective_restaurant);

        FloatingActionButton fab = findViewById(R.id.fab_want_to_visit);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProspectiveRestaurantActivity.this, ProspectiveRestaurantFormActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView itemListView = (ListView) findViewById(R.id.list_visited);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new ProspectiveRestaurantCursoryAdapter(this, null);
        itemListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(ProspectiveRestaurantActivity.this, ProspectiveRestaurantFormActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link ProspectiveRestaurantEntry#CONTENT_URI}.
                Uri currentRestaurantUri =
                        ContentUris.withAppendedId(ProspectiveRestaurantEntry.CONTENT_URI, id);

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
        values.put(ProspectiveRestaurantEntry.COLUMN_RESTAURANT_NAME, "Jakes Pizza Shack");
        values.put(ProspectiveRestaurantEntry.COLUMN_RESTAURANT_ADDRESS, "101 Moonbase, Moon");
        values.put(ProspectiveRestaurantEntry.COLUMN_RESTAURANT_NOTE, "It was too good I died");
        values.put(ProspectiveRestaurantEntry.COLUMN_RESTAURANT_PHONE, "123-456-7890");
        values.put(ProspectiveRestaurantEntry.COLUMN_RESTAURANT_IMAGE, dummyImageInBytes);
        Uri newUri = getContentResolver().insert(ProspectiveRestaurantEntry.CONTENT_URI, values);
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
                ProspectiveRestaurantEntry._ID,
                ProspectiveRestaurantEntry.COLUMN_RESTAURANT_NAME,
                ProspectiveRestaurantEntry.COLUMN_RESTAURANT_ADDRESS,
                ProspectiveRestaurantEntry.COLUMN_RESTAURANT_NOTE,
                ProspectiveRestaurantEntry.COLUMN_RESTAURANT_PHONE,
                ProspectiveRestaurantEntry.COLUMN_RESTAURANT_IMAGE };

        return new CursorLoader(this,
                ProspectiveRestaurantEntry.CONTENT_URI,
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

}
