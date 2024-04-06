package com.example.footyinsight;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NormalActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private Button submitButton, nextButton;
    private int points = 0;
    private int currentQuestionIndex = 0;
    private int currentAnswerIndex = -1;
    private int[] buttonColors = new int[4];

    private String[] questions = {
            "Which country has won the most FIFA World Cup titles?",
            "Who is the only player to have won the FIFA World Cup three times?",
            "Which team has won the most UEFA Champions League titles?",
            "Who is the youngest player to score in a World Cup final?",
            "Which player has won the most Ballon d'Or awards?",
            "Which country has won the most UEFA European Championship titles?",
            "Who is the all-time top scorer in the Copa America tournament?",
            "Which club has won the most English Premier League titles?",
            "Who is the youngest player to debut in the UEFA Champions League?",
            "Which player has the most career goals in international football?"
    };

    private String[][] answerOptions = {
            {"Brazil", "Germany", "Italy", "Argentina"},
            {"Pelé", "Diego Maradona", "Zinedine Zidane", "Cristiano Ronaldo"},
            {"Real Madrid", "FC Barcelona", "Bayern Munich", "AC Milan"},
            {"Pelé", "Kylian Mbappé", "Diego Maradona", "Ronaldo"},
            {"Lionel Messi", "Cristiano Ronaldo", "Michel Platini", "Johan Cruyff"},
            {"Germany", "Spain", "France", "Italy"},
            {"Lionel Messi", "Neymar", "Gabriel Batistuta", "Zlatan Ibrahimović"},
            {"Manchester United", "Liverpool", "Arsenal", "Chelsea"},
            {"Gareth Bale", "Ansu Fati", "Bojan Krkić", "Cesc Fàbregas"},
            {"Cristiano Ronaldo", "Pelé", "Romário", "Ali Daei"}
    };

    private int[] correctAnswers = {0, 0, 0, 1, 1, 2, 2, 0, 1, 0};

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
    }

    private void enableSubmitButton() {
        submitButton.setEnabled(true);
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
            Intent intent = new Intent(NormalActivity.this, EndActivity.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        }
    }
}