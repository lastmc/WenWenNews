package com.java.yandifei.ui.coronadata;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.java.yandifei.R;

public class CoronaDataItemViewHolder extends RecyclerView.ViewHolder {
    public TextView district;
    public TextView beginTime;
    public LineChart lineChart;

    public CoronaDataItemViewHolder(@NonNull View itemView) {
        super(itemView);
        district = itemView.findViewById(R.id.district);
        beginTime = itemView.findViewById(R.id.begin_time);
        lineChart = itemView.findViewById(R.id.line_chart);
    }

}
