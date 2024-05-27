package com.example.footyinsight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signin_email, signin_password;
    private Button signin_button;
    private TextView signUpBtn, guestModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        signin_email = findViewById(R.id.signin_email);
        signin_password = findViewById(R.id.signin_password);
        signin_button = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        guestModeBtn = findViewById(R.id.guestModeBtn);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signin_email.getText().toString();
                String pass = signin_password.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(SignIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignIn.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignIn.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        signin_password.setError("Password can't be empty");
                    }
                } else if (email.isEmpty()) {
                    signin_email.setError("Email can't be empty");
                } else {
                    signin_email.setError("Please enter valid email");
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        guestModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, MainActivity.class));
                finish();
            }
        });
    }
}
