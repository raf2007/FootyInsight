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
        // Get the data item for this position
        Category category = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvIcon = convertView.findViewById(R.id.tvIcon);

        // Populate the data into the template view using the data object
        if(category != null) {
            tvName.setText(category.getCategory());
            tvIcon.setText(category.getIcon());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}