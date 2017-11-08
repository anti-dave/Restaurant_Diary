package com.example.android.restaurantdiary;

/**
 * Created by jake on 11/2/17.
 */

public class Restaurant {
    private String mName;
    // rest of the private stuff

    public Restaurant(String name){
        mName = name;
    }

    public String getName(){ return mName; }
}
