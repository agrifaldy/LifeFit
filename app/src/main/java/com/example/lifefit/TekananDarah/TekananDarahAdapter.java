package com.example.lifefit.TekananDarah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

public class TekananDarahAdapter extends RecyclerView.Adapter<TekananDarahAdapter.ViewHolder> {

    private Context context;
    private List<TekananDarah> list;
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

        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getKey())){
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

        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String[] action = {"Edit", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                onCallBack.onButtonEditClick(list.get(position));
                                break;
                            case 1:
                                onCallBack.onButtonDeleteClick(list.get(position));
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public TekananDarah getIdItem(int position) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tekananAtas, tv_tekananBawah, tv_tanggalTensi, tv_keteranganTensi;
        CardView delButton;
        private LinearLayout ListItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            params = new LinearLayout.LayoutParams(0,0);
            tv_tekananAtas = itemView.findViewById(R.id.tv_tekanan_atas);
            tv_tekananBawah = itemView.findViewById(R.id.tv_tekanan_bawah);
            tv_tanggalTensi = itemView.findViewById(R.id.tv_tanggal_tensi);
            tv_keteranganTensi = itemView.findViewById(R.id.tv_keterangan_tensi);
            ListItem = itemView.findViewById(R.id.ListItem);
            hasilTensi = itemView.findViewById(R.id.cv_hasilTensi);
        }
    }


    public interface OnCallBack{
        void onButtonDeleteClick(TekananDarah tekananDarah);
        void onButtonEditClick(TekananDarah tekananDarah);
    }

}
