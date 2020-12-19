package com.example.lifefit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifefit.CardSlide.AdapterCardSlide;
import com.example.lifefit.CardSlide.ModelCardSlide;

import java.util.ArrayList;

public class lunch extends Fragment {

    RecyclerView recyclerView;
    View view;
    ArrayList<ModelCardSlide> mainModels;
    AdapterCardSlide mainAdapter;

    public lunch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lunch, container, false);

        recyclerView = view.findViewById(R.id.recycler_view3);

        Integer[] langLogo = {R.drawable.air, R.drawable.jus_orange, R.drawable.es_kelapa_jeruk_nipis, R.drawable.mango_squash, R.drawable.es_cincau,
                R.drawable.es_blewah, R.drawable.es_kuwut};

        String[] langName = {"Air", "Jus Orange", "Es Kelapa Jeruk Nipis", "Mango Squash", "Es Cincau", "Es Blewah", "Es Kuwut"};

        String[] langKal = {"0 KKAL", "49,5 KKAL", "113 KKAL", "100 KKAL", "122 KKAL", "53,9 KKAL", "247 KKAL"};

        mainModels = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            ModelCardSlide model = new ModelCardSlide(langLogo[i],langName[i], langKal[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new AdapterCardSlide(getActivity(), mainModels);
        recyclerView.setAdapter(mainAdapter);

        return view;
    }
}