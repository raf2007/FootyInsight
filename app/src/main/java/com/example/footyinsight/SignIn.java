package com.example.footyinsight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signin_email, signin_password;
    private Button signin_button;
    private TextView dont_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        auth= FirebaseAuth.getInstance();
        signin_email=findViewById(R.id.signin_email);
        signin_password=findViewById(R.id.signin_password);
        signin_button=findViewById(R.id.signin_button);
        dont_have_account=findViewById(R.id.dont_have_account);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signin_email.getText().toString();
                String pass = signin_password.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!pass.isEmpty()){
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
                    }
                    else{
                        signin_password.setError("Password can't be empty");
                    }
                } else if (email.isEmpty()) {
                    signin_email.setError("Email can't be empty");
                }
                else {
                    signin_email.setError("Please enter valid email");
                }
            }
        });

        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }
}