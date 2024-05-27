package com.example.footyinsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.footyinsight.adapters.CategoryAdapter;
import com.example.footyinsight.dataclasses.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Category> categoriesList;
    private CategoryAdapter adapter;
    private FirebaseFirestore firestore;
    private Button logoutBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        listView = findViewById(R.id.categories_list);
        categoriesList = new ArrayList<>();
        adapter = new CategoryAdapter(this, categoriesList);
        listView.setAdapter(adapter);

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        retrieveCategoriesFromFirestore();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            CollectionReference ref = null;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setEnabled(false);
                Category clickedCategory = categoriesList.get(position);
                if (ref == null) {
                    ref = firestore.collection(FirestoreCollections.CATEGORIES_COLL).document(clickedCategory.getCategory()).collection(FirestoreCollections.CATEGORIES_COLL);
                } else {
                    ref = ref.document(clickedCategory.getCategory()).collection(FirestoreCollections.CATEGORIES_COLL);
                }

                ref.get().addOnSuccessListener(task -> {
                    categoriesList.clear();
                    if (!task.isEmpty()) {
                        for (DocumentSnapshot doc : task.getDocuments()) {
                            Category category = doc.toObject(Category.class);
                            categoriesList.add(category);
                        }
                        adapter.notifyDataSetChanged();
                        listView.setEnabled(true);
                    } else {
                        listView.setEnabled(true);
                        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                        intent.putExtra("quizPath", Objects.requireNonNull(ref.getParent()).getPath());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void retrieveCategoriesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(FirestoreCollections.CATEGORIES_COLL)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            categoriesList.clear();

                            for (DocumentSnapshot document : task.getResult()) {
                                Category categoryObj = document.toObject(Category.class);
                                if (categoryObj != null) {
                                    categoriesList.add(categoryObj);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
