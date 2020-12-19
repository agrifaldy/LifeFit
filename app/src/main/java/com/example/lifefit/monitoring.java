package com.example.lifefit;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


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
    private boolean shouldRefreshOnResume = false;


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

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.monitoring);

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



        //
        ViewPager2 newsViewPager = v.findViewById(R.id.newsViewPager);
        List<ModelNews> modelNews = new ArrayList<>();

        ModelNews modelNews1 = new ModelNews();
        modelNews1.imageUrl = "https://asset.kompas.com/crops/zU8c-nW-gYdZNpWul4TKvV1MMOE=/93x1:1000x606/750x500/data/photo/2019/02/04/719295795.jpg";
        modelNews1.title = "10 Gejala Keracunan Makanan yang Perlu Diwaspadai";
        modelNews.add(modelNews1);

        ModelNews modelNews2 = new ModelNews();
        modelNews2.imageUrl = "https://asset.kompas.com/crops/3k9uT4AxthensHqrltKfPbvtFQ4=/0x11:900x611/750x500/data/photo/2019/04/04/115867469.jpg";
        modelNews2.title = "11 Makanan untuk Bantu Terlihat Awet Muda";
        modelNews.add(modelNews2);

        ModelNews modelNews3 = new ModelNews();
        modelNews3.imageUrl = "https://asset.kompas.com/crops/rPvJAQP8POgdwJMyLt2BkhR7ap4=/0x0:1000x667/750x500/data/photo/2020/05/31/5ed373e36fdac.jpg";
        modelNews3.title = "4 Bahaya Susu Sapi Jika Dikonsumsi Berlebihan";
        modelNews.add(modelNews3);

        ModelNews modelNews4 = new ModelNews();
        modelNews4.imageUrl = "https://asset.kompas.com/crops/x2qHEjZgH0BeYGOxwQ2z_BXftV8=/6x35:994x694/750x500/data/photo/2020/11/06/5fa564193b144.jpg";
        modelNews4.title = "Cara Mencegah dan mengatasi Diare";
        modelNews.add(modelNews4);

        ModelNews modelNews5 = new ModelNews();
        modelNews5.imageUrl = "https://asset.kompas.com/crops/QA5yVeOXQJVpbwp_TK_Hf_DhJ44=/2x20:972x667/750x500/data/photo/2020/11/12/5fad0004b705b.jpg";
        modelNews5.title = "6 Penyebab Obesitas yang Perlu Diwaspadai";
        modelNews.add(modelNews5);

        ModelNews modelNews6 = new ModelNews();
        modelNews6.imageUrl = "https://asset.kompas.com/crops/BV5h1jySrt3cLxr0mqECBfvsvVQ=/0x0:1000x667/750x500/data/photo/2020/06/12/5ee331e8db424.jpg";
        modelNews6.title = "8 Jenis Vitamin B dan Manfaatnya bagi Tubuh";
        modelNews.add(modelNews6);


        String[] link = new String[]{"https://health.kompas.com/read/2020/12/09/100500268/10-gejala-keracunan-makanan-yang-perlu-diwaspadai?page=all",
                "https://health.kompas.com/read/2020/12/09/080500568/11-makanan-untuk-bantu-terlihat-awet-muda?page=all",
                "https://health.kompas.com/read/2020/12/07/060000268/4-bahaya-susu-sapi-jika-dikonsumsi-berlebihan?page=all",
                "https://health.kompas.com/read/2020/12/17/100000768/cara-mencegah-dan-mengatasi-diare",
                "https://health.kompas.com/read/2020/12/15/140500468/6-penyebab-obesitas-yang-perlu-diwaspadai",
                "https://health.kompas.com/read/2020/12/15/120500168/8-jenis-vitamin-b-dan-manfaatnya-bagi-tubuh"};

        newsViewPager.setAdapter(new ModelNewsAdapter(getContext(), modelNews, link));

        newsViewPager.setClipToPadding(false);
        newsViewPager.setClipChildren(false);
        newsViewPager.setOffscreenPageLimit(3);
        newsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        newsViewPager.setPageTransformer(compositePageTransformer);
        //



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

    /**@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        Fragment currentFragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.monitoring);
        if (currentFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(currentFragment);
            ft.attach(currentFragment);
            ft.commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Fragment currentFragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.monitoring);
        if (currentFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(currentFragment);
            ft.attach(currentFragment);
            ft.commit();
        }
    }**/
}