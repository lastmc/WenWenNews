package com.java.yandifei.ui.scholar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.Scholar;
import com.java.yandifei.ui.news.NewsContentActivity;;

import java.util.ArrayList;
import java.util.List;

public class ScholarListFragment extends Fragment {

    public List<Scholar> scholarList;
    public ScholarItemRecyclerViewAdapter adapter = null;

    public ScholarListFragment(){
        this.scholarList = new ArrayList<>();
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        final ScholarItemRecyclerViewAdapter adapter = new ScholarItemRecyclerViewAdapter(this.scholarList);
        this.adapter = adapter;
        adapter.setOnItemClickListener(new ScholarItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("mylog: change page to " + scholarList.get(position));
                Intent intent = new Intent();
                intent.putExtra("data", scholarList.get(position));
                intent.setClass(getActivity(),ScholarActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        // get data from network
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                Scholar.getScholarList(scholarList);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                adapter.notifyDataSetChanged();
            }
        }.execute();

        return view;
    }

}
