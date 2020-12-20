package com.example.lifefit.DenyutJantung;

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

import com.example.lifefit.IndeksMassaTubuh.page_monitoring;
import com.example.lifefit.R;
import com.example.lifefit.TekananDarah.TekananDarah;
import com.example.lifefit.TekananDarah.TekananDarahAdapter;
import com.example.lifefit.TekananDarah.tensi;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class denyut_jantung extends AppCompatActivity {

    private FloatingActionButton btnAddDenyutJantung;
    private RecyclerView recyclerViewDenyutJantung;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("DenyutJantung");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<DenyutJantung> list = new ArrayList<>();
    private int year, month, day;
    DenyutJantungAdapter adapter;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_denyut_jantung);

        btnAddDenyutJantung = findViewById(R.id.btn_add_denyut_jantung);
        recyclerViewDenyutJantung = findViewById(R.id.recycler_view_denyut_jantung);

        recyclerViewDenyutJantung.setLayoutManager(new LinearLayoutManager(this));

        //button click
        btnAddDenyutJantung.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        readData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewDenyutJantung);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(denyut_jantung.this);
            builder.setTitle("Delete");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DenyutJantung denyutJantung = list.get(position);
                    String denyut = denyutJantung.getId();
                    myRef.child(denyut).removeValue();
                    Toast.makeText(denyut_jantung.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
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
        dialog.setContentView(R.layout.activity_denyut_jantung_input);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton btnClose = dialog.findViewById(R.id.btn_close_denyut_jantung);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText dj = dialog.findViewById(R.id.et_denyut_jantung);
        final EditText tgl = dialog.findViewById(R.id.et_tanggal_denyut_jantung);
        Button btnAdd = dialog.findViewById(R.id.btn_simpan_denyut_jantung);

        final Calendar calendar = Calendar.getInstance();

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(denyut_jantung.this, new DatePickerDialog.OnDateSetListener() {
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
                if (TextUtils.isEmpty(dj.getText())){
                    dj.setError("Tidak boleh kosong");
                }  else if (TextUtils.isEmpty(tgl.getText())){
                    tgl.setError("Tidak boleh kosong");
                } else {
                    String dtkJ = dj.getText().toString();
                    String tglT = tgl.getText().toString();

                    int a = Integer.parseInt(dtkJ);

                    String keterangan = null;

                    if(a < 59) {
                        keterangan = "Detak Jantung Tidak Normal";
                    }else if(a > 59 && a < 101) {
                        keterangan = "Detak Jantung Normal";
                    }else if(a > 100) {
                        keterangan = "Detak Jantung Tidak Normal";
                    }

                    addDataToFirebase(dtkJ, tglT, keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void addDataToFirebase(String dtkjntg, String Tanggal, String ket){
        String id = myRef.push().getKey();
        String key = mAuth.getCurrentUser().getUid();
        DenyutJantung denyutJantung = new DenyutJantung(id, key, dtkjntg, Tanggal, ket);

        myRef.child(id).setValue(denyutJantung).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    DenyutJantung value = snapshot.getValue(DenyutJantung.class);
                    list.add(0,value);
                }
                adapter = new DenyutJantungAdapter(denyut_jantung.this,list);
                recyclerViewDenyutJantung.setAdapter(adapter);

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
        adapter.setOnCallBack(new DenyutJantungAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(final DenyutJantung denyutJantung) {
                AlertDialog.Builder builder = new AlertDialog.Builder(denyut_jantung.this);
                builder.setTitle("Delete");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData(denyutJantung);
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
            public void onButtonEditClick(DenyutJantung denyutJantung) {
                showDialogUpdateData(denyutJantung);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDialogUpdateData(final DenyutJantung denyutJantung) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_denyut_jantung_input);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton btnClose = dialog.findViewById(R.id.btn_close_denyut_jantung);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText dj = dialog.findViewById(R.id.et_denyut_jantung);
        dj.setText(denyutJantung.getDenyutJantung());
        final EditText tgl = dialog.findViewById(R.id.et_tanggal_denyut_jantung);
        tgl.setText(denyutJantung.getTanggal());
        Button btnAdd = dialog.findViewById(R.id.btn_simpan_denyut_jantung);
        btnAdd.setText("Update");

        final Calendar calendar = Calendar.getInstance();

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(denyut_jantung.this, new DatePickerDialog.OnDateSetListener() {
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
                if (TextUtils.isEmpty(dj.getText())){
                    dj.setError("Tidak boleh kosong");
                }  else if (TextUtils.isEmpty(tgl.getText())){
                    tgl.setError("Tidak boleh kosong");
                } else {
                    String dtkJ = dj.getText().toString();
                    String tglT = tgl.getText().toString();

                    int a = Integer.parseInt(dtkJ);

                    String keterangan = null;

                    if(a < 59) {
                        keterangan = "Detak Jantung Tidak Normal";
                    }else if(a > 59 && a < 101) {
                        keterangan = "Detak Jantung Normal";
                    }else if(a > 100) {
                        keterangan = "Detak Jantung Tidak Normal";
                    }

                    updateData(denyutJantung, dtkJ, tglT, keterangan);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateData(DenyutJantung denyutJantung, String dtkJ, String tglT, String keterangan) {
        myRef.child(denyutJantung.getId()).child("denyutJantung").setValue(dtkJ);
        myRef.child(denyutJantung.getId()).child("tanggal").setValue(tglT);
        myRef.child(denyutJantung.getId()).child("keterangan").setValue(keterangan);
        Toast.makeText(getApplicationContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
    }

    private void deleteData(DenyutJantung denyutJantung) {
        myRef.child(denyutJantung.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToPageMonitoringb(View view) {
        finish();
    }

    public void toJantungGrafik(View view) {
        Intent intent = new Intent(this, denyut_jantung_grafik.class);
        startActivity(intent);
    }

}