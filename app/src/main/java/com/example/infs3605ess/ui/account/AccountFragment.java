package com.example.infs3605ess.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.User;
import com.example.infs3605ess.ui.dashboard.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    private static final String TAG ="Account Fragment" ;
    private AccountViewModel accountViewModel;
    private TextView message;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDb;
    private String userId,userName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        accountViewModel =
//                new ViewModelProvider(this).get(AccountViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myaccount, container, false);
//        return root;
    }
    // Didn't Finish
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mFirebaseAuth=FirebaseAuth.getInstance();

        mDb = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "mDb connection");
        message=view.findViewById(R.id.account_text);
        userId=mFirebaseAuth.getCurrentUser().getUid();
        Log.d(TAG, userId);


        mDb.child("User").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userName = user.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        message.setText(userName);
    };
}