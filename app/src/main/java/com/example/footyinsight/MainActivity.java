package com.example.footyinsight;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.footyinsight.adapters.CategoryAdapter;
import com.example.footyinsight.dataclasses.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayList<Category> categoriesList;
    private CategoryAdapter adapter;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firestore = FirebaseFirestore.getInstance();

        listView = findViewById(R.id.categories_list);
        categoriesList = new ArrayList<>();
        adapter = new CategoryAdapter(this, categoriesList);
        listView.setAdapter(adapter);

        retrieveCategoriesFromFirestore();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            CollectionReference ref = null;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked category
                listView.setEnabled(false);
                Category clickedCategory = categoriesList.get(position);
                if (ref == null) {
                ref = firestore.collection(FirestoreCollections.CATEGORIES_COLL).document(clickedCategory.getCategory()).collection(FirestoreCollections.CATEGORIES_COLL);
                }
                else{
                    ref = ref.document(clickedCategory.getCategory()).collection(FirestoreCollections.CATEGORIES_COLL);
                }

                ref.get().addOnSuccessListener(task -> {
                    categoriesList.clear();
                    if(!task.isEmpty()){
                        for(DocumentSnapshot doc : task.getDocuments()) {
                            Category category = doc.toObject(Category.class);
                            categoriesList.add(category);
                        };
                        adapter.notifyDataSetChanged();
                        listView.setEnabled(true);
                    }
                    else{
                        listView.setEnabled(true);
                        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                        intent.putExtra("quizPath", Objects.requireNonNull(ref.getParent()).getPath());
                        startActivity(intent);
                    }
                });


                // Make Firestore call using category information
            }
        });


    }

    private void retrieveCategoriesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the "categories" collection
        db.collection(FirestoreCollections.CATEGORIES_COLL)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Clear previous data
                            categoriesList.clear();

                            // Iterate through the documents
                            for (DocumentSnapshot document : task.getResult()) {
                                // Get category name and icon from Firestore document
                                Category categoryObj = document.toObject(Category.class);
                                if (categoryObj != null) {
                                    categoriesList.add(categoryObj);
                                }
                            }

                            // Notify adapter that the data set has changed
                            adapter.notifyDataSetChanged();
                        } else {
                            // Handle errors
                        }
                    }
                });
    }


}
