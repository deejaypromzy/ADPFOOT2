package com.projectwork.adp.adpfoot;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class game2 extends android.support.v4.app.Fragment {
Button ans;
RadioButton a;
RadioGroup radioGroup;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.game_test_fragment2,container,false);

        ans=view.findViewById(R.id.ans);
        radioGroup=view.findViewById(R.id.rg);



        final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.correct);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                 text = radioButton.getText().toString();
                }
        });


        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (text==null) {
    Toast.makeText(v.getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
    return;

}else {

                if (text.contains("Michael Owen")) {
                    if (mp != null ) {
                        if (!mp.isPlaying()) {
                            mp.start();

                            game3 newGamefragment = new game3();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, newGamefragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                        }
                    }
                }else {
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.wrong);

                    if (mp != null ) {
                        if (!mp.isPlaying()) {
                            mp.start();
                        }
                    }
                }

            }


            }
        });
        return view;
    }


}