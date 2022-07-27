package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroSecActivity extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView;
    private long duration=0;
    private ImageView next,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_sec);
        lottieAnimationView=findViewById(R.id.anim_second);

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("Animation:","start");
                duration=animation.getDuration();
                System.out.println(duration);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("Animation:","end");

                if(duration==9999){
                    lottieAnimationView.setAnimation(R.raw.tree);
                    lottieAnimationView.playAnimation();
                }
                else if(duration==3336){
                    lottieAnimationView.setAnimation(R.raw.intro_invoice);
                    lottieAnimationView.playAnimation();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("Animation:","cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("Animation:","repeat");
            }
        });

        back=findViewById(R.id.second_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtolast();
            }
        });
        next=findViewById(R.id.second_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveNext();
            }
        });


    };
    public void backtolast(){
        Intent intent = new Intent(this,IntroFirstActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void moveNext(){
        Intent intent = new Intent(this,IntroThirdActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    }
