package com.example.infs3605ess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceView extends AppCompat implements Serializable {
    private static final String TAG ="Invoice View" ;
    private Invoice myInvoice;
    private TextView InvoiceNo, InvoiceDate, Issuer, Address, PriceTotal, DueDate, Tax, SubTotal, ShHan, Total, Title1, Title2;
    private Button Pay;
    private RecyclerView mRecyclerView;
    private List<Description> mDescription = new ArrayList<>();
    List<String> NAME = new ArrayList<>();
    List<Integer> QUANTITY = new ArrayList<>();
    List<Double> PRICE = new ArrayList<>();
    List<Double> TOTAL = new ArrayList<>();
    private DescriptionAdapter mAdapter;
    private ScrollView view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);

        //Find XML objects
        InvoiceNo = findViewById(R.id.tv_no1);
        InvoiceDate = findViewById(R.id.tv_date1);
        Issuer = findViewById(R.id.tv_name1);
        Address = findViewById(R.id.tv_address1);
        PriceTotal = findViewById(R.id.tv_total1);
        DueDate = findViewById(R.id.tv_due_date1);
        Tax = findViewById(R.id.tv_tax1);
        SubTotal = findViewById(R.id.tv_subtotal1);
        ShHan = findViewById(R.id.tv_shiphand1);
        Total = findViewById(R.id.tv_total_due1);
        Pay = findViewById(R.id.btn_payment);
        mRecyclerView = findViewById(R.id.recyclerview1);
        view = findViewById(R.id.Scroll);
        Title1 = findViewById(R.id.tv_title);
        Title2 = findViewById(R.id.tv_content);

        //Get intent data from InvoicesFragment
        myInvoice = getIntent().getParcelableExtra("View");
        Intent i = getIntent();
        NAME = (List<String>) i.getSerializableExtra("NAME");
        QUANTITY = (List<Integer>) i.getSerializableExtra("QUANTITY");
        PRICE = (List<Double>) i.getSerializableExtra("PRICE");
        TOTAL = (List<Double>) i.getSerializableExtra("TOTAL");

        //Set background color
        if(myInvoice.getStatus().equals("Paid")){
            view.setBackgroundResource(R.drawable.listview_bg_green);
        }else if(myInvoice.getStatus().equals("unpaid")){
            view.setBackgroundResource(R.drawable.listview_bg_blue);
            Pay.setVisibility(View.VISIBLE);
        }

        //Set title and content
        if(myInvoice.getStatus().equals("Paid")){
            Title1.setText(R.string.invoice_view_Title_paid);
            Title2.setText(R.string.invoice_view_Content_paid);
        }else if(myInvoice.getStatus().equals("unpaid")){
            Title1.setText(R.string.invoice_view_Title_unpaid);
            Title2.setText(R.string.invoice_view_Content_unpaid);
        }else{
            Title1.setText(R.string.invoice_view_Title_overdue);
            Title2.setText(R.string.invoice_view_Content_overdue);
        }

        Address.setText(myInvoice.getStreet() + "\n" + myInvoice.getCity() + "\n" + myInvoice.getState() +", " + myInvoice.getCountry());
        InvoiceNo.setText(myInvoice.getInvoiceNum());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println(myInvoice.getDueDate());
        

        String invoiceDate = getIntent().getStringExtra("invoiceDate");
        String dueDate = getIntent().getStringExtra("dueDate");
        DueDate.setText(dueDate);
        InvoiceDate.setText(invoiceDate);
        Issuer.setText(myInvoice.getIssuer());
        Tax.setText("$" + String.valueOf(myInvoice.getExtra()));
        SubTotal.setText("$" + String.valueOf(myInvoice.getSubTotal()));
        ShHan.setText("$" + String.valueOf(myInvoice.getShipHand()));
        Total.setText("$" + String.valueOf(myInvoice.getTotal()));

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        int A = NAME.size();
        Double sum = 0.0;
        for(int a = 0; a<A; a++){
        Description d = new Description();
            String name = NAME.get(a);
            int quantity = QUANTITY.get(a);
            double price = PRICE.get(a);
            double total = TOTAL.get(a);
            d.setName(name);
            d.setQuantity(quantity);
            d.setPrice(price);
            d.setTotal(total);
            sum += total;
            mDescription.add(d);
        }
        PriceTotal.setText(String.valueOf(sum));


        DescriptionAdapter.ClickListener listener = new DescriptionAdapter.ClickListener() {
            @Override
            public void onProductClick(View view, int DescriptionID) {
                Description description = mDescription.get(DescriptionID);
            }
        };



        mAdapter = new DescriptionAdapter(mDescription, listener);
        mRecyclerView.setAdapter(mAdapter);

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PaySuccessActivity.class);
                i.putExtra("Invoice Number", myInvoice.getInvoiceNum());
                startActivity(i);
            }
        });







    }
}
