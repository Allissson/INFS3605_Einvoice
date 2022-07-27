package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroFirstActivity extends AppCompatActivity {
    private ImageView next;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_first);
        next=findViewById(R.id.first_next);
        lottieAnimationView = findViewById(R.id.lottietree);
        //lottieAnimationView.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               transition();
            }
        });




    }
    public void transition(){
        Intent intent = new Intent(this,IntroSecActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}