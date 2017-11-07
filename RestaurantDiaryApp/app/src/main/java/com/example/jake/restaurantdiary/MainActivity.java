package com.example.jake.restaurantdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toVisit = (Button) findViewById(R.id.button_main_menu_want_to);
        Button visited = (Button) findViewById(R.id.button_main_menu_have_been);
        Button search = (Button) findViewById(R.id.button_main_menu_search);

        toVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toVisitIntent = new Intent(MainActivity.this, WantToVisit.class);
                startActivity(toVisitIntent);
            }
        });

        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitedIntent = new Intent(MainActivity.this, HasVisited.class);
                startActivity(visitedIntent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });
    }
}
