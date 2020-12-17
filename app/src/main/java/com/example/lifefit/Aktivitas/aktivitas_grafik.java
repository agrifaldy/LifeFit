package com.example.lifefit.Aktivitas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.lifefit.DenyutJantung.DenyutJantung;
import com.example.lifefit.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class aktivitas_grafik extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Aktivitas");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //private List<indeks_massa_tubuh_grafik> list;
    BarChart barChart;
    private List<AktivitasHarian> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitas_grafik);
        showBartChart();
    }

    private void showBartChart () {

        //final ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Object fieldsObj = new Object();
        HashMap fldObj;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                Object fieldsObj = new Object();
                Object fieldsOb2 = new Object();
                Object fieldsOb3 = new Object();
                HashMap fldObj;
                HashMap values;
                HashMap tinggiBadanValue;
                Object tinggiBadan;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    fldObj = (HashMap)snapshot.getValue(fieldsObj.getClass());
                    values = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());
                    /**for (int x = 0; x < values.size(); x++) {
                     Bmi value = snapshot.getValue(Bmi.class);
                     Bmi valueTinggi = dataSnapshot.getValue(Bmi.class);
                     list.add(x, value);
                     }**/
                    //tinggiBadanValue = (HashMap) dataSnapshot.getValue(fieldsOb3.getClass());

                    /**for ( Object key : values.keySet() ) {

                     }**/



                    values.get("");

                    fieldsOb2.toString();

                    //list.add(fldObj);
                    fldObj.get("");
                    //value.get("");


                    //list.clear();
                    AktivitasHarian value = snapshot.getValue(AktivitasHarian.class);
                    //Bmi valueTinggi = dataSnapshot.getValue(Bmi.class);
                    list.add(0, value);

                    /**if (mAuth.getCurrentUser().getUid().equals(value.getId())) {
                     values = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());
                     values.size();
                     }**/

                    BarChart barChart = (BarChart) findViewById(R.id.barChartAktivitas);

                    ArrayList<BarEntry> entries = new ArrayList<>();
                    //entries.add(new BarEntry(1, Float.parseFloat(listData.get(0).getTinggi()), "Tinggi Badan"));
                    //entries.add(new BarEntry(2, Float.parseFloat(berat), "Berat Badan"));
                    //entries.add(new BarEntry(3, Float.parseFloat(imt), "IMT"));

                    //for(int i = 0; i < listData.get(0).getTinggi().length() && i < listData.get(0).getBerat().length() && i < listData.get(0).getImt().length(); i++)

                    for(int i = 0; i < list.size(); i++){

                        if (mAuth.getCurrentUser().getUid().equals(list.get(i).getId())) {

                            entries.add(new BarEntry(i, Float.parseFloat(list.get(i).getMakan()), "Makan"));

                            ArrayList<String> label = new ArrayList<>();
                            label.add("Aktifitas");

                            BarDataSet bardataset = new BarDataSet(entries, label.toString());

                            bardataset.setColors(Color.parseColor("#424874"));
                            bardataset.setValueTextColor(Color.BLACK);
                            bardataset.setValueTextSize(16f);


                            BarData barData = new BarData(bardataset);

                            barChart.setFitBars(true);
                            barChart.setData(barData);
                            barChart.getDescription().setText("Tanggal");
                            barChart.animateY(2000, Easing.EaseInOutQuad);
                            barChart.setTouchEnabled(true);

                        }



                        /**if (mAuth.getCurrentUser().getUid().equals(listData.get(0).getId()) && mAuth.getCurrentUser().getUid().equals(value.getId())) {
                         barChart.setData(barData); // set the data and list of labels into chart
                         bardataset.setVisible(true);

                         }**/





                        /**} else {
                         bardataset.setVisible(false);
                         }**/


                    }




                    //}

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }
}