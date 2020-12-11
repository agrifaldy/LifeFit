package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lifefit.IndeksMassaTubuh.indeks_massa_tubuh;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class akun_saya extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView tv_title_username;
    private TextView tv_title_email;
    private TextView tv_dp_username;
    private TextView tv_dp_email;
    private TextView tv_dp_notelp;
    private TextView tv_dp_pekerjaanp;
    private TextView tv_dp_passwordp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    CircularImageView profileImageAkunSaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_akun_saya);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fetch();
                Picasso.get().load(uri).into(profileImageAkunSaya);
            }
        });

        profileImageAkunSaya = findViewById(R.id.ci_image_akunsaya);

        mAuth = FirebaseAuth.getInstance();
        String emailUser = mAuth.getCurrentUser().getEmail();
        String namaUser = mAuth.getCurrentUser().getDisplayName();

        tv_title_username = findViewById(R.id.tv_title_username);
        tv_title_username.setText(namaUser);
        tv_title_email = findViewById(R.id.tv_title_email);
        tv_title_email.setText(emailUser);

        tv_dp_username = findViewById(R.id.tv_dp_username);
        tv_dp_username.setText(namaUser);
        tv_dp_email = findViewById(R.id.tv_dp_email);
        tv_dp_email.setText(emailUser);

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Object fieldsObj = new Object();
                Object fieldsOb2 = new Object();
                HashMap fldObj;
                HashMap value;


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    fldObj = (HashMap)snapshot.getValue(fieldsObj.getClass());
                    value = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());

                    fldObj.get("");
                    value.get("nodeId");


                    if(mAuth.getCurrentUser().getUid().equals(fldObj.get("userId").toString())) {
                        String password = fldObj.get("password").toString();
                        String telepon = fldObj.get("nomorTelepon").toString();
                        String pekerjaan = fldObj.get("pekerjaan").toString();

                        tv_dp_notelp = findViewById(R.id.tv_dp_notelp);
                        tv_dp_notelp.setText(telepon);

                        tv_dp_pekerjaanp = findViewById(R.id.tv_pekerjaanP);
                        tv_dp_pekerjaanp.setText(pekerjaan);

                        tv_dp_passwordp = findViewById(R.id.tv_passwordP);
                        tv_dp_passwordp.setText(password);



                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public void toEdit(View view) {
        Intent intent = new Intent(this, akun_saya_edit.class);
        intent.putExtra("usernameP", tv_dp_username.getText().toString());
        intent.putExtra("emailP", tv_dp_email.getText().toString());
        intent.putExtra("phoneP", tv_dp_notelp.getText().toString());
        intent.putExtra("pekerjaanP", tv_dp_pekerjaanp.getText().toString());
        intent.putExtra("passwordP", tv_dp_passwordp.getText().toString());
        startActivity(intent);
    }
}