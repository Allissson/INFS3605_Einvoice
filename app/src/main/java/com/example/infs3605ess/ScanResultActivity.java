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
        String From = ScanResult.substring(0, ScanResult.indexOf("INVOICE"));
        String[] FromSplit = From.split(", ");
        String s1 = FromSplit[0];
        String s2 = FromSplit[1];
        String s3 = FromSplit[2];
        String s4 = FromSplit[3];
        String s5 = FromSplit[4];

        //String To = ScanResult.substring(ScanResult.indexOf("Invoice To: ") + 12);
        //To = To.substring(0, To.indexOf(" Invoice No: "));


        String invoicenumber = (ScanResult.substring(ScanResult.indexOf("Invoice No:") + 12)).substring(0, 5);
        String invoicedate = (ScanResult.substring(ScanResult.indexOf("Date: ") + 6)).substring(0, 11);
        String duedate = (ScanResult.substring(ScanResult.indexOf("Due Date: ") + 10)).substring(0, 11);



        //思路如果是multiple description， 用if查一下$的数量。
        String DescriptionT = ScanResult.substring(ScanResult.indexOf("Amount ") + 7);
        DescriptionT = DescriptionT.substring(0, DescriptionT.indexOf(" Tax "));
        String[] DescriptionSplit = DescriptionT.split(" \\$");
        String Description1Name = DescriptionSplit[0];
        String Description1Price = DescriptionSplit[1];
        String Description1Amount = DescriptionSplit[2];

        String Tax =  ScanResult.substring(ScanResult.indexOf("Tax ") + 4);
        Tax = Tax.substring(0, Tax.indexOf(" Sub Total"));

        String SubTotal =  ScanResult.substring(ScanResult.indexOf("Sub Total ") + 10);
        SubTotal = SubTotal.substring(0, SubTotal.indexOf(" Shipping"));

        String ShippingHandling = ScanResult.substring(ScanResult.indexOf("Handling ") + 9);
        ShippingHandling = ShippingHandling.substring(0, ShippingHandling.indexOf(" Total Due "));

        String total = ScanResult.substring(ScanResult.indexOf("Total Due ") + 10);
        total = total.substring(0, total.indexOf(" Please make"));








        //String Description2
        //String subtotal = ScanResult.substring(ScanResult.indexOf("Subtotal ") + 9, ScanResult.indexOf("Shipping/handling"));
        //String ShippingHandling = ScanResult.substring(ScanResult.indexOf("Shipping/handling ") + 18, ScanResult.indexOf("GST"));



        System.out.println(message);

        InvoiceNum.setText(invoicenumber);
        InvoiceDate.setText(invoicedate);
        DueDate.setText(duedate);
        Subtotal.setText(From);
        ShipHand.setText(Description1Price);
        //GST.setText(gst);
        Total.setText(total);


    }
}