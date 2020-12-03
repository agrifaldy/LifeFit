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


public class monitoring extends Fragment {

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

        mAuth = FirebaseAuth.getInstance();

        /**String emailUser = mAuth.getCurrentUser().getEmail();
        tv_emailPengguna = getActivity().findViewById(R.id.tv_emailPengguna);
        tv_emailPengguna.setText(emailUser);**/

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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