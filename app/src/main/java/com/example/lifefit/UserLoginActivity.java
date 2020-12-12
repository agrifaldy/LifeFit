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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText et_email;
    private EditText et_password;
    private Button b_login;
    private TextView link_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //variabel tadi untuk memanggil fungsi
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        // diatur sesuai id komponennya
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        b_login = (Button)findViewById(R.id.b_login);
        link_signup = (TextView)findViewById(R.id.link_signup) ;

        //nambahin method onClick, biar tombolnya bisa diklik
        b_login.setOnClickListener(this);
        link_signup.setOnClickListener(this);

    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        final String email = et_email.getText().toString();
        String password = et_password.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser.getEmail().equals("admin@gmail.com")) {
                                startActivity(new Intent(UserLoginActivity.this, AdminLoginActivity.class));
                                finish();
                            } else  {
                                startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
                                finish();
                            }

                        } else {
                            Toast.makeText(UserLoginActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
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

    public void onClick(View view){
        int i = view.getId();
        if (i == R.id.b_login) {
            signIn();
        } else if (i == R.id.link_signup) {
            startActivity(new Intent(UserLoginActivity.this, UserSignupActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }

}