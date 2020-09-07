package com.java.yandifei.ui.scholar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.yandifei.R;
import com.java.yandifei.network.GetBitmapAsyncTask;
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
        Toolbar toolbar = findViewById(R.id.scholar_toolbar);
        if(scholar.is_passedaway) toolbar.setTitle(R.string.scholar_passed);
        else toolbar.setTitle(R.string.scholar_default);

        bio.setText(scholar.profile.bio);
        name.setText(scholar.nameString());
        position.setText(scholar.profile.position);
        edu.setText(scholar.profile.edu);
        work.setText(scholar.profile.work);

        final ImageView imageView = findViewById(R.id.scholar_avatar);
        new GetBitmapAsyncTask(scholar.avatar, new GetBitmapAsyncTask.PostExecBitmap() {
            @Override
            public void handleBitmap(Bitmap avatar) {
                imageView.setImageBitmap(avatar);
            }
        }).execute();
    }
}
