package com.example.infs3605ess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompat {
    private static final String TAG = "Register Activity";
    private FirebaseAuth mAuth;
    private EditText companyNameText, userNameText, emailText,passwordText;
    private Button register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        companyNameText = findViewById(R.id.companyName);
        userNameText=findViewById(R.id.username);
        emailText=findViewById(R.id.email);
        passwordText=findViewById(R.id.forgot_email);
        register=findViewById(R.id.registerBtt);
        progressBar=findViewById(R.id.regProgressBar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        String email  = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String userName = userNameText.getText().toString().trim();
        String companyName = companyNameText.getText().toString().trim();
        if(companyName.isEmpty()){
            companyNameText.setError("Company Name is Required !");
            companyNameText.requestFocus();
            return;
        }
        if(userName.isEmpty()){
            userNameText.setError("User Name is Required !");
            userNameText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Please Provide Valid Email Address !");
            emailText.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailText.setError("Email is Required !");
            emailText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordText.setError("Password is Required !");
            passwordText.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordText.setError("Min Password length should be 6 characters! ");
            passwordText.requestFocus();
            return;
        }
        progressBar = findViewById(R.id.regProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(companyName,userName,email,0);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "onComplete: user created successful");
                                        progressBar.setVisibility(View.GONE);
                                        alter("User had registered successfully!");
                                    }
                                    else{
                                        Log.d(TAG, "onComplete: incomplete");
                                        progressBar.setVisibility(View.GONE);
                                        alterUnsuccessful("Failed to Register, try again!");

                                    }
                                }
                            });
                        }else{
                            Log.d(TAG, "onComplete: incomplete");
                            progressBar.setVisibility(View.GONE);
                            alterUnsuccessful("Failed to Register, try again!");

                        }
                    }
                });



    }
    // Create Successful Hint Dialog
    private void alter(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).setTitle("Message")
                .setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        back();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void alterUnsuccessful(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).setTitle("Message")
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

    private void back(){
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
    }
}