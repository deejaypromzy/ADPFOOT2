package com.projectwork.adp.adpfoot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity {
    private YouTubePlayer mPlayer;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        final String key = getIntent().getStringExtra("key");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        youTubePlayerView.initialize("", onInitializedListener);

            }
        });

        youTubePlayerView =  findViewById(R.id.youtube_view);



        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                mPlayer = player;
                mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                mPlayer.setFullscreen(true);
                mPlayer.loadVideo(key);
                mPlayer.play();

//
//                switch (key){
//                    case "one":
//                        mPlayer.loadVideo("JZJMHv2cLrI");
//                        mPlayer.play();
//                        break;
//                    case "two":
//                        mPlayer.loadVideo("A50YmfDSlSA");
//                        mPlayer.play();
//                        break;
//                    case "three":
//                        mPlayer.loadVideo("uLSkpyHEGR0");
//
//                        mPlayer.play();
//                        break;
//                    case "four":
//                        mPlayer.loadVideo("XHJjz9wHG8");
//                        mPlayer.play();
//                        break;
//                    case "five":
//                        mPlayer.loadVideo("f6MdWyuUfzs");
//                        mPlayer.play();
//                        break;
//                    case "six":
//                        mPlayer.loadVideo("7U05-b2VtIg");
//                        mPlayer.play();
//                        break;
//                    case "seven":
//                        mPlayer.loadVideo("LELv7P_3SA8");
//                        mPlayer.play();
//                        break;
//                    case "eight":
//                        mPlayer.loadVideo("cjVAZskU4Tw");
//                        mPlayer.play();
//                        break;
//                    case "nine":
//                        mPlayer.loadVideo("gorGiH_2HXU");
//                        mPlayer.play();
//                        break;
//                    case "ten":
//                        mPlayer.loadVideo("hzRVy31U4dE");
//                        mPlayer.play();
//                        break;
//                    case "11":
//                        mPlayer.loadVideo("D-mcLd3GAPc");
//                        mPlayer.play();
//                        break;
//                    case "12":
//                        mPlayer.loadVideo("0HtqESB7nBQ");
//                        mPlayer.play();
//                        break;
//                    case "13":
//                        mPlayer.loadVideo("xWfGAnUNgIs");
//                        mPlayer.play();
//                        break;
//                    case "14":
//                        mPlayer.loadVideo("cnDza9uXuhQ");
//                        mPlayer.play();
//                        break;
//                    case "15":
//                        mPlayer.loadVideo("-FnYx3Z63bQ");
//                        mPlayer.play();
//                        break;
//                    case "16":
//                        mPlayer.loadVideo("CWLJ1J5HdZY");
//                        mPlayer.play();
//                        break;
//                    case "17":
//                        mPlayer.loadVideo("4RbhSYw8xF8");
//                        mPlayer.play();
//                        break;
//
//                    default:
//                        mPlayer.loadVideo("eAl2O0mu_TU");
//                        mPlayer.play();
//                        break;
//                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {

         String Error =error.toString();
            Toast.makeText(getApplicationContext(),Error,Toast.LENGTH_LONG).show();
            }
        };
    }
}
