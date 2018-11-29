package com.utnproject.myflight.login.view;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utnproject.myflight.R;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_create_account),true);

        btnJoinUs    = findViewById(R.id.register);
        edtEmail     = findViewById(R.id.email_reg);
        edtPassword  = findViewById(R.id.password_reg);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG,"User logged"+firebaseUser.getEmail());
                }
                else{
                    Log.w(TAG,"User not logged");

                }
            }
        };

        btnJoinUs.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                createAccount();
            }
        });




    }

    private void createAccount() {

        String email    =  edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this,
                                    "Account created successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CreateAccountActivity.this,
                                    "Error creating account",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void showToolbar(String tittle, boolean upButton){
         Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle(tittle);
         getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);

    }

}
