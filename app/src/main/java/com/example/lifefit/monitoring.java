package com.example.lifefit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lifefit.IndeksMassaTubuh.page_monitoring;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lifefit.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class monitoring extends Fragment {

    private CardView cv_deteksi;
    private CardView cv_monitoring;
    private CardView cv_toAkunSaya;
    private TextView tv_emailPengguna;
    private TextView tv_username;
    private TextView sapa;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    CircularImageView profileImageMonitoring;


    public monitoring() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Picasso.get().load(uri).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get().load(uri).networkPolicy(NetworkPolicy.OFFLINE).into(profileImageMonitoring);

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

        String emailUser = mAuth.getCurrentUser().getEmail();

        String namaUser = mAuth.getCurrentUser().getDisplayName();

        Uri photo = mAuth.getCurrentUser().getPhotoUrl();

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = inflater.inflate(R.layout.fragment_monitoring, container, false);
        // Inflate the layout for this fragment
        cv_monitoring = v.findViewById(R.id.cv_monitoring);
        cv_deteksi = v.findViewById(R.id.cv_deteksi);
        cv_toAkunSaya = v.findViewById(R.id.cv_toAkunSaya);
        profileImageMonitoring = v.findViewById(R.id.iv_gambar_profil);

        tv_username = v.findViewById(R.id.tv_username);
        tv_username.setText(namaUser);

        sapa = v.findViewById(R.id.sapa);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            sapa.setText("Selamat pagi, " +namaUser);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            sapa.setText("Selamat siang, " +namaUser);
        }else if(timeOfDay >= 16 && timeOfDay < 19){
            sapa.setText("Selamat sore, " +namaUser);
        }else if(timeOfDay >= 19 && timeOfDay < 24){
            sapa.setText("Selamat malam, " +namaUser);
        }

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

        cv_toAkunSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),akun_saya.class));
            }
        });

        return v;
    }

}