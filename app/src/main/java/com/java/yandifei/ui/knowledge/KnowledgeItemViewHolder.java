package com.java.yandifei.ui.knowledge;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;

public class KnowledgeItemViewHolder extends RecyclerView.ViewHolder {
    public TextView entityName;
    public TextView entityBrief;

    public KnowledgeItemViewHolder(@NonNull View itemView) {
        super(itemView);
        entityName = itemView.findViewById(R.id.entity_name);
        entityBrief = itemView.findViewById(R.id.entity_brief);
    }

}
