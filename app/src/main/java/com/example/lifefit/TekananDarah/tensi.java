package com.example.lifefit.TekananDarah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import com.example.lifefit.R;
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

public class tensi extends AppCompatActivity {

    private FloatingActionButton btnAddTensi;
    private RecyclerView recyclerViewTensi;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("TekananDarah");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<TekananDarah> list = new ArrayList<>();
    private int year, month, day;

    TekananDarahAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tensi);

        btnAddTensi = findViewById(R.id.btn_add_tensi);
        recyclerViewTensi = findViewById(R.id.recycler_view_tensi);

        recyclerViewTensi.setLayoutManager(new LinearLayoutManager(this));

        adapter =  new TekananDarahAdapter(this, list);

        recyclerViewTensi.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerViewTensi);

        //button click
        btnAddTensi.setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(R.layout.activity_tensi_input);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton btnClose = dialog.findViewById(R.id.btn_close_tensi);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText ta = dialog.findViewById(R.id.et_tekanan_atas);
        final EditText tb = dialog.findViewById(R.id.et_tekanan_bawah);
        final EditText tgl = dialog.findViewById(R.id.et_tanggal_tensi);
        Button btnAdd = dialog.findViewById(R.id.btn_simpan_tensi);

        final Calendar calendar = Calendar.getInstance();

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(tensi.this, new DatePickerDialog.OnDateSetListener() {
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
                if (TextUtils.isEmpty(ta.getText())){
                    ta.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tb.getText())){
                    tb.setError("Tidak boleh kosong");
                } else if (TextUtils.isEmpty(tgl.getText())){
                    tgl.setError("Tidak boleh kosong");
                } else {
                    String tekA = ta.getText().toString();
                    String tekB = tb.getText().toString();
                    String tglT = tgl.getText().toString();

                    int a = Integer.parseInt(tekA);
                    int b = Integer.parseInt(tekB);

                    String keterangan = null;

                    if(a < 121 && b < 80) {
                        keterangan = "Tekanan darah normal";
                    }else if(a > 120 && a < 140 || b > 79 && b < 90) {
                        keterangan = "Pra Hipertensi";
                    }else if(a > 139 && a < 160 || b > 89 && b < 100) {
                        keterangan = "Hipertensi tingkat I";
                    }else if(a > 159 && a < 181 || b > 99 && b < 111) {
                        keterangan = "Hipertensi tingkat II";
                    }else if(a > 181 || b > 110 ) {
                        keterangan = "Hipertensi Krisis";
                    }

                    addDataToFirebase(tekA, tekB, tglT, keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void addDataToFirebase(String tknanA, String tknanB, String Tanggal, String ket){
        String id = myRef.push().getKey();
        TekananDarah tekananDarah = new TekananDarah(id, tknanA, tknanB, Tanggal, ket);

        myRef.child(id).setValue(tekananDarah).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TekananDarah value = snapshot.getValue(TekananDarah.class);
                    list.add(0,value);
                }
                recyclerViewTensi.setAdapter(new TekananDarahAdapter(tensi.this,list));
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
}