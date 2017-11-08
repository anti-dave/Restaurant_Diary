package com.example.android.restaurantdiary;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.restaurantdiary.data.RestaurantContract.RestaurantEntry;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** Logger tag */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * onCreate method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                insertItem();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to insert hardcoded item data into the database. For debugging purposes only.
     */
    private void insertItem() {
        // Fetch dummy image
        Bitmap dummyImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.jakes_pizza);
        // Convert dummy image to bytes so it can be written to db
        byte[] dummyImageInBytes = ImageUtils.getBytes(dummyImage);

        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.COLUMN_RESTAURANT_NAME, "Jakes Pizza Shack");
        values.put(RestaurantEntry.COLUMN_RESTAURANT_ADDRESS, "101 Moonbase, Moon");
        values.put(RestaurantEntry.COLUMN_RESTAURANT_NOTE, "It was too good I died");
        values.put(RestaurantEntry.COLUMN_RESTAURANT_IMAGE, dummyImageInBytes);
        Uri newUri = getContentResolver().insert(RestaurantEntry.CONTENT_URI, values);
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
                RestaurantEntry._ID,
                RestaurantEntry.COLUMN_RESTAURANT_NAME,
                RestaurantEntry.COLUMN_RESTAURANT_ADDRESS,
                RestaurantEntry.COLUMN_RESTAURANT_NOTE,
                RestaurantEntry.COLUMN_RESTAURANT_IMAGE };

        return new CursorLoader(this,
                RestaurantEntry.CONTENT_URI,
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

    }

    /*
     * Stubs for methods to be implemented later. Will need for views.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
