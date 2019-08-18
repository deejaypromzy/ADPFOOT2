package com.projectwork.adp.adpfoot;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Locale;

public class law_one extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, TextToSpeech.OnInitListener {
    SliderLayout sliderLayout ;
    HashMap<String, Integer> HashMapForLocalRes ;
    private  String key;
    private TextView texttoplay,tv_details;
    private TextToSpeech tts;
    LinearLayout btnSpeak;
    private String title,image,details;
    private String urL,sub_title;
    private String video;
    private ImageView text;
private FloatingActionButton fab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_intro);

        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
            sub_title = getIntent().getStringExtra("sub_title");
            image = getIntent().getStringExtra("image");
            video = getIntent().getStringExtra("video");
            details = getIntent().getStringExtra("details");

        }


        fab2 = findViewById(R.id.fab2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            fab2.show();
        }else {
            fab2.hide();
        }

fab2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(law_one.this,CHK.class);
        intent.putExtra("title",title);
        intent.putExtra("sub_title",sub_title);
        intent.putExtra("video",video);
        intent.putExtra("image",image);
        intent.putExtra("details",details);
        startActivity(intent);
    }
});

        text = findViewById(R.id.text);
        sliderLayout = findViewById(R.id.slider);


        GlideApp.with(this)
                .load(image)
                .placeholder(R.drawable.law_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(text);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(title);


        // loadBackdrop();
        //Call this method to add images from local drawable folder .

//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("1",R.drawable.law_bg);
//        file_maps.put("2",R.drawable.laweighta);
//
//        for(String name : file_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(this);
//            textSliderView
//                    .description(title)
//                    .image(image)
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",title);
//            sliderLayout.addSlider(textSliderView);
//    }


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(100);
        sliderLayout.addOnPageChangeListener(law_one.this);



        texttoplay=findViewById(R.id.recyclerView);
        texttoplay.setText(details);
        tts = new TextToSpeech(this, this);
        // Refer 'Speak' button
        btnSpeak = (LinearLayout) findViewById(R.id.play);
        // Refer 'Text' control
        // Handle onClick event for button 'Speak'
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Method yet to be defined
                speakOut();
            }

        });




    }

    private void speakOut() {
        // Get the text typed
        String text = texttoplay.getText().toString();
       // If no text is typed, tts will read out 'You haven't typed text'
        // else it reads out the text you typed
        if (!tts.isSpeaking()){
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else {
            tts.stop();
        }

    }

    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

        // Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    public void youtube(View view) {
        Intent intent = new Intent(law_one.this,YoutubeVideo.class);
        intent.putExtra("key",video);
        startActivity(intent);

    }


    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        // TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.UK);
            // If your device doesn't support language you set above
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            // Enable the button - It was disabled in main.xml (Go back and
            // Check it)
            else {
                btnSpeak.setEnabled(true);
            }
            // TTS is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
                    .show();
            Log.e("TTS", "Initilization Failed");
        }
    }

    public void test(View view) {
        startActivity(new Intent(law_one.this,GameTest.class));
    }
}
