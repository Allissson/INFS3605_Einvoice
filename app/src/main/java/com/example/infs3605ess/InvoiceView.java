package com.example.infs3605ess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceView extends AppCompat implements Serializable {
    private Invoice myInvoice;
    private TextView InvoiceNo, InvoiceDate, Issuer, Address, PriceTotal, DueDate, Tax, SubTotal, ShHan, Total;
    private Button Pay;
    private RecyclerView mRecyclerView;
    private List<Description> mDescription;
    private DescriptionAdapter mAdapter;
    private ScrollView view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);

        myInvoice = getIntent().getParcelableExtra("View");
        //mDescription = getIntent().getParcelableExtra("Description");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        List<Description> description = (List<Description>) args.getSerializable("ARRAYLIST");


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

        Pay.setVisibility(View.GONE);


        if(myInvoice.getState().equals("Paid")){
            view.setBackgroundResource(R.drawable.listview_bg_green);
        }else if(myInvoice.getState().equals("unpaid")){
            view.setBackgroundResource(R.drawable.listview_bg_blue);
            Pay.setVisibility(View.VISIBLE);
        }

        Address.setText(myInvoice.getStreet() + "\n" + myInvoice.getCity() + "\n" + myInvoice.getState() +", " + myInvoice.getCountry());
        InvoiceNo.setText(myInvoice.getInvoiceNum());
        InvoiceDate.setText((CharSequence) myInvoice.getInvoiceDate());
        //InvoiceDate.setText(String.valueOf(Date.parse(String.valueOf(myInvoice.getInvoiceDate()))));
        Issuer.setText(myInvoice.getIssuer());
        //DueDate.setText(String.valueOf(Date.parse(String.valueOf(myInvoice.getDueDate()))));
        Tax.setText(String.valueOf(myInvoice.getExtra()));
        SubTotal.setText(String.valueOf(myInvoice.getSubTotal()));
        ShHan.setText(String.valueOf(myInvoice.getShipHand()));
        Total.setText(String.valueOf(myInvoice.getTotal()));

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        /*
        int i = myInvoice.getDescriptionList().size();
        for(int a=0; a<i; a++){
            mDescription.add(myInvoice.getDescriptionList().get(a));
        }
*/
        int i = description.size();
        for(int a = 0; a<i; a++){
            Description d = new Description();
            String name = description.get(a).getName();
            int quantity = description.get(a).getQuantity();
            double price = description.get(a).getPrice();
            double total = description.get(a).getTotal();
            d.setName(name);
            d.setQuantity(quantity);
            d.setPrice(price);
            d.setTotal(total);
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
