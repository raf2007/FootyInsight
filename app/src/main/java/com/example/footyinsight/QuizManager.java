package com.example.footyinsight;

import android.util.Log;

import com.example.footyinsight.dataclasses.Quiz;

import java.text.DecimalFormat;
import java.util.List;

public class QuizManager {
    private List<Quiz> quizList;
    private int currentQuizIndex = 0;
    public int totalRight = 0;


    public QuizManager(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public String totalScore(){
        // Format the double value as a percentage string
        return  totalRight+"/"+quizList.size();
    }

    public boolean isRightAnswer(int selectedInd){
        int ind = getCurrentQuiz().getCorrectOptionIndex();
        if(selectedInd == ind){
            totalRight++;
        }
        return selectedInd == ind;

    }

    public void nextQuestion(){
        if(currentQuizIndex < quizList.size()) {
            currentQuizIndex++;
        }
        else{
            currentQuizIndex = 1000;
        }
        Log.d("currentQuiz",""+currentQuizIndex);
    }

    public Quiz getCurrentQuiz(){
        if(currentQuizIndex >= quizList.size()){
            return null;
        }
        else {
            return quizList.get(currentQuizIndex);
        }
    }

}
