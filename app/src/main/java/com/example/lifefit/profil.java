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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class profil extends Fragment implements View.OnClickListener {

    private Button b_logout;
    View view;
    private LinearLayout ll_logout;
    private RelativeLayout feedback, akunsaya, aboutus, sharetext;

    public profil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profil, container, false);

        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        feedback = (RelativeLayout) view.findViewById(R.id.rl_feedback);
        akunsaya = (RelativeLayout) view.findViewById(R.id.rl_akun_saya);
        aboutus = (RelativeLayout) view.findViewById(R.id.rl_about_us);
        sharetext = (RelativeLayout) view.findViewById(R.id.rl_share_text);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] strTo = { "18523227@students.uii.ac.id" };
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        akunsaya.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), akun_saya.class);
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), tentang_kami.class);
                startActivity(intent);
            }
        });

        sharetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Hai, nyobain aplikasi LifeFit yuk \n\n Di aplikasinya kamu dapat memonitoring pola hidup sehatmu sehari-hari, buruan download!";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
//                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });

        ll_logout.setOnClickListener(this);
        return view;
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);
        //Quit();

    }

    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.ll_logout) {
            logOut();
        }
    }

    /**protected void Quit() {
        super.getActivity().finish();
    }**/

}