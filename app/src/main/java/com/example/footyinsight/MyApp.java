package com.example.footyinsight;

import android.app.Application;

import com.example.footyinsight.dataclasses.Category;
import com.example.footyinsight.dataclasses.Quiz;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        /*FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        String[] categoryNames = {"Football", "Basketball", "Volleyball"};
        String[] icons = {"⚽", "🏀", "🏐"}; // Unicode characters for the icons

        // Generate Category objects

        String[] QUESTIONS = {
                "Who is considered the greatest volleyball player of all time?",
                "Which player holds the record for the most Olympic gold medals in volleyball?",
                "Who is known as 'The Phenom' in women's volleyball?",
                "Who is the tallest volleyball player in history?",
                "Which player is known for the 'Sky Ball' serve?",
                "Who is the current captain of the Brazilian men's national volleyball team?",
                "Who won the Best Setter award at the 2016 Summer Olympics?",
                "Who is the only player to win the FIVB World Cup three times?",
                "Which player is known as the 'Iron Hammer'?",
                "Who is the first player to win the FIVB World Championship three times?"
        };

        String[][] OPTIONS = {
                {"Karch Kiraly", "Giba", "Earvin N'Gapeth", "Kazuhiro Yoshimura"},
                {"Kerri Walsh Jennings", "Giba", "Sergey Tetyukhin", "Lang Ping"},
                {"Thaisa Menezes", "Kerri Walsh Jennings", "Zhu Ting", "Sheilla Castro"},
                {"Dmitriy Muserskiy", "Phil Dalhausser", "Andrea Giani", "Giba"},
                {"Nikolay Apalikov", "Earvin N'Gapeth", "Yuki Ishikawa", "Bartosz Kurek"},
                {"Bruno Rezende", "Andrea Giani", "Giba", "Wilfredo Leon"},
                {"Sergey Grankin", "Giba", "Bruno Rezende", "Micah Christenson"},
                {"Giba", "Maxim Mikhaylov", "Wilfredo Leon", "Zhu Ting"},
                {"Kurek Bartosz", "Yuki Ishikawa", "Karch Kiraly", "Giba"},
                {"Giba", "Karch Kiraly", "Maurice Torres", "Sergey Tetyukhin"}
        };

        int[] CORRECT_OPTION_INDICES = {0, 1, 2, 0, 0, 2, 2, 3, 0, 1};
        int numOf = CORRECT_OPTION_INDICES.length;
        for(int j  = 0;j < numOf;j++){

            List<String> optionsList = Arrays.asList(OPTIONS[j]);

            Quiz quizQuestion = new Quiz(QUESTIONS[j],optionsList,CORRECT_OPTION_INDICES[j]);
            firestore.collection("/categories/Volleyball/categories/Volleyball Players/quiz")
                    .document(quizQuestion.getQuestion())
                    .set(quizQuestion.toMap());
        }*/


        //for (int i = 0; i < categoryNames.length; i++) {
            //Category category = new Category(categoryNames[i], icons[i]);

        /* Category footballRules = new Category("Volleyball Rules", "📜");
            Category footballPlayers = new Category("Volleyball Players", "\uD83C\uDFD0");

            firestore.collection("/categories/Volleyball/categories").document(footballRules.getCategory()).set(footballRules.toMap());
        firestore.collection("/categories/Volleyball/categories").document(footballPlayers.getCategory()).set(footballPlayers.toMap());*/

            /*firestore.collection(FirestoreCollections.CATEGORIES_COLL)
                    .document(category.getCategory())
                    .collection(FirestoreCollections.CATEGORIES_COLL)
                    .document(basketballRules.getCategory())
                    .set(basketballRules);*/


            //firestore.collection(FirestoreCollections.CATEGORIES_COLL).document(category.getCategory()).set(category.toMap());

        //}

    }

}
