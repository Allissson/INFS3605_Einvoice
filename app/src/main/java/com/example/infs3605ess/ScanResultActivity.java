package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScanResultActivity extends AppCompatActivity {
    private String message;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        message=getIntent().getStringExtra("output");
        System.out.println(message);
        result=findViewById(R.id.result);
        result.setText(message);


    }
}