package com.java.yandifei.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        Toolbar toolbar = findViewById(R.id.news_content_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadNewsContent((NewsEntry) getIntent().getSerializableExtra("data"));

    }

    private void loadNewsContent(NewsEntry entry){
        System.out.println(entry);
        TextView title = findViewById(R.id.news_title);
        TextView content = findViewById(R.id.news_content);
        TextView author = findViewById(R.id.news_author);
        title.setText(entry.title);
        content.setText(entry.content);//Provide html support?
        author.setText(entry.source+" · "+entry.time);
    }
}
