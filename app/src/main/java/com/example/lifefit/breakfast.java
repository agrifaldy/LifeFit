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

public class breakfast extends Fragment {

    RecyclerView recyclerView;
    View view;
    ArrayList<ModelCardSlide> mainModels;
    AdapterCardSlide mainAdapter;

    public breakfast() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_breakfast, container, false);
        recyclerView = view.findViewById(R.id.recycler_view2);

        Integer[] langLogo = {R.drawable.air, R.drawable.tehhijau, R.drawable.susu, R.drawable.kopi, R.drawable.teh_mint, R.drawable.teh_earl_gray};

        String[] langName = {"Air", "Teh Hijau", "Susu", "Kopi", "Teh Earl Gray", "Teh Mint"};

        String[] langKal = {"0 KKAL", "2 KKAL", "42,3 KKAL", "2 KKAL", "52 KKAL", "4 KKAL"};

        mainModels = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            ModelCardSlide model = new ModelCardSlide(langLogo[i], langName[i], langKal[i]);
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