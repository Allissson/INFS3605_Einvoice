package com.example.infs3605ess.ui.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605ess.Description;
import com.example.infs3605ess.DescriptionAdapter;
import com.example.infs3605ess.Invoice;
import com.example.infs3605ess.InvoiceAdapter;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ui.dashboard.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoicesFragment extends Fragment {

    private static final String TAG = "Invoices Fragment";
    private List<Invoice> mInvoice = new ArrayList<>();
    private InvoiceAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference uDb;

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
                    mInvoice.add(invoice);
                    System.out.println("TestP: " + mInvoice.size());
                }
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        System.out.println("Test: " + mInvoice.size());

    }
}