package com.example.lifefit.Aktivitas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.DenyutJantung.DenyutJantung;
import com.example.lifefit.DenyutJantung.DenyutJantungAdapter;
import com.example.lifefit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.ViewHolder> {

    private Context context;
    private List<AktivitasHarian> list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView hasilAktivitas;
    public LinearLayout.LayoutParams params;

    public AktivitasAdapter(Context context, List<AktivitasHarian> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AktivitasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Aktivitas");
        mAuth = FirebaseAuth.getInstance();
        hasilAktivitas = parent.findViewById(R.id.cv_Aktivitas);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_aktivitas_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AktivitasAdapter.ViewHolder holder, int position) {
        holder.tv_aktivitas_makan.setText(list.get(position).getMakan());
        holder.tv_aktivitas_minum.setText(list.get(position).getMinum());
        holder.tv_aktivitas_tidur.setText(list.get(position).getTidur());
        holder.tv_aktivitas_olahraga.setText(list.get(position).getOlahraga());
        holder.tv_aktivitas_tanggal.setText(list.get(position).getTanggal());
        holder.tv_aktivitas_keterangan.setText(list.get(position).getKeterangan());


        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())){
            holder.tv_aktivitas_makan.setVisibility(View.VISIBLE);
            holder.tv_aktivitas_minum.setVisibility(View.VISIBLE);
            holder.tv_aktivitas_tidur.setVisibility(View.VISIBLE);
            holder.tv_aktivitas_olahraga.setVisibility(View.VISIBLE);
            holder.tv_aktivitas_tanggal.setVisibility(View.VISIBLE);
            holder.tv_aktivitas_keterangan.setVisibility(View.VISIBLE);
            hasilAktivitas.setVisibility(View.VISIBLE);
        } else {
            holder.tv_aktivitas_makan.setVisibility(View.INVISIBLE);
            holder.tv_aktivitas_minum.setVisibility(View.INVISIBLE);
            holder.tv_aktivitas_tidur.setVisibility(View.INVISIBLE);
            holder.tv_aktivitas_olahraga.setVisibility(View.INVISIBLE);
            holder.tv_aktivitas_tanggal.setVisibility(View.INVISIBLE);
            holder.tv_aktivitas_keterangan.setVisibility(View.INVISIBLE);
            hasilAktivitas.setVisibility(View.INVISIBLE);
            hasilAktivitas.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_aktivitas_makan, tv_aktivitas_minum, tv_aktivitas_tidur, tv_aktivitas_olahraga,
                tv_aktivitas_keterangan, tv_aktivitas_tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_aktivitas_makan = itemView.findViewById(R.id.tv_aktivitas_makan);
            tv_aktivitas_minum = itemView.findViewById(R.id.tv_aktivitas_minum);
            tv_aktivitas_tidur = itemView.findViewById(R.id.tv_aktivitas_tidur);
            tv_aktivitas_olahraga = itemView.findViewById(R.id.tv_aktivitas_olahraga);
            tv_aktivitas_keterangan = itemView.findViewById(R.id.tv_aktivitas_keterangan);
            tv_aktivitas_tanggal = itemView.findViewById(R.id.tv_aktivitas_tanggal);
            hasilAktivitas = itemView.findViewById(R.id.cv_Aktivitas);
        }
    }
}
