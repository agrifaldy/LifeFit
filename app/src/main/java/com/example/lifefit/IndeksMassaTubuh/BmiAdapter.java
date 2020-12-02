package com.example.lifefit.IndeksMassaTubuh;


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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class BmiAdapter extends RecyclerView.Adapter<BmiAdapter.ViewHolder> {

    private Context context;
    private List<Bmi> list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView hasilIbm;

    public BmiAdapter(Context context, List<Bmi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_indeks_massa_tubuh_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_berat.setText(list.get(position).getBerat());
        holder.tv_tinggi.setText(list.get(position).getTinggi());
        holder.tv_tanggal.setText(list.get(position).getTanggal());
        holder.tv_imt.setText(formatNumberCurrency(list.get(position).getImt()));
        holder.tv_keterangan.setText(list.get(position).getKeterangan());
        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())){
            holder.tv_berat.setVisibility(View.VISIBLE);
            holder.tv_tinggi.setVisibility(View.VISIBLE);
            holder.tv_tanggal.setVisibility(View.VISIBLE);
            holder.tv_imt.setVisibility(View.VISIBLE);
            holder.tv_keterangan.setVisibility(View.VISIBLE);
            hasilIbm.setVisibility(View.VISIBLE);
        } else {
            holder.tv_berat.setVisibility(View.GONE);
            holder.tv_tinggi.setVisibility(View.GONE);
            holder.tv_tanggal.setVisibility(View.GONE);
            holder.tv_imt.setVisibility(View.GONE);
            holder.tv_keterangan.setVisibility(View.GONE);
            hasilIbm.setVisibility(View.GONE);
        }
    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("00.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
            return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int position;

        private TextView tv_berat, tv_tinggi, tv_tanggal, tv_imt, tv_keterangan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_berat = itemView.findViewById(R.id.tv_berat);
            tv_tinggi = itemView.findViewById(R.id.tv_tinggi);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_imt = itemView.findViewById(R.id.tv_imt);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);
            hasilIbm = itemView.findViewById(R.id.cv_hasiIbm);

            /**if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())) {
                hasilIbm.setVisibility(View.VISIBLE);
            } else {
                hasilIbm.setVisibility(View.GONE);
            }**/

        }
    }
}
