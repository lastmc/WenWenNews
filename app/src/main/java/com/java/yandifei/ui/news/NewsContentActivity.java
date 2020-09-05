package com.java.yandifei.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.java.yandifei.R;

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

        TextView textView = findViewById(R.id.news_content);
        textView.setText(Html.fromHtml("<h1>Hello</h1>",Html.FROM_HTML_MODE_LEGACY));

    }
}
