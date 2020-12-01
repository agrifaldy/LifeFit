package com.example.lifefit.IndeksMassaTubuh;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class BmiAdapter extends RecyclerView.Adapter<BmiAdapter.ViewHolder> {

    private Context context;
    private List<Bmi> list;

    public BmiAdapter(Context context, List<Bmi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        private TextView tv_berat, tv_tinggi, tv_tanggal, tv_imt, tv_keterangan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_berat = itemView.findViewById(R.id.tv_berat);
            tv_tinggi = itemView.findViewById(R.id.tv_tinggi);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_imt = itemView.findViewById(R.id.tv_imt);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);
        }
    }
}
