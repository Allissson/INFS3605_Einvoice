package com.example.infs3605ess.ui.invoices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.infs3605ess.Description;
import com.example.infs3605ess.DescriptionAdapter;
import com.example.infs3605ess.ForgotPasswordActivity;
import com.example.infs3605ess.Invoice;
import com.example.infs3605ess.InvoiceAdapter;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.ScanDescriptionModify;
import com.example.infs3605ess.ui.dashboard.DashboardViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
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
import java.util.Locale;

public class InvoicesFragment extends Fragment {

    private static final String TAG = "Invoices Fragment";
    private List<Invoice> Invoice = new ArrayList<>();
    private List<Invoice> mInvoice = new ArrayList<>();
    private InvoiceAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference uDb;
    private TabItem paid, unpaid, overdue;
    private TabLayout StatusTabs;
    private ProgressBar Progress;
    private LottieAnimationView tick;
    private TextView hint;

    private InvoicesViewModel invoicesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        invoicesViewModel =
//                new ViewModelProvider(this).get(InvoicesViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        return root;
        return inflater.inflate(R.layout.fragment_invoices, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        paid = view.findViewById(R.id.Paid);
        unpaid = view.findViewById(R.id.Unpaid);
        overdue = view.findViewById(R.id.Overdue);
        StatusTabs = view.findViewById(R.id.StatusTab);
        Progress = view.findViewById(R.id.progressBarInvoice);
        tick=view.findViewById(R.id.invoice_tick);
        hint = view.findViewById(R.id.invoice_hint);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        InvoiceAdapter.ClickListener listener = new InvoiceAdapter.ClickListener() {
            @Override
            public void onInvoiceClick(View view, int InvoiceID) {
                //Model model=(Model)adapter.getItem(position);
                Invoice invoice = mInvoice.get(InvoiceID);
                // Display details for the selected RecyclerView item (product on the list)

                Toast.makeText(getActivity().getApplicationContext(), invoice.getIssuer()+"\nPrice = $"+invoice.getTotal(), Toast.LENGTH_SHORT).show();
            }
        };
        mAdapter = new InvoiceAdapter( mInvoice, listener);
        mRecyclerView.setAdapter(mAdapter);

        uDb= FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Invoice");
        uDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Invoice invoice = snapshot1.getValue(Invoice.class);
                    Invoice.add(invoice);
                    mInvoice.clear();
                    for(int i=0; i < Invoice.size(); i++){
                        if(Invoice.get(i).getStatus().equals("Paid")){
                            mInvoice.add(Invoice.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                Progress.setVisibility(View.GONE);
                if(mInvoice.size()==0){
                    tick.setVisibility(View.VISIBLE);
                    tick.playAnimation();
                    hint.setText(R.string.invoice_list_hint_pay);
                    hint.setVisibility(View.VISIBLE);
                }
                else{
                    tick.setVisibility(View.INVISIBLE);
                    hint.setVisibility(View.INVISIBLE);
                }
                if(Invoice.size() == 0){
                    StatusTabs.setEnabled(false);
                    alter("No Invoice under user database!");
                    //Toast.makeText(getContext(), "No Invoice under user database!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                alter("Unable to pull data from database! Check network connection!");
                //Toast.makeText(getContext(), "Unable to pull data from database! Check network connection!", Toast.LENGTH_SHORT).show();
            }
        });


        StatusTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        mInvoice.clear();
                        for(int i=0; i < Invoice.size(); i++){
                            if(Invoice.get(i).getStatus().equals("Paid")){
                                mInvoice.add(Invoice.get(i));

                            }
                        }

                        if(mInvoice.size()==0){
                            tick.setVisibility(View.VISIBLE);
                            tick.playAnimation();
                            hint.setText(R.string.invoice_list_hint_pay);
                            hint.setVisibility(View.VISIBLE);
                        }
                        else{
                            tick.setVisibility(View.INVISIBLE);
                            hint.setVisibility(View.INVISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        mInvoice.clear();
                       for(int i = 0; i < Invoice.size(); i++){
                        if((Invoice.get(i)).getStatus().equals("unpaid")){
                                mInvoice.add(Invoice.get(i));
                                System.out.println(mInvoice.size());

                            }

                        }
                        if(mInvoice.size()==0){
                            tick.setVisibility(View.VISIBLE);
                            tick.playAnimation();
                            hint.setText(R.string.invoice_list_hint_unpaid);
                            hint.setVisibility(View.VISIBLE);
                        }
                        else{
                            hint.setVisibility(View.INVISIBLE);
                            tick.setVisibility(View.INVISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        mInvoice.clear();
                        System.out.println("Overdue before for"+Invoice.size());
                        for(int i=0; i < Invoice.size(); i++){
                            if(Invoice.get(i).getStatus().equals("Overdue") ){
                                mInvoice.add(Invoice.get(i));

                            }
                        }
                        if(mInvoice.size() == 0){
                            tick.setVisibility(View.VISIBLE);
                            tick.playAnimation();
                            hint.setText(R.string.invoice_list_hint_overdue);
                            hint.setVisibility(View.VISIBLE);
                        }
                        else{
                            hint.setVisibility(View.INVISIBLE);
                            tick.setVisibility(View.INVISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    // Hint Pop Up Window Method
    private void alter(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setTitle("Message")
                .setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}