package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaySuccessActivity extends AppCompat {
    private Button bankSlip, emailShare, back;
    private TextView invoiceNumber;
    private String invoiceNo;
    private DatabaseReference uDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);


        invoiceNo=getIntent().getStringExtra("Invoice Number");
        uDb= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice").child(invoiceNo);
        uDb.child("status").setValue("Paid");
        invoiceNumber=findViewById(R.id.successInvoiceNum);

        back=findViewById(R.id.back);
        invoiceNumber.setText(getString(R.string.paysuccess)+":"+invoiceNo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumptoHome();
            }
        });

    }
    public void jumptoHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}