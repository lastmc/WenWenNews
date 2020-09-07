package com.java.yandifei.ui.knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.KnowledgeGraph;

import java.util.ArrayList;
import java.util.List;

;

public class KnowledgeListFragment extends Fragment {

    public List<KnowledgeGraph> scholarList;
    public KnowledgeItemRecyclerViewAdapter adapter = null;
    public String key;

    public KnowledgeListFragment(){
        this.scholarList = new ArrayList<>();
    }

    public KnowledgeListFragment(String key){
        this();
        this.key = key;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scholar_list, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.scholar_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        final KnowledgeItemRecyclerViewAdapter adapter = new KnowledgeItemRecyclerViewAdapter(this.scholarList);
        this.adapter = adapter;
        adapter.setOnItemClickListener(new KnowledgeItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("mylog: change page to " + scholarList.get(position));
                Intent intent = new Intent();
                intent.putExtra("data", scholarList.get(position));
                intent.setClass(getActivity(),KnowledgeActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        // get data from network
        new KnowledgeGraph.GetKnowledgeGraghListAsyncTask(scholarList,key,new KnowledgeGraph.PostExec() {
            @Override
            public void onPostExec(Boolean success) {
                adapter.notifyDataSetChanged();
            }
        }).execute();

        return view;
    }

}
