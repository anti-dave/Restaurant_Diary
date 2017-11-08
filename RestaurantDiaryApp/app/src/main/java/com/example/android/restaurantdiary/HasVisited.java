package com.example.android.restaurantdiary;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HasVisited extends AppCompatActivity {

    private RestaurantAdapter mAdapter;
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_visited);

        ListView RestaurantListView = (ListView) findViewById(R.id.list_visited);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view_visited);
        RestaurantListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new RestaurantAdapter(this, new ArrayList<Restaurant>());

        RestaurantListView.setAdapter(mAdapter);



        /**
         *  Clicking this button should allow the user to add a restaurant they have visited
         *
         *  In the future this will create an intent for a form activity that allows the user to give information about their restaurant
         * */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_visited);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something for now, probably add a generic restaurant to the listview
            }
        });


    }
}
