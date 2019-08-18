package com.projectwork.adp.adpfoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final Thread timmer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {

                    e.printStackTrace();


                } finally {
                    startActivity(new Intent(splash.this,MainActivity.class));
                }
            }
        };
        timmer.start();
    }

    //onPause ExitActivity Method
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    }


