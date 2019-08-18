package com.projectwork.adp.adpfoot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Glossary extends AppCompatActivity {
    private GlossaryAdapter mAdapter;
    private ArrayList<Glossory_Database> mSportsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the adapter and set it ot the RecyclerView


        //Initialize the ArrayList that will contain the data
        mSportsData = new ArrayList<>();
        mAdapter = new GlossaryAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);


        Map<String, String> items = new HashMap<>();
        items.put("FIFA", "The acronym used for the Federation Internationale de Football Association,the world's governing body for the game of association football,which is based in Switzerland.");
        items.put("Technical Area", "Defined area for the team officials which");
        items.put("DRIBBLE", "Keeping control of the ball while running");
        items.put("DUMMY RUN", "A run by a player without the bal");
        items.put("FOUL", "An illegal play");
        items.put("FUTSAL", "A version of football played indoors.");
        items.put("KICKOFF", "The Kickoff is taken from the center spot at the start of play,at the beginning of each half and after a goal has been scored.");
        items.put("INFRINGEMENT", "An action which is against/breaks/violates the laws.");
        items.put("INTERCEPT", "To prevent a ball from reaching its intended destination");
        items.put("ADVANTAGE", "The Referee allows play to continue when and offence has occured, and this benefits the non offending team");
        items.put("ATTACKER", "A player whose job is to play the ball forward towards the opponent's goal area to create a scoring opportunity");


        for (Map.Entry<String, String> entry : items.entrySet()) {
            mSportsData.add(new Glossory_Database(entry.getKey() ,  entry.getValue()));
        }

          //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
