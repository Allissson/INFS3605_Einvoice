package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaveResultActivity extends AppCompatActivity {
    Button next,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_result);
        next=findViewById(R.id.upload_save);
        back=findViewById(R.id.upload_back);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               toScan();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void toScan(){
        Intent intent =  new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    public void toHome(){
        Intent intent =  new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}