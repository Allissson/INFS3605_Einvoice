package com.example.infs3605ess.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infs3605ess.MainActivity;
import com.example.infs3605ess.R;
import com.example.infs3605ess.ScanActivity;
import com.example.infs3605ess.User;
import com.example.infs3605ess.ui.dashboard.DashboardViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private TextView message,points;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDb;
    private String userId,userName;
    private Button logout;
    private int progr = 0;
    private int bonus=0;
    private ProgressBar progressBar;

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
        logout=view.findViewById(R.id.btt_logout);
        mDb = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "mDb connection");
        message=view.findViewById(R.id.account_text);
        userId=mFirebaseAuth.getCurrentUser().getUid();
        Log.d(TAG, userId);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        mDb.child("User").child(userId).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //User user = snapshot.getValue(User.class);
                userName = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        mDb.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    userName=String.valueOf(task.getResult().getValue());
                    message.setText(userName);
                }
            }
        });




        // progress show

        mDb.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bonus").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
        progr=bonus/200;
        System.out.println(progr);
        progressBar=view.findViewById(R.id.id_progress);
        progressBar.setProgress(progr);

        // points show
        points=view.findViewById(R.id.points);
        points.setText(bonus+"/200");
    };
    private void logout(){
        mFirebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}