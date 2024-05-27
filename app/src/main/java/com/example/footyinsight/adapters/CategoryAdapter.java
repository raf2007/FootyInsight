package com.example.footyinsight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.footyinsight.R;
import com.example.footyinsight.dataclasses.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvIcon = convertView.findViewById(R.id.tvIcon);

        if(category != null) {
            tvName.setText(category.getCategory());
            tvIcon.setText(category.getIcon());
        }

        return convertView;
    }
}