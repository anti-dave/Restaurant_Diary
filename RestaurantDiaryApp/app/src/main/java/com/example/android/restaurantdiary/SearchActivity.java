package com.example.android.restaurantdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*
        * Rob was here
        * I'm not sure if you guys wanted me to do anything for this part but this feels like more backendy(thats tots a werd beeteedoubleyou)
        *
        * user entered text can be found in ----> R.id.search_activity_user_input <---- use it as you will...
        * */

        // jake says yo and demo/check that adapter works
        ArrayList<Restaurant> locations = new ArrayList<Restaurant>();

        locations.add(new Restaurant("Mario's Pizza", "pizza","123 Boston Street", "781 123 4567","4"));
        locations.add(new Restaurant("Luigi's Pizza", "pizza","124 Boston Street", "781 123 4568","5"));
        locations.add(new Restaurant("Clam Chowder?", "soup","5 MysteryMeat Ave", "156 545 1846","1"));
        locations.add(new Restaurant("The House Of Ramen", "raman", "5 Ramen Road", "012 345 6789","11"));

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, locations);
        ListView listView = (ListView) findViewById(R.id.restaurant_in_search_list);
        listView.setAdapter(restaurantAdapter);
    }
}
