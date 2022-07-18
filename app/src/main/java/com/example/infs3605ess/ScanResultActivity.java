package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScanResultActivity extends AppCompatActivity {
    private String message;
    private TextView InvoiceNum, InvoiceDate, DueDate, Subtotal, ShipHand, GST, Total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        InvoiceNum=findViewById(R.id.InvoiceNum);
        InvoiceDate=findViewById(R.id.InvoiceDate);
        DueDate=findViewById(R.id.DueDate);
        Subtotal=findViewById(R.id.Subtotal);
        ShipHand=findViewById(R.id.ShipHand);
        GST=findViewById(R.id.GST);
        Total=findViewById(R.id.Total);

        message=getIntent().getStringExtra("output");

        //Get Scan result
        String ScanResult = message;

        //Get variables
        String invoicenumber = (ScanResult.substring(ScanResult.indexOf("Invoice number:") + 15)).substring(0, 7);
        String invoicedate = (ScanResult.substring(ScanResult.indexOf("Invoice date") + 13)).substring(0, 13);
        String duedate = (ScanResult.substring(ScanResult.indexOf("Due date:") + 10)).substring(0, 7);
        //String Description1
        //String Description2
        String subtotal = ScanResult.substring(ScanResult.indexOf("Subtotal ") + 9, ScanResult.indexOf("Shipping/handling"));
        String ShippingHandling = ScanResult.substring(ScanResult.indexOf("Shipping/handling ") + 18, ScanResult.indexOf("GST"));
        String gst = ScanResult.substring(ScanResult.indexOf("GST ") + 10, ScanResult.indexOf("Total"));
        String total = (ScanResult.substring(ScanResult.indexOf("Total ") + 6)).substring(0, 8);
        System.out.println(message);

        InvoiceNum.setText(invoicenumber);
        InvoiceDate.setText(invoicedate);
        DueDate.setText(duedate);
        Subtotal.setText(subtotal);
        ShipHand.setText(ShippingHandling);
        GST.setText(gst);
        Total.setText(total);


    }
}