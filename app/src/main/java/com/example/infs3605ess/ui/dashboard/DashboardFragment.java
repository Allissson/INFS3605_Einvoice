package com.example.infs3605ess.ui.dashboard;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.infs3605ess.AppCompat;
import com.example.infs3605ess.Description;
import com.example.infs3605ess.Invoice;
import com.example.infs3605ess.LanguageManager;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.UrgentPayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String TAG = "Dashboard Fragment";
    private DashboardViewModel dashboardViewModel;
    private Button scan, zh, en,close;


    private RecyclerView mRecyclerView;
    private UrgentPayAdapter mAdapter;
    private DatabaseReference uDb;
    private ArrayList<Invoice> urgentInvoice = new ArrayList<Invoice>();
    private TextView noInvoiceHint,name;
    private ProgressBar progressBar;
    private LottieAnimationView lottieAnimationView,tick;
    private long duration=0;
    private String userName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        progressBar=view.findViewById(R.id.progressBar2);
        noInvoiceHint=view.findViewById(R.id.noInvoiceHint);

        LanguageManager lang = new LanguageManager(this.getActivity());
        tick = view.findViewById(R.id.dashboard_tick);


        // set up urgent pay recycler view
        // Log.d(TAG,String.valueOf(urgentInvoice.isEmpty()));
        mRecyclerView = view.findViewById(R.id.urgentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        UrgentPayAdapter.RecyclerViewClickListener mListener = new UrgentPayAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, Invoice invoice) {

            }
        };
        Log.d(TAG,"before set adapter"+String.valueOf(urgentInvoice.isEmpty()));
        mAdapter = new UrgentPayAdapter(getActivity(),urgentInvoice ,mListener);
        Log.d(TAG,"Finish Adapter setup");
        mRecyclerView.setAdapter(mAdapter);

        // retrive data from firebase
        uDb= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice");
        uDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date date = new Date();
                System.out.println(date);

//                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
//                String current = dateFormat.format(date);
//                System.out.println(date);

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Invoice invoice = snapshot1.getValue(Invoice.class);
                    System.out.println(invoice.getDueDate());
//                    Log.d(TAG,invoice.getStatus());
//                    Log.d(TAG,invoice.getDueDate().toString());
//                    System.out.println(String.valueOf(invoice.getDueDate().before(date))+ String.valueOf(invoice.getStatus().equals("unpaid")));
//                    // check overdue invoice
                    if(invoice.getStatus().equals("unpaid") && invoice.getDueDate().after(date)){
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
                    if(invoice.getStatus().equals("unpaid") && days<5){
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
        //name=view.findViewById(R.id.dashboard_name);
//        uDb = FirebaseDatabase.getInstance().getReference();
//        uDb.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                } else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                    userName = String.valueOf(task.getResult().getValue());
//                    name.setText(userName);
//                }
//            }
//        });

        // scan function
        scan=view.findViewById(R.id.btt_scan);
        scan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                                        startActivity(intent);
                                    }
                                });



//        // test recycler view
//        Date dInvoiceDate = null;
//        SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy");
//        try {
//            dInvoiceDate = formatter.parse("12-May-2022");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        List<Description> des = null;
//
//        Invoice test = new Invoice("123","123","123","123","123","123",dInvoiceDate,dInvoiceDate,123.1,123.1,123.123,123.1,des,"Unpaid");
//        urgentInvoice.add(test);

        // animation
        lottieAnimationView =view.findViewById(R.id.lottieAnimationView2);
        lottieAnimationView.setAnimation(R.raw.dashboard_scan);
        lottieAnimationView.playAnimation();

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("Animation:","start");
                duration=animation.getDuration();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("Animation:","end");

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

//    private void startAnimation(){
//        lottieAnimationView =view.f
//    }
//    func startAnimation1() {
//        animationLottieView.animation = Animation.named("Slidetwop1")
//        animationLottieView.play { (finished) in
//            // completion handler
//            self.animationLottieView.animation = Animation.named("Slidetwop2")
//            self.animationLottieView.play { (finishedAnimation) in
//                self.startAnimation()}
//        }
//    }
    }
