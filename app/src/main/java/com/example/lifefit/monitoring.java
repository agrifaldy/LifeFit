package com.example.lifefit;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifefit.IndeksMassaTubuh.page_monitoring;


public class monitoring extends Fragment {

    private CardView cv_monitoring;

    public monitoring() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_monitoring, container, false);
        // Inflate the layout for this fragment
        cv_monitoring = v.findViewById(R.id.cv_monitoring);

        cv_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),page_monitoring.class));
            }
        });

        return v;
    }

}