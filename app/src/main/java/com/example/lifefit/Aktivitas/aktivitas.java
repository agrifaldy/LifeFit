package com.example.lifefit.Aktivitas;

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
import android.widget.Toast;

import com.example.lifefit.DenyutJantung.DenyutJantung;
import com.example.lifefit.DenyutJantung.denyut_jantung;
import com.example.lifefit.IndeksMassaTubuh.Bmi;
import com.example.lifefit.IndeksMassaTubuh.BmiAdapter;
import com.example.lifefit.IndeksMassaTubuh.indeks_massa_tubuh;
import com.example.lifefit.IndeksMassaTubuh.page_monitoring;
import com.example.lifefit.R;
import com.example.lifefit.TekananDarah.tensi_grafik;
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

public class aktivitas extends AppCompatActivity {

    private FloatingActionButton btnAddAktivitas;
    private RecyclerView recyclerViewAktivitas;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Aktivitas");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<AktivitasHarian> list = new ArrayList<>();
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnAddAktivitas = findViewById(R.id.btn_add_aktivitas);
        recyclerViewAktivitas = findViewById(R.id.recycler_view_aktivitas);

        recyclerViewAktivitas.setLayoutManager(new LinearLayoutManager(this));

        //button click
        btnAddAktivitas.setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(R.layout.activity_aktivitas_input);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton btnClose = dialog.findViewById(R.id.btn_close_aktivitas);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText makan = dialog.findViewById(R.id.et_aktivitas_makan);
        final EditText minum = dialog.findViewById(R.id.et_aktivitas_minum);
        final EditText tidur = dialog.findViewById(R.id.et_aktivitas_tidur);
        final EditText olahraga = dialog.findViewById(R.id.et_aktivitas_olahraga);
        final EditText tgl = dialog.findViewById(R.id.et_aktivitas_tanggal);
        Button btnAdd = dialog.findViewById(R.id.btn_simpan_aktivitas);

        final Calendar calendar = Calendar.getInstance();

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(aktivitas.this, new DatePickerDialog.OnDateSetListener() {
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
                if (TextUtils.isEmpty(makan.getText())){
                    makan.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(minum.getText())){
                    minum.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tidur.getText())){
                    tidur.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(olahraga.getText())){
                    olahraga.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tgl.getText())){
                    tgl.setError("Tidak boleh kosong");
                } else {

                    String mkn = makan.getText().toString();
                    String mnm = minum.getText().toString();
                    String tdr = tidur.getText().toString();
                    String or = olahraga.getText().toString();
                    String tl = tgl.getText().toString();

                    int mk = Integer.parseInt(mkn);
                    int mm = Integer.parseInt(mnm);
                    int tr = Integer.parseInt(tdr);
                    int o = Integer.parseInt(or);

                    String keterangan;

                    if(mk >= 3 && mm >= 4 && tr >= 7 && o >= 30) {
                        keterangan = "Sudah menerapkan aktivitas yang sehat";
                    } else {
                        keterangan = "Belum menerapkan aktivitas yang sehat";
                    }

                    addDataToFirebase(mkn, mnm, tdr, or, tl,  keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void addDataToFirebase(String makan, String minum, String tidur, String olahraga, String tanggal, String ket){
        String id = myRef.push().getKey();
        AktivitasHarian aktivitasHarian = new AktivitasHarian(id, makan, minum, tidur, olahraga, tanggal, ket);

        myRef.child(id).setValue(aktivitasHarian).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                AktivitasHarian username = dataSnapshot.getValue(AktivitasHarian.class);

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AktivitasHarian value = snapshot.getValue(AktivitasHarian.class);
                    list.add(0,value);
                }
                recyclerViewAktivitas.setAdapter(new AktivitasAdapter(aktivitas.this,list));
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

    public void backToPageMonitoringd(View view) {
        finish();
    }

    public void toAktivitasGrafik(View view) {
        Intent intent = new Intent(this, aktivitas_grafik.class);
        startActivity(intent);
    }

}