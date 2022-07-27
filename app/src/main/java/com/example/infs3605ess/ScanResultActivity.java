package com.example.infs3605ess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ScanResultActivity extends AppCompat {
    private static final String TAG = "Scan Result Activity";
    private String message,DescriptionInfo;
    private TextView Issuer, Country, State, City, Street, InvoiceNum, InvoiceDate, DueDate, Subtotal, ShipHand, Total, Extra;
    private Button Description;
    private DatabaseReference uDb;
    private Date dInvoiceDate, dDueDate;
    private List<Description> mDescription = new ArrayList<>();
    private DescriptionAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Button testSave;
    private int bonus =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        Issuer=findViewById(R.id.Issuer);
        Country=findViewById(R.id.Country);
        State=findViewById(R.id.State);
        City=findViewById(R.id.City);
        Street=findViewById(R.id.Street);
        InvoiceNum=findViewById(R.id.InvoiceNum);
        InvoiceDate=findViewById(R.id.InvoiceDate);
        DueDate=findViewById(R.id.DueDate);
        Subtotal=findViewById(R.id.Subtotal);
        ShipHand=findViewById(R.id.ShipHand);
        Extra=findViewById(R.id.Extra);
        Total=findViewById(R.id.Total);
        Description=findViewById(R.id.Description);

        mRecyclerView = findViewById(R.id.recycleView);

        testSave=findViewById(R.id.testSave);



        message=getIntent().getStringExtra("output");

        //Get Scan result
        String ScanResult = message;


        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);



        DescriptionInfo = ScanResult.substring(ScanResult.indexOf("Amount ") + 7);
        DescriptionInfo = DescriptionInfo.substring(0, DescriptionInfo.indexOf(" Tax "));

        String[] messageSplit = DescriptionInfo.split(" ");
        int i = messageSplit.length;
        System.out.println(String.valueOf(i));

        for(int a=0;a<i;a=a+4){
            Description d = new Description();
            Log.d(TAG, messageSplit[a]);
            d.setName(messageSplit[a]);
            d.setQuantity(Integer.parseInt(messageSplit[a+1]));
            Log.d(TAG, messageSplit[a+1]);
            String price = messageSplit[a+2];
            String destotal = messageSplit[a+3];
            price = price.replace("$","");
            price = price.replace(",","");
            price = price.substring(0, price.length() - 3);


            destotal = destotal.replace("$","");
            destotal = destotal.replace(",","");
            destotal = destotal.substring(0, destotal.length() - 3);

            d.setTotal(Integer.parseInt(destotal));
            d.setPrice(Integer.parseInt(price));
            mDescription.add(d);
        }

        DescriptionAdapter.ClickListener listener = new DescriptionAdapter.ClickListener() {
            @Override
            public void onProductClick(View view, int DescriptionID) {
                //Model model=(Model)adapter.getItem(position);
                Description description = mDescription.get(DescriptionID);
                // Display details for the selected RecyclerView item (product on the list)
                /////Toast.makeText(getApplicationContext(), description.getName()+"\nPrice = $"+description.getPrice(), Toast.LENGTH_SHORT).show();
            }
        };

        mAdapter = new DescriptionAdapter(mDescription, listener);
        mRecyclerView.setAdapter(mAdapter);



        //Get variables

        //发方地址
        String FromInfo = ScanResult.substring(0, ScanResult.indexOf("INVOICE"));
        String[] FromSplit = FromInfo.split(", ");

        String country = FromSplit[FromSplit.length - 1];
        String state = FromSplit[FromSplit.length - 2];
        String city = FromSplit[FromSplit.length - 3];

        String StreetInfo = FromSplit[FromSplit.length - 4];
        String[] StreetSplit = StreetInfo.split(" ");
        String street = StreetSplit[StreetSplit.length - 3] + " " + StreetSplit[StreetSplit.length - 2] + " " + StreetSplit[StreetSplit.length - 1];

        //发方姓名
        String From = ScanResult.substring(0, ScanResult.indexOf(street));
        String[] FromEliminateSpace = From.split(" ");
        String Name = FromEliminateSpace[1] + " " + FromEliminateSpace[2];





        //Number, date, due date
        String invoicenumber = (ScanResult.substring(ScanResult.indexOf("com.example.infs3605ess.Invoice No:") + 12)).substring(0, 5);
        String invoicedate = (ScanResult.substring(ScanResult.indexOf("Date: ") + 6)).substring(0, 11);
        String duedate = (ScanResult.substring(ScanResult.indexOf("Due Date: ") + 10)).substring(0, 11);



        //思路如果是multiple description， 用if查一下$的数量。
        /*String DescriptionT = ScanResult.substring(ScanResult.indexOf("Amount ") + 7);
        DescriptionT = DescriptionT.substring(0, DescriptionT.indexOf(" Tax "));
        String[] DescriptionSplit = DescriptionT.split(" \\$");
        String Description1Name = DescriptionSplit[0];
        String Description1Price = DescriptionSplit[1];
        String Description1Amount = DescriptionSplit[2];
        */
        DescriptionInfo = ScanResult.substring(ScanResult.indexOf("Amount ") + 7);
        DescriptionInfo = DescriptionInfo.substring(0, DescriptionInfo.indexOf(" Tax "));
        System.out.println(DescriptionInfo);
        int count = DescriptionInfo.split("\\$",-1).length-1;
        String Test = String.valueOf(count);



        //Tax, subtotal, shipping & handling, Total Due
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

        Issuer.setText(Name);
        Country.setText(country);
        State.setText(state);
        City.setText(city);
        Street.setText(street);
        InvoiceNum.setText(invoicenumber);
        InvoiceDate.setText(invoicedate);
        DueDate.setText(duedate);
        Subtotal.setText(SubTotal);
        ShipHand.setText(ShippingHandling);
        Total.setText(total);
        Extra.setText(Test);

        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();

            }
        });

        // upload data to database

        uDb= FirebaseDatabase.getInstance().getReference().child("User");
        // change date from string to Date
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy");

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

        double dSub = convert(SubTotal);
        double dShip = convert(ShippingHandling);
        double dTotal = convert(total);
        double dExtra = convert(Test);

        // split description

        /*String[] messageSplit = DescriptionInfo.split(" ");
        int i = messageSplit.length;
        System.out.println(String.valueOf(i));


        for(int a=0;a<i;a=a+4){
            Description d = new Description();
            Log.d(TAG, messageSplit[a]);
            d.setName(messageSplit[a]);
            d.setQuantity(Integer.parseInt(messageSplit[a+1]));
            Log.d(TAG, messageSplit[a+1]);
            String price = messageSplit[a+2];
            String destotal = messageSplit[a+3];
            price = price.replace("$","");
            price = price.replace(",","");
            price = price.substring(0, price.length() - 3);


            destotal = destotal.replace("$","");
            destotal = destotal.replace(",","");
            destotal = destotal.substring(0, destotal.length() - 3);

            d.setTotal(Integer.parseInt(destotal));
            d.setPrice(Integer.parseInt(price));
            mDescription.add(d);
        }

         */

        Invoice invoice =new Invoice(Name,country,state,city,street,invoicenumber,dInvoiceDate,dDueDate,dSub,dShip,dTotal,dExtra,mDescription,"unpaid");
        uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice").child(invoicenumber).setValue(invoice);

        // Save Bonus
        //int bonus = Integer.parseInt(String.valueOf(uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bonus").get().getResult().getValue()));
        //System.out.println(bonus);

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
        testSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bonus").setValue(bonus+1);
            }
        });


    }

    private void switchActivities() {
        Intent i = new Intent(this, DescriptionActivity.class);
        i.putExtra("key",DescriptionInfo);
        startActivity(i);
    }

    private double convert (String number){
        number = number.replace("s","");
        number= number.replace("$","");
        number = number.replace(",","");
        double resNum = Double.parseDouble(number);
        return resNum;
    }



}