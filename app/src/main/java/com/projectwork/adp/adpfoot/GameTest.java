package com.projectwork.adp.adpfoot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_test_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new game1())
                    .commitNow();
        }
    }



}
