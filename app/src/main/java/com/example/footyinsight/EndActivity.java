package com.example.footyinsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndActivity extends AppCompatActivity {

    private TextView pointsTextView;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        pointsTextView = findViewById(R.id.pointsTextView);
        points = getIntent().getIntExtra("points", 0);

        pointsTextView.setText("Total Points: " + points);

        findViewById(R.id.restartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    private void restartQuiz() {
        Intent intent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}