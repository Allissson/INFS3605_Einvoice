package com.example.infs3605ess.ui.dashboard;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.infs3605ess.Description;
import com.example.infs3605ess.Invoice;
import com.example.infs3605ess.InvoiceView;
import com.example.infs3605ess.LanguageManager;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.UrgentPayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String TAG = "Dashboard Fragment";
    private DashboardViewModel dashboardViewModel;
    private RecyclerView mRecyclerView;
    private UrgentPayAdapter mAdapter;
    private DatabaseReference uDb;

    private List<Invoice> urgentInvoice = new ArrayList<>();
    private TextView noInvoiceHint,name;

    private ProgressBar progressBar;
    private LottieAnimationView lottieAnimationView,tick;
    private long duration=0;
    private LinearLayout lscan;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        progressBar=view.findViewById(R.id.progressBar2);
        noInvoiceHint=view.findViewById(R.id.noInvoiceHint);

        LanguageManager lang = new LanguageManager(this.getActivity());
        tick = view.findViewById(R.id.invoice_tick);

        lscan=view.findViewById(R.id.scan);



        // set up urgent pay recycler view
        mRecyclerView = view.findViewById(R.id.urgentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        UrgentPayAdapter.ClickListener listener = new UrgentPayAdapter.ClickListener() {
            @Override
            public void onUrgentPayClick(View view, int invoiceID) {
                System.out.println("on click");
                Invoice invoice = urgentInvoice.get(invoiceID);
                Intent intent = new Intent(view.getContext(), InvoiceView.class);
                intent.putExtra("View",  urgentInvoice.get(invoiceID));

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                String invoiceDate = sdf1.format(urgentInvoice.get(invoiceID).getInvoiceDate());
                String dueDate = sdf1.format(urgentInvoice.get(invoiceID).getDueDate());
                intent.putExtra("invoiceDate",invoiceDate);
                intent.putExtra("dueDate",dueDate);

                List<String> Name = new ArrayList<>();
                List<Integer> Quantity = new ArrayList<>();
                List<Double> Price = new ArrayList<>();
                List<Double> Total = new ArrayList<>();
                int i = urgentInvoice.get(invoiceID).getDescriptionList().size();
                for(int a = 0; a<i; a++){
                    Description d = new Description();
                    String name = urgentInvoice.get(invoiceID).getDescriptionList().get(a).getName();
                    int quantity = urgentInvoice.get(invoiceID).getDescriptionList().get(a).getQuantity();
                    double price = urgentInvoice.get(invoiceID).getDescriptionList().get(a).getPrice();
                    double total = urgentInvoice.get(invoiceID).getDescriptionList().get(a).getTotal();
                    Name.add(name);
                    Quantity.add(quantity);
                    Price.add(price);
                    Total.add(total);
                }
                intent.putExtra("NAME", (Serializable) Name);
                intent.putExtra("QUANTITY", (Serializable) Quantity);
                intent.putExtra("PRICE", (Serializable) Price);
                intent.putExtra("TOTAL", (Serializable) Total);
                startActivity(intent);
            }


        };
        Log.d(TAG,"before set adapter"+String.valueOf(urgentInvoice.isEmpty()));
        mAdapter = new UrgentPayAdapter(urgentInvoice, listener);
        Log.d(TAG,"Finish Adapter setup");
        mRecyclerView.setAdapter(mAdapter);

        // retrive data from firebase
        uDb= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice");
        uDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date date = new Date();
                System.out.println(date);

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Invoice invoice = snapshot1.getValue(Invoice.class);
                    System.out.println("/////"+invoice.getInvoiceNum()+invoice.getStatus()+invoice.getDueDate());
//                    Log.d(TAG,invoice.getStatus());
//                    Log.d(TAG,invoice.getDueDate().toString());
//                    System.out.println(String.valueOf(invoice.getDueDate().before(date))+ String.valueOf(invoice.getStatus().equals("unpaid")));
//                    // check overdue invoice
                    if(invoice.getStatus().equals("unpaid") && invoice.getDueDate().before(date)){
                        Log.d(TAG,"unpaid change");
                        uDb.child(invoice.getInvoiceNum()).child("status").setValue("Overdue");
                    }
                    // compare the time difference
                    long due = invoice.getDueDate().getTime();
                    long now = date.getTime();
                    int days = (int) ((now-due) / (1000 * 60 * 60 * 24));
                    System.out.println(invoice.getInvoiceNum()+invoice.getStatus().equals("unpaid"));
                    System.out.println(days);
                    // add urgent invoice to the list
//                    if(invoice.getStatus().equals("unpaid") && days<0 && days>=-5){
//                        urgentInvoice.add(invoice);
//                    }
                    if(invoice.getStatus().equals("unpaid") && days<-5){
                        urgentInvoice.add(invoice);
                    }
                    //urgentInvoice.add(invoice);

                }
                if(urgentInvoice.isEmpty()){
                    noInvoiceHint.setVisibility(View.VISIBLE);
                    tick.setVisibility(View.VISIBLE);
                    //tick.setAnimation(R.raw.tick);
                    tick.playAnimation();
                    mRecyclerView.setVisibility(View.INVISIBLE);

                }
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // scan function
        lscan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                                        startActivity(intent);
                                    }
                                });


        // set up animation
        lottieAnimationView =view.findViewById(R.id.lottieAnimationView2);
        lottieAnimationView.setAnimation(R.raw.dashboard_scan);
        lottieAnimationView.playAnimation();

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Log.d("Animation:","start");
                duration=animation.getDuration();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Log.d("Animation:","end");

                if(duration==2702){
                    lottieAnimationView.setAnimation(R.raw.tree);
                    lottieAnimationView.playAnimation();
                }
                else if(duration==3336){
                    lottieAnimationView.setAnimation(R.raw.dashboard_scan);
                    lottieAnimationView.playAnimation();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("Animation:","cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("Animation:","repeat");
            }
        });


        };
    }
