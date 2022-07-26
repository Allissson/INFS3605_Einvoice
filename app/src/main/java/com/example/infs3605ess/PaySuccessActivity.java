package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        bankSlip = findViewById(R.id.bankSlipUpload);
        emailShare=findViewById(R.id.emailShare);
        back=findViewById(R.id.back);
        invoiceNumber.setText(invoiceNo);

    }
}