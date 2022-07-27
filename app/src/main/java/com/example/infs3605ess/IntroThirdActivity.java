package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class IntroThirdActivity extends AppCompatActivity {
    private ImageView back;
    private Button scan, vanata,backHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_third);
        back=findViewById(R.id.third_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtolast();
            }
        });
        scan=findViewById(R.id.scan);
        vanata=findViewById(R.id.vanata);
        backHome=findViewById(R.id.intro_back);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toScan();
            }
        });
        vanata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVanata();
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHome();
            }
        });
    }

    public void backtolast(){
        Intent intent = new Intent(this,IntroSecActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void toScan(){
        Intent intent = new Intent(this,ScanActivity.class);
        startActivity(intent);
    }
    public void openVanata(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://venatata.org/en/home-page/?gclid=EAIaIQobChMIscjH-9eW-QIVT6aWCh3IxgpTEAAYASAAEgIBIfD_BwE"));
        startActivity(intent);
    }
    public void toHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}