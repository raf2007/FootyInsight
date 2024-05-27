package com.example.footyinsight;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.footyinsight.adapters.OptionAdapter;
import com.example.footyinsight.dataclasses.Quiz;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private OptionAdapter adapter;
    private ArrayList<String> options = new ArrayList<>();
    private TextView quizQuestionView;
    private TextView tvQuestionNumber;
    private Button submitBtn;
    private Button nextBtn;

    private List<Quiz> quizList = new ArrayList<>();
    private QuizManager quizManager;
    private FirebaseFirestore firestore;

    private ListView optionsList;
    private boolean isSubmitted = false;
    private int selectedIndex = -1;

    public void clearBackground(){
        for (int i = 0; i < optionsList.getChildCount(); i++) {
            if (optionsList.getChildAt(i) != null) {
                optionsList.getChildAt(i).findViewById(R.id.quizQuestionBtn).setBackgroundTintList(getTintColor(R.color.magenta));
            }
        }
    }

    public void updateQuiz(Quiz currentQuiz){
        if (currentQuiz != null) {
            quizQuestionView.setText(currentQuiz.getQuestion());
            updateQuestionNumber();
            clearBackground();
            options.clear();
            options.addAll(currentQuiz.getOptions());
            adapter.notifyDataSetChanged();
            optionsList.setEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
            intent.putExtra("score", quizManager.totalScore());
            startActivity(intent);
        }
    }

    private void updateQuestionNumber() {
        int currentQuestionIndex = quizManager.getCurrentQuestionIndex() + 1;
        int totalQuestions = quizList.size();
        tvQuestionNumber.setText("Question " + currentQuestionIndex + "/" + totalQuestions);
    }

    private ColorStateList getTintColor(int id) {
        return ContextCompat.getColorStateList(getApplicationContext(), id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        firestore = FirebaseFirestore.getInstance();
        quizQuestionView = findViewById(R.id.quizQuestion);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        optionsList = findViewById(R.id.quizOptionsList);
        submitBtn = findViewById(R.id.btnSubmitQuiz);
        nextBtn = findViewById(R.id.btnNextQuiz);

        String ref = getIntent().getStringExtra("quizPath");
        if (ref != null) {
            firestore.collection(ref + "/" + FirestoreCollections.QUESTIONS_SUB_COLL).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        Quiz quiz = snapshot.toObject(Quiz.class);
                        if (quiz != null) {
                            quizList.add(quiz);
                        }
                    }
                }

                quizManager = new QuizManager(quizList);
                adapter = new OptionAdapter(this, options);
                optionsList.setAdapter(adapter);

                options.addAll(quizList.get(0).getOptions());
                quizQuestionView.setText(quizList.get(0).getQuestion());
                updateQuestionNumber();
                adapter.notifyDataSetChanged();
            });
        }

        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearBackground();
                selectedIndex = position;
                view.findViewById(R.id.quizQuestionBtn).setBackgroundTintList(getTintColor(R.color.blue));
            }
        });

        submitBtn.setOnClickListener(l -> {
            if (selectedIndex != -1) {
                isSubmitted = true;
                optionsList.setEnabled(false);
                if (quizManager.isRightAnswer(selectedIndex)) {
                    optionsList.getChildAt(selectedIndex).findViewById(R.id.quizQuestionBtn).setBackgroundTintList(getTintColor(R.color.green));
                } else {
                    optionsList.getChildAt(selectedIndex).findViewById(R.id.quizQuestionBtn).setBackgroundTintList(getTintColor(R.color.red));
                    optionsList.getChildAt(quizManager.getCurrentQuiz().getCorrectOptionIndex()).findViewById(R.id.quizQuestionBtn).setBackgroundTintList(getTintColor(R.color.green));
                }
                selectedIndex = -1;
            }
        });

        nextBtn.setOnClickListener(l -> {
            if (isSubmitted) {
                quizManager.nextQuestion();
                updateQuiz(quizManager.getCurrentQuiz());
                isSubmitted = false;
            }
        });
    }
}
