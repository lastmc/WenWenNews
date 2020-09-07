package com.java.yandifei.ui.scholar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.yandifei.R;
import com.java.yandifei.network.Scholar;

public class ScholarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar);

        Toolbar toolbar = findViewById(R.id.scholar_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadScholarEntry((Scholar) getIntent().getSerializableExtra("data"));
    }

    private void loadScholarEntry(Scholar scholar){
        TextView bio = findViewById(R.id.scholar_bio);
        TextView name = findViewById(R.id.scholar_name);
        TextView position = findViewById(R.id.scholar_position);
        TextView edu = findViewById(R.id.scholar_edu);
        TextView work = findViewById(R.id.scholar_work);

        bio.setText(scholar.profile.bio);
        name.setText(scholar.nameString());
        position.setText(scholar.profile.position);
        edu.setText(scholar.profile.edu);
        work.setText(scholar.profile.work);

        ImageView avatar = findViewById(R.id.scholar_avatar);
    }
}
