package com.example.lifefit;

import android.content.Intent;
import android.os.Bundle;
import com.example.lifefit.R;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class profil extends Fragment implements View.OnClickListener {

    private Button b_logout;
    View view;

    public profil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profil, container, false);

        b_logout = (Button) view.findViewById(R.id.b_logout);
        b_logout.setOnClickListener(this);
        return view;
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);

    }


    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.b_logout) {
            logOut();
        }
    }
}