package com.example.infs3605ess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPassword Activity";
    private Button reset;
    private EditText foemail;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        reset=findViewById(R.id.resetPassword);
        foemail=findViewById(R.id.forgot_email);
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.forgot_progress);
        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = foemail.getText().toString().trim();
                if (email.isEmpty()) {
                    foemail.setError("Enter your registered Email!");
                    foemail.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Password had been sent");
                            progressBar.setVisibility(View.GONE);
                            alter("Reset Password Link had been sent to your email, please check !");

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            progressBar.setVisibility(View.GONE);
                            alter("Invalid email, please check if you had already sign up with this email !");
                        }
                    }
                });
            }
        });
    }


    // Hint Pop Up Window Method
    private void alter(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).setTitle("Message")
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