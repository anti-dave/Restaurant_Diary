package com.example.jake.restaurantdiary;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by jake on 11/2/17.
 */

public class RestaurantAdapter  extends ArrayAdapter<Restaurant> {

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurant) {
        super(context, 0, restaurant);
    }

}
