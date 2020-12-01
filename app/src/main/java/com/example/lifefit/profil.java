package com.example.lifefit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.example.lifefit.R;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class profil extends Fragment implements View.OnClickListener {

    private Button b_logout;
    View view;
    private LinearLayout ll_logout, feedback;

    public profil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profil, container, false);

        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        feedback = (LinearLayout) view.findViewById(R.id.ll_feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] strTo = { "test@g.com" };
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        ll_logout.setOnClickListener(this);
        return view;
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);

    }

    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.ll_logout) {
            logOut();
        }
    }

}