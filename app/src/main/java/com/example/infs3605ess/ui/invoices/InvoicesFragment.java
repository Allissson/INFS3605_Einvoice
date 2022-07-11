package com.example.infs3605ess.ui.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.infs3605ess.R;
import com.example.infs3605ess.ui.dashboard.DashboardViewModel;

public class InvoicesFragment extends Fragment {

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

    }
}