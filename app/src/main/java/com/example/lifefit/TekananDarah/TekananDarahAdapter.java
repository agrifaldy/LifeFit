package com.example.lifefit.TekananDarah;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class TekananDarahAdapter extends RecyclerView.Adapter<TekananDarahAdapter.ViewHolder> {

    private Context context;
    private List<TekananDarah> list;
    private ArrayList<TekananDarah> listt;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference  ref = FirebaseDatabase.getInstance().getReference();
    private CardView hasilTensi;
    public LinearLayout.LayoutParams params;
    OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public TekananDarahAdapter(Context context, List<TekananDarah> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TekananDarahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference("TekananDarah");
        mAuth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_tensi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TekananDarahAdapter.ViewHolder holder, final int position) {
        holder.tv_tekananAtas.setText(list.get(position).getTekananAtas());
        holder.tv_tekananBawah.setText(list.get(position).getTekananBawah());
        holder.tv_tanggalTensi.setText(list.get(position).getTanggal());
        holder.tv_keteranganTensi.setText(list.get(position).getKeterangan());

        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())){
            holder.tv_tekananAtas.setVisibility(View.VISIBLE);
            holder.tv_tekananBawah.setVisibility(View.VISIBLE);
            holder.tv_tanggalTensi.setVisibility(View.VISIBLE);
            holder.tv_keteranganTensi.setVisibility(View.VISIBLE);
            hasilTensi.setVisibility(View.VISIBLE);
        } else {
            holder.tv_tekananAtas.setVisibility(View.INVISIBLE);
            holder.tv_tekananBawah.setVisibility(View.INVISIBLE);
            holder.tv_tanggalTensi.setVisibility(View.INVISIBLE);
            holder.tv_keteranganTensi.setVisibility(View.INVISIBLE);
            hasilTensi.setVisibility(View.INVISIBLE);
            hasilTensi.setLayoutParams(params);
        }

//        CardView delButton = holder.itemView.findViewById(R.id.deleteTensi);
//
//        TekananDarah tekananDarah = list.get(position);
//        final String tensi = tekananDarah.getId();
//        final Query applesQuery = ref.child("TekananDarah").orderByChild("id").equalTo(tensi);
//
//        delButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                            appleSnapshot.getRef().removeValue();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e(TAG, "onCancelled", databaseError.toException());
//                    }
//                });
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public TekananDarah getItemCount(int position) {
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tekananAtas, tv_tekananBawah, tv_tanggalTensi, tv_keteranganTensi;
        CardView delButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            params = new LinearLayout.LayoutParams(0,0);
            tv_tekananAtas = itemView.findViewById(R.id.tv_tekanan_atas);
            tv_tekananBawah = itemView.findViewById(R.id.tv_tekanan_bawah);
            tv_tanggalTensi = itemView.findViewById(R.id.tv_tanggal_tensi);
            tv_keteranganTensi = itemView.findViewById(R.id.tv_keterangan_tensi);

            hasilTensi = itemView.findViewById(R.id.cv_hasilTensi);
        }
    }

    public interface OnCallBack{
        void onTblHapus(TekananDarah tekananDarah);
    }

    public interface FirebaseDataListener{
        void onDeleteData(TekananDarah tekananDarah, int position);
    }
}
