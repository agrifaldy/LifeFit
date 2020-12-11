package com.example.lifefit.IndeksMassaTubuh;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BmiAdapter extends RecyclerView.Adapter<BmiAdapter.ViewHolder> {

    private Context context;
    private List<Bmi> list;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Bmi");
    private FirebaseAuth mAuth;
    private CardView hasilIbm;
    private Activity activity;

    public LinearLayout.LayoutParams params;

//    private OnCallBack onCallBack;

    public BmiAdapter(Context context, List<Bmi> list) {
        this.context = context;
        this.list = list;
    }

//    public void setOnCallBack(OnCallBack onCallBack) {
//        this.onCallBack = onCallBack;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        hasilIbm = parent.findViewById(R.id.cv_hasiIbm);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_indeks_massa_tubuh_item, parent, false);
        return new ViewHolder(view);
    }

    ArrayList<String> keylist = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())){

                        }

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah anda yakin mau menghapus?");
                builder.show();
            }
        });

//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onCallBack.onButtonEditClick(list.get(position));
//            }
//        });

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
            holder.tv_berat.setVisibility(View.INVISIBLE);
            holder.tv_tinggi.setVisibility(View.INVISIBLE);
            holder.tv_tanggal.setVisibility(View.INVISIBLE);
            holder.tv_imt.setVisibility(View.INVISIBLE);
            holder.tv_keterangan.setVisibility(View.INVISIBLE);
            hasilIbm.setVisibility(View.INVISIBLE);
            hasilIbm.setLayoutParams(params);
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
        private CardView btnDelete, btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            params = new LinearLayout.LayoutParams(0, 0);
            tv_berat = itemView.findViewById(R.id.tv_berat);
            tv_tinggi = itemView.findViewById(R.id.tv_tinggi);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_imt = itemView.findViewById(R.id.tv_imt);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);
            btnDelete = itemView.findViewById(R.id.btn_deleteBmi);
            btnEdit = itemView.findViewById(R.id.btn_editBmi);
            hasilIbm = itemView.findViewById(R.id.cv_hasiIbm);


            /**if (mAuth.getCurrentUser().getUid().equals(list.get(position).getId())) {
                hasilIbm.setVisibility(View.VISIBLE);
            } else {
                hasilIbm.setVisibility(View.GONE);
            }**/

        }


    }

//    public interface OnCallBack {
//        void onButtonEditClick(Bmi bmi);
//    }

}
