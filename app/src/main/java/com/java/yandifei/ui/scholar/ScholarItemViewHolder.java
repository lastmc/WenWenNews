package com.java.yandifei.ui.scholar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;

public class ScholarItemViewHolder extends RecyclerView.ViewHolder {
    public TextView scholarName;
    public TextView scholarPosition;

    public ScholarItemViewHolder(@NonNull View itemView) {
        super(itemView);
        scholarName = itemView.findViewById(R.id.scholar_brief_name);
        scholarPosition = itemView.findViewById(R.id.scholar_brief_position);
    }

}
