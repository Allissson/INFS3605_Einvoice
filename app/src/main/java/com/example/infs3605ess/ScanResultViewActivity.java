package com.example.infs3605ess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScanResultViewActivity extends  AppCompat{
    private static final String TAG = "ScanResultViewActivity";
    private TextView InvoiceNo, Date, Issuer, Address, PriceTotal, DueDate, Tax, SubTotal, ShHan, Total;
    private Button Save, Edit;
    private String message,DescriptionInfo;
    private List<Description> mDescription = new ArrayList<>();
    private DescriptionAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference uDb;
    private int bonus =0;
    private java.util.Date dInvoiceDate, dDueDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result_view);
        InvoiceNo = findViewById(R.id.tv_no);
        Date = findViewById(R.id.tv_date);
        Issuer = findViewById(R.id.tv_name);
        Address = findViewById(R.id.tv_address);
        PriceTotal = findViewById(R.id.tv_total);
        DueDate = findViewById(R.id.tv_due_date);
        Tax = findViewById(R.id.tv_tax);
        SubTotal = findViewById(R.id.tv_subtotal);
        ShHan = findViewById(R.id.tv_shiphand);
        Total = findViewById(R.id.tv_total_due);
        Save = findViewById(R.id.btn_save);
        Edit = findViewById(R.id.btn_modify_save);
        mRecyclerView = findViewById(R.id.recyclerview);

        message = getIntent().getStringExtra("output");
        String ScanResult = message;

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScanResultActivity.class);
                intent.putExtra("Edit", ScanResult);
                startActivity(intent);
            }
        });

        //Recycler View
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        DescriptionInfo = ScanResult.substring(ScanResult.indexOf("Amount ") + 7);
        DescriptionInfo = DescriptionInfo.substring(0, DescriptionInfo.indexOf(" Tax "));

        String[] messageSplit = DescriptionInfo.split(" ");
        int i = messageSplit.length;
        System.out.println(String.valueOf(i));

        for (int a = 0; a < i; a = a + 4) {
            Description d = new Description();
            Log.d(TAG, messageSplit[a]);
            d.setName(messageSplit[a]);
            d.setQuantity(Integer.parseInt(messageSplit[a + 1]));
            Log.d(TAG, messageSplit[a + 1]);
            String price = messageSplit[a + 2];
            String destotal = messageSplit[a + 3];
            price = price.replace("$", "");
            price = price.replace(",", "");
            price = price.substring(0, price.length() - 3);
            destotal = destotal.replace("$", "");
            destotal = destotal.replace(",", "");
            destotal = destotal.substring(0, destotal.length() - 3);
            d.setTotal(Double.parseDouble(destotal));
            d.setPrice(Double.parseDouble(price));
            mDescription.add(d);
        }

        DescriptionAdapter.ClickListener listener = new DescriptionAdapter.ClickListener() {
            @Override
            public void onProductClick(View view, int DescriptionID) {
                Description description = mDescription.get(DescriptionID);
            }
        };

        mAdapter = new DescriptionAdapter(mDescription, listener);
        mRecyclerView.setAdapter(mAdapter);

        int count = DescriptionInfo.split("\\$", -1).length - 1;
        int items = count/2;
        double pricetotal = 0;
        for (int b=0; b < items; b++){
            pricetotal += mDescription.get(b).getTotal();
            PriceTotal.setText(String.valueOf(pricetotal));
        }

        //Address
        String FromInfo = ScanResult.substring(0, ScanResult.indexOf("INVOICE"));
        String[] FromSplit = FromInfo.split(", ");
        String country = FromSplit[FromSplit.length - 1];
        String state = FromSplit[FromSplit.length - 2];
        String city = FromSplit[FromSplit.length - 3];
        String StreetInfo = FromSplit[FromSplit.length - 4];
        String[] StreetSplit = StreetInfo.split(" ");
        String street = StreetSplit[StreetSplit.length - 3] + " " + StreetSplit[StreetSplit.length - 2] + " " + StreetSplit[StreetSplit.length - 1];

        Address.setText(street + "\n" + city + "\n" + state +", " + country);

        //????????????
        String From = ScanResult.substring(0, ScanResult.indexOf(street));
        String[] FromEliminateSpace = From.split(" ");
        String Name = FromEliminateSpace[1] + " " + FromEliminateSpace[2];

        Issuer.setText(Name);

        //Number, date, due date
        String invoicenumber = (ScanResult.substring(ScanResult.indexOf("Invoice No: ") + 12)).substring(0, 5);
        String invoicedate = (ScanResult.substring(ScanResult.indexOf("Date: ") + 6)).substring(0, 11);
        String duedate = (ScanResult.substring(ScanResult.indexOf("Due Date: ") + 10)).substring(0, 11);

        InvoiceNo.setText(invoicenumber);
        Date.setText(invoicedate);
        DueDate.setText(duedate);

        //Tax, Subtotal, ShipHand, Total
        String tax = ScanResult.substring(ScanResult.indexOf("Tax ") + 4);
        tax = tax.substring(0, tax.indexOf(" Sub Total"));
        tax = tax.replace("$","");
        String subtotal = ScanResult.substring(ScanResult.indexOf("Sub Total ") + 10);
        subtotal = subtotal.substring(0, subtotal.indexOf(" Shipping"));
        subtotal = subtotal.replace("$","");
        String shippinghandling = ScanResult.substring(ScanResult.indexOf("Handling ") + 9);
        shippinghandling = shippinghandling.substring(0, shippinghandling.indexOf(" Total Due "));
        shippinghandling = shippinghandling.replace("$","");
        String total = ScanResult.substring(ScanResult.indexOf("Total Due ") + 10);
        total = total.substring(0, total.indexOf(" Please make"));
        total = total.replace("$","");

        Tax.setText(tax);
        SubTotal.setText(subtotal);
        ShHan.setText(shippinghandling);
        Total.setText(total);

        //Upload data to database
        uDb = FirebaseDatabase.getInstance().getReference().child("User");
        //Change date from string to Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        try {
            dInvoiceDate = formatter.parse(invoicedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dDueDate = formatter.parse(duedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Retrieve data from Invoice
        double dSub = convert(subtotal);
        double dShip = convert(shippinghandling);
        double dTotal = convert(total);
        double dExtra = convert(tax);
        String dName = Name;
        String dCountry = country;
        String dState = state;
        String dCity = city;
        String dStreet = street;
        String dInvoiceNumber = invoicenumber;

        //Set value to firebase
        Invoice invoice =new Invoice(dName,dCountry,dState,dCity,dStreet,dInvoiceNumber,dInvoiceDate,dDueDate,dSub,dShip,dTotal,dExtra,mDescription,"unpaid");
        uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice").child(invoicenumber).setValue(invoice);
        uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bonus").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    String sbonus=String.valueOf(task.getResult().getValue());
                    System.out.println(sbonus.length());
                    //bonus = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    if(sbonus.equals("null")){
                        bonus = 0;
                    }
                    else{
                        bonus = Integer.parseInt(sbonus);
                    }
                }
            }
        });

        //OnClick Listener to upload invoice to firebase
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bonus").setValue(bonus+1);
            }
        });
    }

    //Convert Method
    private double convert (String number){
        number = number.replace("s","");
        number= number.replace("$","");
        number = number.replace(",","");
        double resNum = Double.parseDouble(number);
        return resNum;
    }

}
