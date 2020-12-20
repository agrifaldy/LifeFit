package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class akun_saya_edit extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText et_username, et_email, et_notelp, et_pekerjaan;
    String username, email, phone, password, pekerjaan;
    TextView passwordx;
    Button btn_simpan;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String DISPLAY_NAME = null;
    String EMAIL = null;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    CircularImageView profileImage;
    CardView changeProfileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_akun_saya_edit);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fetch();
                Picasso.get().load(uri).into(profileImage);
            }
        });

        Intent data = getIntent();
        username = data.getStringExtra("usernameP");
        email = data.getStringExtra("emailP");
        phone = data.getStringExtra("phoneP");
        password = data.getStringExtra("passwordP");
        pekerjaan = data.getStringExtra("pekerjaanP");


        passwordx = findViewById(R.id.passwordx);
        passwordx.setText(password);

        et_username = findViewById(R.id.et_username);
        et_username.setText(username);
        et_email = findViewById(R.id.et_email);
        et_email.setText(email);
        et_notelp = findViewById(R.id.et_notelp);
        et_notelp.setText(phone);
        et_pekerjaan = findViewById(R.id.et_pekerjaan);
        et_pekerjaan.setText(pekerjaan);
        btn_simpan = findViewById(R.id.btn_update);


        profileImage = findViewById(R.id.ci_image);
        changeProfileImage = findViewById(R.id.upload_image);

        /**et_username.setText(username);
        et_email.setText(email);
        et_notelp.setText(phone);**/

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = mAuth.getCurrentUser().getUid();
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                String password = passwordx.getText().toString();
                String nomorTelepon = et_notelp.getText().toString();
                String pekerjaan = et_pekerjaan.getText().toString();

                User user1 = new User(userId, username, email, password, nomorTelepon, pekerjaan);

                FirebaseUser userData = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                userData.updateProfile(profileUpdates);

                mDatabase.child("users").child(userId).setValue(user1);

                /**ProgressDialog dialog = ProgressDialog.show(akun_saya_edit.this, "Update Profil",
                        "Loading. Please wait...", true);**/
                startActivity(new Intent(getApplicationContext(), LoadingSplash.class));
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fetch();
                        Picasso.get().load(uri).into(profileImage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(akun_saya_edit.this, "Failed Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**public void updateProfile(String userId, String username, String email, String password, String nomorTelepon, String pekerjaan){

        userId = mAuth.getCurrentUser().getUid();
        username = et_username.getText().toString();
        email = et_email.getText().toString();
        password = passwordx.getText().toString();
        nomorTelepon = et_notelp.getText().toString();
        pekerjaan = et_pekerjaan.getText().toString();

        User user1 = new User(userId, username, email, password, nomorTelepon, pekerjaan);


        mDatabase.child("users").child(userId).setValue(user1);
    }**/

    public void handleImageClick(View view) {

    }

    public void backToAkunSaya(View view) {
        finish();
    }
}