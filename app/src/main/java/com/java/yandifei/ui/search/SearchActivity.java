package com.java.yandifei.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.java.yandifei.R;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_tool_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final SearchView searchView = findViewById(R.id.search_view);
        final AutoCompleteTextView textView = searchView.findViewById(R.id.search_src_text);
        textView.setThreshold(0);
        String[] history = {"123","234"};
        final ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,history);
        textView.setAdapter(historyAdapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(historyAdapter.getItem(position));
                searchView.clearFocus();
            }
        });
    }
}
