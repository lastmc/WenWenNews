package com.java.yandifei.ui.coronadata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.java.yandifei.R;
import com.java.yandifei.network.CoronaData;
import com.java.yandifei.ui.news.NewsItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoronaDataListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_corona_data, container, false);
        final RecyclerView dataListChinaView = view.findViewById(R.id.corona_data_list_china);
        dataListChinaView.setHasFixedSize(true);
        dataListChinaView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                GridLayoutManager.HORIZONTAL, false));
        final RecyclerView dataListGlobalView = view.findViewById(R.id.corona_data_global);
        dataListGlobalView.setHasFixedSize(true);
        dataListGlobalView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                GridLayoutManager.HORIZONTAL, false));
        final List<CoronaData> coronaDataList = new ArrayList<CoronaData>();
        new CoronaData.GetCoronaDataAsyncTask(coronaDataList, new CoronaData.PostExec() {
            @Override
            public void onPostExec(boolean success) {
                List<CoronaData> dataChina = new ArrayList<CoronaData>();
                List<CoronaData> dataGlobal = new ArrayList<CoronaData>();
                for (CoronaData data : coronaDataList) {
                    if (data.district.toLowerCase().contains("china")) dataChina.add(data);
                    else dataGlobal.add(data);
                }
                final CoronaDataItemRecyclerViewAdapter adapterChina =
                        new CoronaDataItemRecyclerViewAdapter(dataChina);
                dataListChinaView.setAdapter(adapterChina);
                final CoronaDataItemRecyclerViewAdapter adapterGlobal =
                        new CoronaDataItemRecyclerViewAdapter(dataGlobal);
                dataListGlobalView.setAdapter(adapterGlobal);
            }
        }).execute();
        return view;
    }

    public View createDataItem(CoronaData data) {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.fragment_corona_data_item, null, false);
        TextView district = (TextView)view.findViewById(R.id.district);
        district.setText(data.district);
        TextView beginTime = (TextView)view.findViewById(R.id.begin_time);
        beginTime.setText(data.begin);
        LineChart chart = (LineChart)view.findViewById(R.id.line_chart);
        List<String> labels = data.getFields();
        for (String label : labels) {
            // get data of days
            List<Entry> entries = new ArrayList<Entry>();
            for (int i = 0; i < data.dataOfDays.size(); ++i) {
                entries.add(new Entry(i, data.dataOfDays.get(i).getData(label)));
            }
            LineDataSet dataSet = new LineDataSet(entries, label);
            chart.setData(new LineData(dataSet));
        }
        for (int i = 0; i < data.dataOfDays.size(); ++i) {

        }
        return view;
    }
}
