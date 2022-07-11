package com.example.infs3605ess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG ="Main activity" ;
    private FirebaseAuth mAuth;
    private EditText emailText, passwordText;
    private ProgressBar progressBar;
    private Button loginBtt, register,forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailText=findViewById(R.id.email);
        passwordText=findViewById(R.id.passsword);
        progressBar=findViewById(R.id.progressBar);
        loginBtt=findViewById(R.id.logIn);
        register=findViewById(R.id.register);
        forget=findViewById(R.id.forgetPass);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forget.setPaintFlags(forget.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toForget();
            }
        });
        loginBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();
            }
        });


    }
    private void toRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    private void launchDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    private void toForget(){
        Intent intent = new Intent(this,ForgotPasswordActivity.class);
        startActivity(intent);
    }



    private void loginUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please Provide Valid Email Address !");
            emailText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailText.setError("Email is Required !");
            emailText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordText.setError("Password is Required !");
            passwordText.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Successfully log in");
                    progressBar.setVisibility(View.GONE);
                    launchDashboard();
                } else {
                    Log.d(TAG, "Log in unsuccessful");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    progressBar.setVisibility(View.GONE);
                    alter("Incorrect password, try again!");
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    progressBar.setVisibility(View.GONE);
                    alter("Invalid email, try again!");
                }

            }
        });



    }
    // Hint Pop Up Window Method
    private void alter(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Message")
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