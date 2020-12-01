package com.example.lifefit;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lifefit.IndeksMassaTubuh.page_monitoring;

public class ensiklopedia extends Fragment implements View.OnClickListener{

    private CardView cv_makanan;
    private CardView cv_olahraga;
    View view;

    public ensiklopedia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ensiklopedia, container, false);
        cv_makanan = (CardView) view.findViewById(R.id.cv_makanan);
        cv_makanan.setOnClickListener(this);
        cv_olahraga = (CardView) view.findViewById(R.id.cv_olahraga);
        cv_olahraga.setOnClickListener(this);
        return view;
    }

    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.cv_makanan) {
            Intent intent = new Intent(getActivity(), MenuMakan.class);
            startActivity(intent);
        } else if (i == R.id.cv_olahraga) {
            Intent intent = new Intent(getActivity(), MenuOlahraga.class);
            startActivity(intent);
        }
    }
}
