package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignupActivity extends AppCompatActivity implements View.OnClickListener {

    //deklarasi beberapa variabel kayak button editext, databaserefrence, firebaseauth
    private static final String TAG = "LoginActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText et_nama;
    private EditText et_email;
    private EditText et_password;
    private Button b_signup;
    private TextView link_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //variabel tadi untuk memanggil fungsi
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // diatur sesuai id komponennya
        et_nama = (EditText)findViewById(R.id.et_nama);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        b_signup = (Button)findViewById(R.id.b_signup);
        link_login = (TextView)findViewById(R.id.link_login);

        //nambahin method onClick, biar tombolnya bisa diklik
        b_signup.setOnClickListener(this);
        link_login.setOnClickListener(this);
    }

    //fungsi ini untuk mendaftarkan data pengguna ke Firebase
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        final String username =et_nama.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            FirebaseUser userData = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();

                            userData.updateProfile(profileUpdates);
                        } else {
                            Toast.makeText(UserSignupActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String username = et_nama.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        // membuat User baru
        writeNewUser(user.getUid(), username, email, password);

        // Go to Login
        startActivity(new Intent(UserSignupActivity.this, UserLoginActivity.class));
        finish();
    }

    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(et_nama.getText().toString())) {
            et_nama.setError("Required");
            result = false;
        } else {
            et_nama.setError(null);
        }

        if (TextUtils.isEmpty(et_email.getText().toString())) {
            et_email.setError("Required");
            result = false;
        } else {
            et_email.setError(null);
        }

        if (TextUtils.isEmpty(et_password.getText().toString())) {
            et_password.setError("Required");
            result = false;
        } else {
            et_password.setError(null);
        }

        return result;
    }

    // menulis ke Database
    private void writeNewUser(String userId, String username, String email, String password) {
        User user = new User(userId, username, email, password);

        mDatabase.child("users").child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.b_signup) {
            signUp();
        }
        else if (i == R.id.link_login) {
            startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
        }
    }

}