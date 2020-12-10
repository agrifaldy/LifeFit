package com.example.lifefit.IndeksMassaTubuh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifefit.R;
import com.example.lifefit.IndeksMassaTubuh.Bmi;
import com.example.lifefit.IndeksMassaTubuh.BmiAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class indeks_massa_tubuh extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Bmi");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<Bmi> list = new ArrayList<>();
    private int year, month, day;

//    private BmiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_indeks_massa_tubuh);

        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //button click
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        readData();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_indeks_massa_tubuh_input);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText brt = dialog.findViewById(R.id.et_berat);
        final EditText tgi = dialog.findViewById(R.id.et_tinggi);
        final EditText tgl = dialog.findViewById(R.id.et_tanggal);
        Button btnAdd = dialog.findViewById(R.id.btn_add);

        final Calendar calendar = Calendar.getInstance();

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(indeks_massa_tubuh.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        tgl.setText(day+"/"+(month+1)+"/"+year);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());

                        tgl.setText(currentDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(brt.getText())){
                    brt.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tgi.getText())){
                    tgi.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tgl.getText())){
                    tgl.setError("Tidak boleh kosong");
                } else {
                    String b = brt.getText().toString();
                    String ti = tgi.getText().toString();
                    String tl = tgl.getText().toString();

                    double beratBadan = Double.parseDouble(b);
                    double tinggiBadan = Double.parseDouble(ti);

                    double hasil = beratBadan/(tinggiBadan*tinggiBadan*0.0001);

                    String p = Double.toString(hasil);

                    String keterangan;

                    if(hasil <= 18.4) {
                        keterangan = "Berat Badan Kurang";
                    }else if(hasil >= 18.5 && hasil <= 24.9 ) {
                        keterangan = "Berat Badan Ideal";
                    }else if(hasil >= 25 && hasil <= 29.9) {
                        keterangan = "Berat Badan Lebih";
                    }else if(hasil >= 30 && hasil <= 39.9) {
                        keterangan = "Gemuk";
                    }else{
                        keterangan = "Sangat Gemuk";
                    }

                    addDataToFirebase(b, ti, tl, p, keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void addDataToFirebase(String x, String y, String z, String p, String ket){
        String id = myRef.push().getKey();
        Bmi bmi = new Bmi(id, x, y, z, p, ket);

        myRef.child(id).setValue(bmi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void readData(){
        // Read from the database

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Bmi username = dataSnapshot.getValue(Bmi.class);

                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        list.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Bmi value = snapshot.getValue(Bmi.class);
                            list.add(0,value);
                        }
                        recyclerView.setAdapter(new BmiAdapter(indeks_massa_tubuh.this,list));
//                        setClick();
                    /**if (mAuth.getCurrentUser().getUid().equals(username.getId())){
                        recyclerView.setVisibility(View.VISIBLE);
                    }**/
                    }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
    }

//    private void setClick() {
//        adapter.setOnCallBack(new BmiAdapter.OnCallBack() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onButtonEditClick(Bmi bmi) {
//                showDialogUpdateBmi(bmi);
//            }
//        });
//    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void showDialogUpdateBmi(final Bmi bmi) {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.activity_indeks_massa_tubuh_input);
//
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(lp);
//
//        ImageButton btnClose = dialog.findViewById(R.id.btn_close);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        final EditText brt = dialog.findViewById(R.id.et_berat);
//        brt.setText(bmi.getBerat());
//        final EditText tgi = dialog.findViewById(R.id.et_tinggi);
//        tgi.setText(bmi.getTinggi());
//        final EditText tgl = dialog.findViewById(R.id.et_tanggal);
//        tgl.setText(bmi.getTanggal());
//        Button btnAdd = dialog.findViewById(R.id.btn_add);
//
//        final Calendar calendar = Calendar.getInstance();
//
//        tgl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(indeks_massa_tubuh.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
////                        tgl.setText(day+"/"+(month+1)+"/"+year);
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, month);
//                        calendar.set(Calendar.DAY_OF_MONTH, day);
//                        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
//
//                        tgl.setText(currentDate);
//                    }
//                },year,month,day);
//                datePickerDialog.show();
//            }
//        });
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(brt.getText())){
//                    brt.setError("Tidak boleh kosong");
//                } else if (TextUtils.isEmpty(tgi.getText())){
//                    tgi.setError("Tidak boleh kosong");
//                } else if (TextUtils.isEmpty(tgl.getText())){
//                    tgl.setError("Tidak boleh kosong");
//                } else {
//                    String p = "17.53";
//                    String keterangan = "Ideal";
//
//                    String a = brt.getText().toString();
//                    String b = tgi.getText().toString();
//                    String c = tgl.getText().toString();
//
//                    updateBmi(bmi, a, b, c, p, keterangan);
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        dialog.show();
//    }
//
//    private void updateBmi(Bmi bmi, String aa, String bb, String cc, String dd, String ee) {
//        myRef.child(bmi.getId()).child("berat").setValue(aa);
//        myRef.child(bmi.getId()).child("tinggi").setValue(bb);
//        myRef.child(bmi.getId()).child("tanggal").setValue(cc);
//        myRef.child(bmi.getId()).child("imt").setValue(dd);
//        myRef.child(bmi.getId()).child("keterangan").setValue(ee);
//    }


    public void toHalamanGrafik(View view) {
        Intent intent = new Intent(this, indeks_massa_tubuh_grafik.class);
        startActivity(intent);
    }
}