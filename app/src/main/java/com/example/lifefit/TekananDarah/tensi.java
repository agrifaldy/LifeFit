package com.example.lifefit.TekananDarah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.lifefit.IndeksMassaTubuh.indeks_massa_tubuh_grafik;
import com.example.lifefit.IndeksMassaTubuh.page_monitoring;
import com.example.lifefit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class tensi extends AppCompatActivity {

    private FloatingActionButton btnAddTensi;
    private RecyclerView recyclerViewTensi;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("TekananDarah");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    private List<TekananDarah> list = new ArrayList<>();
    private int year, month, day;

    TekananDarahAdapter adapter;
    private SimpleDateFormat dateFormat;

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
        
        btnAddTensi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        readData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewTensi);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(tensi.this);
            builder.setTitle("Delete");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TekananDarah delete = list.get(position);
                    String del = delete.getId();
                    myRef.child(del).removeValue();
                    Toast.makeText(tensi.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }).setMessage("Apakah anda yakin mau menghapus data ini?");
            builder.show();
        }
    };

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
                        dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("id"));
                        String currentDate = dateFormat.format(calendar.getTime());
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
        String key = mAuth.getCurrentUser().getUid();
        TekananDarah tekananDarah = new TekananDarah(id, key, tknanA, tknanB, Tanggal, ket);

        myRef.child(id).setValue(tekananDarah).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan",Toast.LENGTH_SHORT).show();
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
                adapter = new TekananDarahAdapter(tensi.this,list);
                recyclerViewTensi.setAdapter(adapter);

                setClick();

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

    private void setClick() {
        adapter.setOnCallBack(new TekananDarahAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(final TekananDarah tekananDarah) {
                AlertDialog.Builder builder = new AlertDialog.Builder(tensi.this);
                builder.setTitle("Delete");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData(tekananDarah);
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah anda yakin mau menghapus data ini?");
                builder.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onButtonEditClick(TekananDarah tekananDarah) {
                showDialogUpdateData(tekananDarah);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDialogUpdateData(final TekananDarah tekananDarah) {
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
        ta.setText(tekananDarah.getTekananAtas());
        final EditText tb = dialog.findViewById(R.id.et_tekanan_bawah);
        tb.setText(tekananDarah.getTekananBawah());
        final EditText tgl = dialog.findViewById(R.id.et_tanggal_tensi);
        tgl.setText(tekananDarah.getTanggal());
        Button btnAdd = dialog.findViewById(R.id.btn_simpan_tensi);
        btnAdd.setText("Update");

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

                    updateData(tekananDarah, tekA, tekB, tglT, keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateData(TekananDarah tekananDarah, String tekA, String tekB, String tglT, String keterangan) {
        myRef.child(tekananDarah.getId()).child("tekananAtas").setValue(tekA);
        myRef.child(tekananDarah.getId()).child("tekananBawah").setValue(tekB);
        myRef.child(tekananDarah.getId()).child("tanggal").setValue(tglT);
        myRef.child(tekananDarah.getId()).child("keterangan").setValue(keterangan);
        Toast.makeText(getApplicationContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
    }

    private void deleteData(TekananDarah tekananDarah) {
        myRef.child(tekananDarah.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToPageMonitoringc(View view) {
        finish();
    }

    public void toTensiGrafik(View view) {
        Intent intent = new Intent(this, tensi_grafik.class);
        startActivity(intent);
    }

}