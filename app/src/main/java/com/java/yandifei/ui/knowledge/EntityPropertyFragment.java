package com.java.yandifei.ui.knowledge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.java.yandifei.R;

public class EntityPropertyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entity_property,container,false);
        TextView text = view.findViewById(R.id.property_text);
        TextView object =  view.findViewById(R.id.property_object);
        Bundle arguments = getArguments();
        text.setText(arguments.getCharSequence("text"));
        object.setText(arguments.getCharSequence("object"));
        return view;
    }
}
