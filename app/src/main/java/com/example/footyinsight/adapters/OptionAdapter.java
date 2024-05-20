package com.example.footyinsight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.footyinsight.R;

import java.util.List;

public class OptionAdapter extends ArrayAdapter<String> {

    public OptionAdapter(@NonNull Context context, @NonNull List<String> questions) {
        super(context, 0, questions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_option, parent, false);
        }

        String currentOption = getItem(position);

        TextView quizQuestionBtn = convertView.findViewById(R.id.quizQuestionBtn);
        quizQuestionBtn.setText(currentOption);

        return convertView;
    }

}