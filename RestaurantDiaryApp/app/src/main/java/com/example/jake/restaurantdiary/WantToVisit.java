package com.example.jake.restaurantdiary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WantToVisit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_visit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_want_to_visit);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WantToVisit.this, ActivityForm.class);
                startActivity(intent);
            }
        });
    }
}
