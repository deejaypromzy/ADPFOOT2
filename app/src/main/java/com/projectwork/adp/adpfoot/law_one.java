package com.projectwork.adp.adpfoot;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;
import java.util.Locale;

public class law_one extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, TextToSpeech.OnInitListener {
    SliderLayout sliderLayout ;
    HashMap<String, Integer> HashMapForLocalRes ;
    private  String key;
    private TextView texttoplay;
    private TextToSpeech tts;
    LinearLayout btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_intro);


        sliderLayout = findViewById(R.id.slider);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);

                collapsingToolbar.setTitle("Introduction");


      //  loadBackdrop();
        //Call this method to add images from local drawable folder .
        AddImageUrlFormLocalRes();
        for(String name : HashMapForLocalRes.keySet()){

            TextSliderView textSliderView = new TextSliderView(law_one.this);
            textSliderView
                    .description(name)
                    .image(HashMapForLocalRes.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(100);
        sliderLayout.addOnPageChangeListener(law_one.this);
        texttoplay=findViewById(R.id.recyclerView);
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
    public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<String, Integer>();

        key = getIntent().getStringExtra("key");
        if (key==null){
            key="one";
        }

        switch (key){
            case "intro":
                HashMapForLocalRes.put("1", R.drawable.bg);
                HashMapForLocalRes.put("2", R.drawable.law_bg);
                break;
            case "one":
                HashMapForLocalRes.put("8", R.drawable.lawoneh);
                HashMapForLocalRes.put("7", R.drawable.lawoneg);
                HashMapForLocalRes.put("6", R.drawable.lawonef);
                HashMapForLocalRes.put("5", R.drawable.lawonee);
                HashMapForLocalRes.put("4", R.drawable.lawoned);
                HashMapForLocalRes.put("3", R.drawable.lawonec);
                HashMapForLocalRes.put("2", R.drawable.lawoneb);
                HashMapForLocalRes.put("1", R.drawable.lawonea);

                break;
            case "two":
                HashMapForLocalRes.put("4", R.drawable.lawtwod);
                HashMapForLocalRes.put("3", R.drawable.lawtwoc);
                HashMapForLocalRes.put("2", R.drawable.lawtwob);
                HashMapForLocalRes.put("1", R.drawable.lawtwoa);
                break;
            case "three":
                HashMapForLocalRes.put("15", R.drawable.lawthreeo);
                HashMapForLocalRes.put("14", R.drawable.lawthreen);
                HashMapForLocalRes.put("13", R.drawable.lawthreem);
                HashMapForLocalRes.put("12", R.drawable.lawthreel);
                HashMapForLocalRes.put("11", R.drawable.lawthreek);
                HashMapForLocalRes.put("10", R.drawable.lawthreej);
                HashMapForLocalRes.put("9", R.drawable.lawthreei);
                HashMapForLocalRes.put("8", R.drawable.lawthreeh);
                HashMapForLocalRes.put("7", R.drawable.lawthreeg);
                HashMapForLocalRes.put("6", R.drawable.lawthreef);
                HashMapForLocalRes.put("5", R.drawable.lawthreee);
                HashMapForLocalRes.put("4", R.drawable.lawthreed);
                HashMapForLocalRes.put("3", R.drawable.lawthreec);
                HashMapForLocalRes.put("2", R.drawable.lawthreeb);
                HashMapForLocalRes.put("1", R.drawable.lawthreea);
                break;
            case "four":
                HashMapForLocalRes.put("1", R.drawable.lawfourc);
                HashMapForLocalRes.put("2", R.drawable.lawfourb);
                HashMapForLocalRes.put("3", R.drawable.lawfoura);
                HashMapForLocalRes.put("4", R.drawable.lawfourd);
                HashMapForLocalRes.put("5", R.drawable.lawfoure);
                HashMapForLocalRes.put("6", R.drawable.lawfourf);
                HashMapForLocalRes.put("7", R.drawable.lawfourg);
                HashMapForLocalRes.put("8", R.drawable.lawfourh);
                break;
            case "five":
                HashMapForLocalRes.put("1", R.drawable.lawfiveg);
                HashMapForLocalRes.put("2", R.drawable.lawfivef);
                HashMapForLocalRes.put("3", R.drawable.lawfivee);
                HashMapForLocalRes.put("4", R.drawable.lawfived);
                HashMapForLocalRes.put("5", R.drawable.lawfivec);
                HashMapForLocalRes.put("6", R.drawable.lawfiveb);
                HashMapForLocalRes.put("7", R.drawable.lawfivea);

                break;
            case "six":
                HashMapForLocalRes.put("1", R.drawable.lawsixaa);
                HashMapForLocalRes.put("2", R.drawable.lawsixa);
                HashMapForLocalRes.put("3", R.drawable.lawsixb);
                HashMapForLocalRes.put("4", R.drawable.lawsixc);
                HashMapForLocalRes.put("5", R.drawable.lawsixd);
                HashMapForLocalRes.put("6", R.drawable.lawsixe);
                HashMapForLocalRes.put("7", R.drawable.lawsixf);
                HashMapForLocalRes.put("8", R.drawable.lawsixg);
                HashMapForLocalRes.put("9", R.drawable.lawsixh);
                HashMapForLocalRes.put("10", R.drawable.lawsixi);
                HashMapForLocalRes.put("11", R.drawable.lawsixj);
                HashMapForLocalRes.put("12", R.drawable.lawsixj);
                HashMapForLocalRes.put("13", R.drawable.lawsixk);

                break;
            case "seven":
                HashMapForLocalRes.put("1", R.drawable.lawsevena);
                HashMapForLocalRes.put("2", R.drawable.lawsevenb);
                HashMapForLocalRes.put("3", R.drawable.lawsevenc);
                HashMapForLocalRes.put("4", R.drawable.lawsevend);
                HashMapForLocalRes.put("5", R.drawable.lawsevene);

                break;
            case "eight":
                HashMapForLocalRes.put("1", R.drawable.laweighta);
                HashMapForLocalRes.put("2", R.drawable.laweightb);
                HashMapForLocalRes.put("3", R.drawable.laweightc);
                HashMapForLocalRes.put("4", R.drawable.laweightd);
                HashMapForLocalRes.put("5", R.drawable.laweighte);
                HashMapForLocalRes.put("6", R.drawable.laweightf);

                break;
            case "nine":
                HashMapForLocalRes.put("1", R.drawable.lawninea);
                HashMapForLocalRes.put("2", R.drawable.lawnineb);
                HashMapForLocalRes.put("3", R.drawable.lawninec);
                HashMapForLocalRes.put("4", R.drawable.lawnined);
                HashMapForLocalRes.put("5", R.drawable.lawninee);
                HashMapForLocalRes.put("6", R.drawable.lawninef);
                break;
            case "ten":
                HashMapForLocalRes.put("1", R.drawable.lawtena);
                HashMapForLocalRes.put("2", R.drawable.lawtenb);
                HashMapForLocalRes.put("3", R.drawable.lawtenc);
                HashMapForLocalRes.put("4", R.drawable.lawtend);
                break;
            case "11":
                HashMapForLocalRes.put("1", R.drawable.lawtena);
                HashMapForLocalRes.put("2", R.drawable.elevena);
                HashMapForLocalRes.put("3", R.drawable.elevenb);
                HashMapForLocalRes.put("4", R.drawable.elevenc);
                HashMapForLocalRes.put("5", R.drawable.elevend);
                HashMapForLocalRes.put("6", R.drawable.elevene);
                HashMapForLocalRes.put("7", R.drawable.elevenf);
                HashMapForLocalRes.put("8", R.drawable.elevenh);
                HashMapForLocalRes.put("9", R.drawable.eleveni);
                HashMapForLocalRes.put("10", R.drawable.elevenj);
                HashMapForLocalRes.put("11", R.drawable.elevenk);
                HashMapForLocalRes.put("12", R.drawable.elevenl);
                HashMapForLocalRes.put("13", R.drawable.elevenm);
                HashMapForLocalRes.put("14", R.drawable.eleveno);
                HashMapForLocalRes.put("15", R.drawable.eleveno);

                break;
            case "12":
                HashMapForLocalRes.put("1", R.drawable.lawtwelvea);
               // HashMapForLocalRes.put("2", R.drawable.lawtwelveb);
                HashMapForLocalRes.put("3", R.drawable.lawtwelvec);

                break;
            case "13":
                HashMapForLocalRes.put("1", R.drawable.lawthirteena);
                HashMapForLocalRes.put("2", R.drawable.lawthirteenb);
                HashMapForLocalRes.put("3", R.drawable.lawthirteenc);
                HashMapForLocalRes.put("4", R.drawable.lawthirteend);
                HashMapForLocalRes.put("5", R.drawable.lawthirteene);
                HashMapForLocalRes.put("6", R.drawable.lawthirteenf);
                break;
            case "14":
                HashMapForLocalRes.put("1", R.drawable.lawfoura);
                HashMapForLocalRes.put("2", R.drawable.lawfourb);
                HashMapForLocalRes.put("3", R.drawable.lawfourc);
                HashMapForLocalRes.put("4", R.drawable.lawfourd);
                HashMapForLocalRes.put("5", R.drawable.lawfoure);
                HashMapForLocalRes.put("6", R.drawable.lawfourf);
                HashMapForLocalRes.put("15", R.drawable.lawfourg);
                HashMapForLocalRes.put("7", R.drawable.lawfourh);
                HashMapForLocalRes.put("8", R.drawable.lawfouri);
                HashMapForLocalRes.put("9", R.drawable.lawfourj);
                HashMapForLocalRes.put("10", R.drawable.lawfourk);
                HashMapForLocalRes.put("11", R.drawable.lawfourl);
                HashMapForLocalRes.put("12", R.drawable.lawfourm);
                HashMapForLocalRes.put("13", R.drawable.lawfourn);
                HashMapForLocalRes.put("14", R.drawable.lawfouro);
                break;
            case "15":
                HashMapForLocalRes.put("1", R.drawable.lawfifteena);
                HashMapForLocalRes.put("2", R.drawable.lawfifteenb);
                HashMapForLocalRes.put("3", R.drawable.lawfifteenc);
                HashMapForLocalRes.put("4", R.drawable.lawfifteend);
                HashMapForLocalRes.put("5", R.drawable.lawfifteene);
                HashMapForLocalRes.put("6", R.drawable.lawfifteenf);
                HashMapForLocalRes.put("8", R.drawable.lawfifteeng);
                HashMapForLocalRes.put("9", R.drawable.lawfifteenh);
                HashMapForLocalRes.put("10", R.drawable.lawfifteeni);
                HashMapForLocalRes.put("11", R.drawable.lawfifteenj);
                HashMapForLocalRes.put("12", R.drawable.lawfifteenk);
                HashMapForLocalRes.put("13", R.drawable.lawfifteenl);
                HashMapForLocalRes.put("14", R.drawable.lawfifteenm);
                HashMapForLocalRes.put("15", R.drawable.lawfifteenn);

                break;
            case "16":
                HashMapForLocalRes.put("1", R.drawable.lawsixteena);
                HashMapForLocalRes.put("2", R.drawable.lawsixteenb);
                HashMapForLocalRes.put("3", R.drawable.lawsixteenc);
                HashMapForLocalRes.put("4", R.drawable.lawsixteend);
                HashMapForLocalRes.put("5", R.drawable.lawsixteene);
                HashMapForLocalRes.put("6", R.drawable.lawsixteenf);
                HashMapForLocalRes.put("7", R.drawable.lawsixteeng);
                HashMapForLocalRes.put("8", R.drawable.lawsixteenh);

                break;
            case "17":
                HashMapForLocalRes.put("1", R.drawable.lawseventeena);
                HashMapForLocalRes.put("2", R.drawable.lawseventeenb);
                HashMapForLocalRes.put("3", R.drawable.lawseventeenc);
                HashMapForLocalRes.put("4", R.drawable.lawseventeend);
                HashMapForLocalRes.put("5", R.drawable.lawseventeene);
                HashMapForLocalRes.put("6", R.drawable.lawseventeenf);
                HashMapForLocalRes.put("7", R.drawable.lawseventeeng);
                HashMapForLocalRes.put("8", R.drawable.lawseventeenh);
                HashMapForLocalRes.put("9", R.drawable.lawseventeeni);
                HashMapForLocalRes.put("10", R.drawable.lawseventeenj);
                HashMapForLocalRes.put("11", R.drawable.lawseventeenk);
                HashMapForLocalRes.put("12", R.drawable.lawseventeenl);
                HashMapForLocalRes.put("13", R.drawable.lawseventeenm);
                HashMapForLocalRes.put("14", R.drawable.lawseventeenn);

                break;
            default:
                HashMapForLocalRes.put("Field", R.drawable.lawonea);
                break;
        }
    }

    public void youtube(View view) {
        key = getIntent().getStringExtra("key");
        if (key==null){
            key="one";
        }
        Intent intent = new Intent(law_one.this,YoutubeVideo.class);
        intent.putExtra("key",key);
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
