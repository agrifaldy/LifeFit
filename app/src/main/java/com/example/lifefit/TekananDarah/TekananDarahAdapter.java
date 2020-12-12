package com.example.lifefit.TekananDarah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TekananDarahAdapter extends RecyclerView.Adapter<TekananDarahAdapter.ViewHolder> {

    private Context context;
    private List<TekananDarah> list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView hasilTensi;
    public LinearLayout.LayoutParams params;

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
    public void onBindViewHolder(@NonNull TekananDarahAdapter.ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tekananAtas, tv_tekananBawah, tv_tanggalTensi, tv_keteranganTensi;
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
}
