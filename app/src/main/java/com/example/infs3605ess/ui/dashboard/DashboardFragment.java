package com.example.infs3605ess.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605ess.Invoice;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.UrgentPayAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button scan;

    private RecyclerView mRecyclerView;
    private UrgentPayAdapter mAdapter;

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
        // scan function
        scan=view.findViewById(R.id.btt_scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
                // set up urgent pay recycler view
//                mRecyclerView  =  view.findViewById(R.id.urgentRecyclerView);
//                mRecyclerView.setHasFixedSize(true);
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                ArrayList<Invoice> urgentPay = null;
//                UrgentPayAdapter.RecyclerViewClickListener mListener = new UrgentPayAdapter.RecyclerViewClickListener() {
//                    @Override
//                    public void onClick(View view, Invoice invoice) {
//
//                    }
//                };
//                mAdapter = new UrgentPayAdapter(getActivity(),urgentPay ,mListener);
//                mRecyclerView.setAdapter(mAdapter);


            }
        });
    }
}