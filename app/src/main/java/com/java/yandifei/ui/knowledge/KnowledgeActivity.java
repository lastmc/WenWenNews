package com.java.yandifei.ui.knowledge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.yandifei.R;
import com.java.yandifei.network.GetBitmapAsyncTask;
import com.java.yandifei.network.KnowledgeGraph;

public class KnowledgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        Toolbar toolbar = findViewById(R.id.knowledge_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadEntity((KnowledgeGraph) getIntent().getSerializableExtra("data"));

    }

    private void loadEntity(KnowledgeGraph kg){
        TextView name = findViewById(R.id.entity_name);
        TextView brief = findViewById(R.id.entity_brief);
        TextView description = findViewById(R.id.entity_description);
        final ImageView img = findViewById(R.id.entity_avatar);
        LinearLayout property = findViewById(R.id.entity_property);
        LinearLayout relation = findViewById(R.id.entity_relation);

        name.setText(kg.label);
        description.setText(kg.abstractInfo.getDescription());
        new GetBitmapAsyncTask(kg.img, new GetBitmapAsyncTask.PostExecBitmap() {
            @Override
            public void handleBitmap(Bitmap avatar) {
                img.setImageBitmap(avatar);
            }
        }).execute();
        brief.setText(kg.getBrief());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(KnowledgeGraph.Relation r:kg.abstractInfo.covid.relations){
            EntityRelationFragment erf = new EntityRelationFragment();
            Bundle arg = new Bundle();
            arg.putCharSequence("text",r.relation);
            arg.putCharSequence("object",r.label);
            erf.setArguments(arg);
            ft.add(R.id.entity_relation,erf);
        }
        for(String key:kg.abstractInfo.covid.properties.keySet()){
            EntityRelationFragment erf = new EntityRelationFragment();
            Bundle arg = new Bundle();
            arg.putCharSequence("text",key);
            arg.putCharSequence("object",kg.abstractInfo.covid.properties.get(key));
            erf.setArguments(arg);
            ft.add(R.id.entity_property,erf);
        }
        ft.commit();
    }
}
