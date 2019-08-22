package com.projectwork.adp.adpfoot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mref;
    private com.tuyenmonkey.mkloader.MKLoader mProgress;
    private LawsAdapter mAdapter;
    private ArrayList<Laws_Database> mData;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();
        final RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mProgress=findViewById(R.id.myprogress);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayList that will contain the data
        mData = new ArrayList<>();
        mAdapter = new LawsAdapter(MainActivity.this, mData);
        mRecyclerView.setAdapter(mAdapter);
        //Get the data
        //Initialize the adapter and set it ot the RecyclerView

        new CountDownTimer(2000,1000)
        {
            public void onTick(long ms)
            {

                mProgress.setVisibility(View.VISIBLE);
            }
            public void onFinish(){
                mref.child("ADP").child("laws").
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                showData(dataSnapshot);
                                mProgress.setVisibility(View.GONE);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        }.start();


     menu_inflate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        menu_inflate();
    }

    private void menu_inflate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);

        }else   {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_user_drawer);
        }

    }
    private void signOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            FirebaseAuth.getInstance().signOut();
            super.finish();
        }

    }
    private void showData(DataSnapshot dataSnapshot) {
        mData.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Laws_Database userDatabase = new Laws_Database();

            userDatabase.setTitle((ds.getValue(Laws_Database.class)).getTitle());
            userDatabase.setSub_title((ds.getValue(Laws_Database.class)).getSub_title());
            userDatabase.setDetail((ds.getValue(Laws_Database.class)).getDetail());
            userDatabase.setImg((ds.getValue(Laws_Database.class)).getImg());
            userDatabase.setAudio((ds.getValue(Laws_Database.class)).getAudio());
            userDatabase.setVideo((ds.getValue(Laws_Database.class)).getVideo());

            mData.add(new Laws_Database(userDatabase.getTitle(), userDatabase.getSub_title(),userDatabase.getImg(),userDatabase.getAudio(),userDatabase.getVideo(),userDatabase.getDetail()));

        }

        mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            super.finish();
            return true;
        } else if (id == R.id.login) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user==null) {
                item.setTitle("Login");
                startActivity(new Intent(MainActivity.this, Login.class));
            }else {
                item.setTitle("Sign Out");
                signOut();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.glossary) {
            startActivity(new Intent(MainActivity.this,Glossary.class));
        }else if (id == R.id.add_laws) {
           startActivity(new Intent(MainActivity.this,AddFifiLaws.class));
        }else if (id == R.id.nav_share) {
             try {
                 Intent shareIntent = new Intent(Intent.ACTION_SEND);
                 shareIntent.setType("text/plain");
                 shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ADP FOOT");
                 String shareMessage= "\nLet me recommend you this application\n\n";
                 shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                 shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                 startActivity(Intent.createChooser(shareIntent, "choose one"));
             } catch(Exception e) {
                 //e.toString();
             }
         }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
