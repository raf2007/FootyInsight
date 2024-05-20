package com.example.footyinsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinishActivity extends AppCompatActivity {


    private TextView finishResult;
    private Button goToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        finishResult = findViewById(R.id.finish_result);
        goToMain = findViewById(R.id.goToMain);

        String score = getIntent().getStringExtra("score");
        finishResult.setText("Your Result is: "+score);
        goToMain.setOnClickListener(l -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        });

        //finishResult.setText("Result is");

    }
}