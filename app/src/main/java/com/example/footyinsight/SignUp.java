/*package com.example.footyinsight;

import android.app.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    public String Password, EmailAddress;
    EditText username, email_address, register_password, confirm_password;
    Button register_button;
    FirebaseAuth m_auth;
    TextView back_to_login_text;
    FirebaseFirestore f_store;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.fullName);
        email_address = findViewById(R.id.email);
        register_password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signupConfirmPassword);
        m_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();
        back_to_login_text = findViewById(R.id.signInBtn);
        register_button = findViewById(R.id.signUpBtn);

        register_button.setOnClickListener(v -> checkCredentials());

        if (m_auth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
        }

        back_to_login_text.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });
    }

    private void checkCredentials() {
        String checkUsername = username.getText().toString().trim();
        String checkEmailAddress = email_address.getText().toString().trim();
        String checkPassword = register_password.getText().toString();
        String checkConfirmedPassword = confirm_password.getText().toString().trim();

        boolean isValid = true;

        if (checkUsername.isEmpty()) {
            showError(username, "Please enter your username");
            isValid = false;
        }
        else if (checkUsername.contains("-") ||
                checkUsername.contains("*") || checkUsername.contains("&") ||
                checkUsername.contains("#") || checkUsername.contains("%") ||
                checkUsername.contains("@") || checkUsername.contains("$") ||
                checkUsername.contains("|") || checkUsername.contains("/") ||
                checkUsername.contains("^") || checkUsername.contains("?") ||
                checkUsername.contains("(") || checkUsername.contains(")") ||
                checkUsername.contains("+") || checkUsername.contains("=") ||
                checkUsername.contains("<") || checkUsername.contains(">") ||
                checkUsername.contains(",") || checkUsername.contains("'")) {
            showError(username, "Your username can contain only specific character ( _ )");
            isValid = false;
        }
        else if (checkUsername.contains(" ")) {
            showError(username, "Your username should not contain spaces");
            isValid = false;
        }
        else if (checkUsername.length() < 7 || checkUsername.length() > 40) {
            showError(username, "Your username length range is from 5 characters to 23");
            isValid = false;
        }
        else if (checkEmailAddress.isEmpty()) {
            showError(email_address, "Please enter your email.");
            isValid = false;
        }
        else if (!checkEmailAddress.contains("@") && !checkEmailAddress.contains(".") || !checkEmailAddress.contains("@") || !checkEmailAddress.contains(".")) {
            showError(email_address, "Please enter a valid email address");
            isValid = false;
        }
        else if (checkPassword.isEmpty()) {
            showError(register_password, "Please enter your password");
            isValid = false;
        }
        else if (checkPassword.length() < 8) {
            showError(register_password, "Your password length must be at least 8 characters");
            isValid = false;
        }
        else if (checkPassword.contains(" ")) {
            showError(register_password, "Your password should not contain spaces");
            isValid = false;
        }
        else if (checkPassword.length() > 64) {
            showError(register_password, "Your password can have at most 64 characters");
            isValid = false;
        }
        else if (checkConfirmedPassword.isEmpty()) {
            showError(confirm_password, "Please confirm your password");
            isValid = false;
        }
        else if (!checkConfirmedPassword.equals(checkPassword)) {
            showError(confirm_password, "Your password doesn't match the previous one");
            isValid = false;
        }

        Password = checkPassword;
        EmailAddress = checkEmailAddress;

        if (isValid) {
            m_auth.createUserWithEmailAndPassword(checkEmailAddress, checkPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser f_user = m_auth.getCurrentUser();
                            if (f_user != null) {
                                f_user.sendEmailVerification()
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(SignUp.this, "Email verification link sent to your email", Toast.LENGTH_SHORT).show();
                                            completeRegistration(checkUsername, checkEmailAddress);
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Email not sent" + e.getMessage());
                                            Toast.makeText(SignUp.this, "Failed to send email verification link", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(SignUp.this, "Failed to get current user", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "This email is already in use. Please enter another one", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void completeRegistration(String password, String emailAddress) {
        Toast.makeText(SignUp.this, "You have successfully registered", Toast.LENGTH_SHORT).show();

        userID = Objects.requireNonNull(m_auth.getCurrentUser()).getUid();
        DocumentReference documentReference = f_store.collection("users").document(userID);

        Map<String, Object> user = getStringObjectMap(password, emailAddress);

        documentReference.set(user)
                .addOnSuccessListener(unused -> Log.d(TAG, "User profile has been created for " + userID))
                .addOnFailureListener(e -> Log.d(TAG, e.toString()));

        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
    }

    @NonNull
    private static Map<String, Object> getStringObjectMap(String emailAddress, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("Email address", emailAddress);
        user.put("Password", password);

        return user;
    }

    private void showError(@NonNull EditText input, String errorText) {
        input.setError(errorText);
        input.requestFocus();
    }
}
*/
package com.example.footyinsight;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    EditText username, email_address, register_password, confirm_password;
    Button register_button;
    FirebaseAuth m_auth;
    FirebaseFirestore f_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.fullName);
        email_address = findViewById(R.id.email);
        register_password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signupConfirmPassword);
        register_button = findViewById(R.id.signUpBtn);

        m_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();

        register_button.setOnClickListener(v -> checkCredentials());

        TextView back_to_login_text = findViewById(R.id.signInBtn);
        back_to_login_text.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });
    }

    private void checkCredentials() {
        String checkUsername = username.getText().toString().trim();
        String checkEmailAddress = email_address.getText().toString().trim();
        String checkPassword = register_password.getText().toString();
        String checkConfirmedPassword = confirm_password.getText().toString().trim();

        boolean isValid = true;

        // Validation checks for username, email, and password...

        // Checking if passwords match
        if (!checkConfirmedPassword.equals(checkPassword)) {
            showError(confirm_password, "Your password doesn't match the previous one");
            isValid = false;
        }

        if (isValid) {
            m_auth.createUserWithEmailAndPassword(checkEmailAddress, checkPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser f_user = m_auth.getCurrentUser();
                            if (f_user != null) {
                                f_user.sendEmailVerification()
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(SignUp.this, "Email verification link sent to your email", Toast.LENGTH_SHORT).show();
                                            completeRegistration(checkUsername, checkEmailAddress);
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Email not sent" + e.getMessage());
                                            Toast.makeText(SignUp.this, "Failed to send email verification link", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(SignUp.this, "Failed to get current user", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "This email is already in use. Please enter another one", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void completeRegistration(String username, String emailAddress) {
        Toast.makeText(SignUp.this, "You have successfully registered", Toast.LENGTH_SHORT).show();

        String userID = Objects.requireNonNull(m_auth.getCurrentUser()).getUid();
        DocumentReference documentReference = f_store.collection("users").document(userID);

        Map<String, Object> user = new HashMap<>();
        user.put("Username", username);
        user.put("Email address", emailAddress);

        documentReference.set(user)
                .addOnSuccessListener(unused -> Log.d(TAG, "User profile has been created for " + userID))
                .addOnFailureListener(e -> Log.d(TAG, e.toString()));

        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
    }

    private void showError(@NonNull EditText input, String errorText) {
        input.setError(errorText);
        input.requestFocus();
    }
}