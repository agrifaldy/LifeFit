package com.example.lifefit;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lifefit.IndeksMassaTubuh.page_monitoring;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lifefit.User;


public class monitoring extends Fragment {

    private CardView cv_deteksi;
    private CardView cv_monitoring;
    private TextView tv_emailPengguna;
    private TextView tv_username;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public monitoring() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        String emailUser = mAuth.getCurrentUser().getEmail();

        String namaUser = mAuth.getCurrentUser().getDisplayName();

        //test


        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = inflater.inflate(R.layout.fragment_monitoring, container, false);
        // Inflate the layout for this fragment
        cv_monitoring = v.findViewById(R.id.cv_monitoring);
        cv_deteksi = v.findViewById(R.id.cv_deteksi);

        tv_username = v.findViewById(R.id.tv_username);
        tv_username.setText(namaUser);

        tv_emailPengguna = v.findViewById(R.id.tv_emailPengguna);
        tv_emailPengguna.setText(emailUser);

        cv_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),page_monitoring.class));
            }
        });

        cv_deteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),DeteksiPenyakit.class));
            }
        });

        return v;
    }

}