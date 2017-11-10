package com.example.android.restaurantdiary;

/**
 * API contract.
 */

public class Restaurant {
    private String mName;
    private String mTypeOfFood;
    private String mAddress;
    private String mPhoneNumber;
    private String mRating;
    // rest of the private stuff

    public Restaurant(String name, String typeOfFood, String address, String phoneNumber, String rating){
        mName = name;
        mTypeOfFood = typeOfFood;
        mPhoneNumber = phoneNumber;
        mAddress = address;
        mRating = rating;
    }

    public String getName(){ return mName; }

    public String getTypeOfFood(){ return mTypeOfFood; }

    public String getAddress(){ return mAddress; }

    public String getPhoneNumber(){ return mPhoneNumber; }

    public String getRating(){ return mRating; }
}
