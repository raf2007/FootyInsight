package com.example.footyinsight;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HardActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private Button submitButton, nextButton;
    private int points = 0;
    private int currentQuestionIndex = 0;
    private int currentAnswerIndex = -1;
    private int[] buttonColors = new int[4];

    private String[] questions = {
            "Which country has won the most FIFA World Cup titles in a row?",
            "Who holds the record for the most goals scored in a single FIFA World Cup tournament?",
            "Which team has the longest unbeaten streak in UEFA Champions League history?",
            "Who is the youngest player to score a hat-trick in a FIFA World Cup match?",
            "Which player has won the most FIFA World Cup Golden Boot awards?",
            "Which country has the most FIFA World Cup appearances without ever winning the tournament?",
            "Who is the only goalkeeper to score a goal in a FIFA World Cup match?",
            "Which team has the most wins in the UEFA European Championship history?",
            "Who is the only player to score in two different FIFA World Cup final matches?",
            "Which player has the most career assists in the UEFA Champions League?"
    };

    private String[][] answerOptions = {
            {"Brazil", "Germany", "Italy", "Argentina"},
            {"Just Fontaine", "Gerd Müller", "Eusébio", "Pelé"},
            {"Real Madrid", "Bayern Munich", "AC Milan", "Barcelona"},
            {"Pele", "Michael Owen", "Julian Green", "Kylian Mbappé"},
            {"Miroslav Klose", "Gary Lineker", "Eusébio", "Just Fontaine"},
            {"United States", "England", "Spain", "Netherlands"},
            {"Rogerio Ceni", "Haris Seferovic", "José Luis Chilavert", "Peter Schmeichel"},
            {"Germany", "Spain", "France", "Italy"},
            {"Zinedine Zidane", "Ronaldo", "Kylian Mbappé", "Lionel Messi"}
    };

    private int[] correctAnswers = {0, 0, 0, 3, 0, 0, 3, 0, 1, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        questionTextView = findViewById(R.id.questionTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        loadQuestion();

        option1Button.setOnClickListener(v -> handleOptionClick(0));
        option2Button.setOnClickListener(v -> handleOptionClick(1));
        option3Button.setOnClickListener(v -> handleOptionClick(2));
        option4Button.setOnClickListener(v -> handleOptionClick(3));

        submitButton.setOnClickListener(v -> {
            checkAnswer();
            updateButtonColors();
            updatePoints(currentAnswerIndex == correctAnswers[currentQuestionIndex]);
            disableSubmitButton();
            enableNextButton();
            resetButtonTexts();
        });

        nextButton.setOnClickListener(v -> {
            moveToNextQuestion();
            resetAnswer();
            resetButtonColors();
            enableSubmitButton();
            disableNextButton();
            resetButtonTexts();
        });
    }
    private void resetButtonColors() {
        buttonColors[0] = Color.WHITE;
        buttonColors[1] = Color.WHITE;
        buttonColors[2] = Color.WHITE;
        buttonColors[3] = Color.WHITE;
        option1Button.setBackgroundColor(Color.WHITE);
        option2Button.setBackgroundColor(Color.WHITE);
        option3Button.setBackgroundColor(Color.WHITE);
        option4Button.setBackgroundColor(Color.WHITE);
    }

    private void loadQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        option1Button.setText(answerOptions[currentQuestionIndex][0]);
        option2Button.setText(answerOptions[currentQuestionIndex][1]);
        option3Button.setText(answerOptions[currentQuestionIndex][2]);
        option4Button.setText(answerOptions[currentQuestionIndex][3]);
    }

    private void handleOptionClick(int index) {
        resetButtonColors();
        buttonColors[index] = Color.GREEN;
        option1Button.setBackgroundColor(buttonColors[0]);
        option2Button.setBackgroundColor(buttonColors[1]);
        option3Button.setBackgroundColor(buttonColors[2]);
        option4Button.setBackgroundColor(buttonColors[3]);
        currentAnswerIndex = index;
    }

    private void checkAnswer() {
    }

    private void updateButtonColors() {
        if (currentAnswerIndex != -1) {
            if (currentAnswerIndex == correctAnswers[currentQuestionIndex]) {
                buttonColors[currentAnswerIndex] = Color.GREEN;
            } else {
                buttonColors[currentAnswerIndex] = Color.RED;
                buttonColors[correctAnswers[currentQuestionIndex]] = Color.GREEN;
            }
        }
        option1Button.setBackgroundColor(buttonColors[0]);
        option2Button.setBackgroundColor(buttonColors[1]);
        option3Button.setBackgroundColor(buttonColors[2]);
        option4Button.setBackgroundColor(buttonColors[3]);
    }

    private void resetAnswer() {
        currentAnswerIndex = -1;
    }

    private void updatePoints(boolean isCorrect) {
        if (isCorrect) {
            points++;
        }
    }

    private void disableSubmitButton() {
        submitButton.setEnabled(false);
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }

    private void enableSubmitButton() {
        submitButton.setEnabled(true);
        option1Button.setEnabled(true);
        option2Button.setEnabled(true);
        option3Button.setEnabled(true);
        option4Button.setEnabled(true);
    }
    private void disableNextButton() {
        nextButton.setEnabled(false);
    }

    private void enableNextButton() {
        nextButton.setEnabled(true);
    }

    private void resetButtonTexts() {
        option1Button.setText(answerOptions[currentQuestionIndex][0]);
        option2Button.setText(answerOptions[currentQuestionIndex][1]);
        option3Button.setText(answerOptions[currentQuestionIndex][2]);
        option4Button.setText(answerOptions[currentQuestionIndex][3]);
    }

    private void moveToNextQuestion() {
        resetAnswer();
        resetButtonColors();
        resetButtonTexts();

        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            loadQuestion();
        } else {
            Intent intent = new Intent(HardActivity.this, EndActivity.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        }
    }
}
