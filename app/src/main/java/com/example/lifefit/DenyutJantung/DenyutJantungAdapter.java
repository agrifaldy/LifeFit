package com.example.lifefit.DenyutJantung;

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
import com.example.lifefit.TekananDarah.TekananDarah;
import com.example.lifefit.TekananDarah.TekananDarahAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DenyutJantungAdapter extends RecyclerView.Adapter<DenyutJantungAdapter.ViewHolder> {

    private Context context;
    private List<DenyutJantung> list;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CardView hasilDenyutJantung;
    public LinearLayout.LayoutParams params;
    DenyutJantungAdapter.OnCallBack onCallBack;

    public void setOnCallBack(DenyutJantungAdapter.OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public DenyutJantungAdapter(Context context, List<DenyutJantung> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DenyutJantungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference("DenyutJantung");
        mAuth = FirebaseAuth.getInstance();
        hasilDenyutJantung = parent.findViewById(R.id.cv_denyutJantung);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_denyut_jantung_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenyutJantungAdapter.ViewHolder holder, final int position) {
        holder.tv_denyutJantung.setText(list.get(position).getDenyutJantung());
        holder.tv_tanggalDenyutJantung.setText(list.get(position).getTanggal());
        holder.tv_keteranganDenyutJantung.setText(list.get(position).getKeterangan());

        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getKey())){
            holder.tv_keteranganDenyutJantung.setVisibility(View.VISIBLE);
            holder.tv_tanggalDenyutJantung.setVisibility(View.VISIBLE);
            holder.tv_keteranganDenyutJantung.setVisibility(View.VISIBLE);
            hasilDenyutJantung.setVisibility(View.VISIBLE);
        } else {
            holder.tv_keteranganDenyutJantung.setVisibility(View.INVISIBLE);
            holder.tv_tanggalDenyutJantung.setVisibility(View.INVISIBLE);
            holder.tv_keteranganDenyutJantung.setVisibility(View.INVISIBLE);
            hasilDenyutJantung.setVisibility(View.INVISIBLE);
            hasilDenyutJantung.setLayoutParams(params);
        }

        holder.menu_dj.setOnLongClickListener(new View.OnLongClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_denyutJantung, tv_tanggalDenyutJantung, tv_keteranganDenyutJantung;
        private LinearLayout menu_dj;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            params = new LinearLayout.LayoutParams(0,0);
            tv_denyutJantung = itemView.findViewById(R.id.tv_denyut_jantung);
            tv_tanggalDenyutJantung = itemView.findViewById(R.id.tv_tanggal_denyut_jantung);
            tv_keteranganDenyutJantung = itemView.findViewById(R.id.tv_keterangan_denyut_jantung);
            hasilDenyutJantung = itemView.findViewById(R.id.cv_denyutJantung);

            menu_dj = itemView.findViewById(R.id.ll_menu_dj);
        }
    }

    public interface OnCallBack{
        void onButtonDeleteClick(DenyutJantung denyutJantung);
        void onButtonEditClick(DenyutJantung denyutJantung);
    }
}
