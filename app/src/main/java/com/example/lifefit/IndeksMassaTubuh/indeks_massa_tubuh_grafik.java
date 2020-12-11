package com.example.lifefit.IndeksMassaTubuh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.lifefit.R;
import com.example.lifefit.IndeksMassaTubuh.Bmi;
import com.example.lifefit.IndeksMassaTubuh.BmiAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class indeks_massa_tubuh_grafik extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<indeks_massa_tubuh_grafik> list;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_indeks_massa_tubuh_grafik);
        showBartChart();

    }

    /**@Override
    public int getItemCount() {
        return list.size();
    }**/

    private void showBartChart () {

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Object fieldsObj = new Object();
        HashMap fldObj;

        mDatabase.child("Bmi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                Object fieldsObj = new Object();
                Object fieldsOb2 = new Object();
                Object fieldsOb3 = new Object();
                HashMap fldObj;
                HashMap value;
                //String[] tinggiBadan;


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //indeks_massa_tubuh_grafik value = snapshot.getValue(indeks_massa_tubuh_grafik.class);
                    //list.add(0,value);

                    fldObj = (HashMap)snapshot.getValue(fieldsObj.getClass());
                    value = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());

                    fieldsOb2.toString();

                    //list.add(fldObj);
                    fldObj.get("");
                    value.get("nodeId");



                    String now = mDatabase.child("wow").getKey();

                    /**for (int i = 0; i < value.size(); i++) {
                        tinggiBadan = (HashMap) i;
                    }

                    tinggiBadan = (HashMap) value.;**/

                            if(mAuth.getCurrentUser().getUid().equals(fldObj.get("id").toString())) {

                                String tinggi = fldObj.get("tinggi").toString();
                                String tanggal = fldObj.get("tanggal").toString();
                                String berat = fldObj.get("berat").toString();
                                String imt = fldObj.get("imt").toString();

                                list.add(value);

                                //String[] tinggiBadan = Arrays.copyOf(( tinggiBadanValue, tinggiBadanValue.length, String[].class);

                                BarChart barChart = (BarChart) findViewById(R.id.barChart);

                                ArrayList<BarEntry> entries = new ArrayList<>();
                                entries.add(new BarEntry(Float.parseFloat(tinggi), Float.parseFloat(tinggi), "Tinggi Badan"));
                                entries.add(new BarEntry(Float.parseFloat(tinggi), Float.parseFloat(berat), "Berat Badan"));
                                entries.add(new BarEntry(Float.parseFloat(tinggi), Float.parseFloat(imt), "IMT"));

                                /**for(int i = 0; i < tinggi.toString().length(); i++){
                                 entries.add(new Entry(tinggi[i] ,i));
                                 }

                                 for(int i = 1; i < xDataL.length; i++){
                                 entries.add(xDataL[i]);
                                 }**/

                                ArrayList<String> label = new ArrayList<>();
                                label.add("Tinggi");
                                label.add("Berat");
                                label.add("Imt");

                                BarDataSet bardataset = new BarDataSet(entries, tanggal);

                                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                                bardataset.setValueTextColor(Color.BLACK);
                                bardataset.setValueTextSize(16f);
                                BarData barData = new BarData(bardataset);
                                barChart.setFitBars(true);
                                barChart.setData(barData); // set the data and list of labels into chart
                                barChart.getDescription().setText(tanggal);


                                barChart.animateY(2000, Easing.EaseInOutQuad);
                            }
                    }



                    //list.add(value);


                    //list.get(0);

                    /**if(mAuth.getCurrentUser().getUid().equals(fldObj.get("id").toString())) {



                        String tinggi = fldObj.get("tinggi").toString();
                        String tanggal = fldObj.get("tanggal").toString();


                        //String[] tinggiBadan = Arrays.copyOf(( tinggiBadanValue, tinggiBadanValue.length, String[].class);

                        BarChart barChart = (BarChart) findViewById(R.id.barChart);

                        ArrayList<BarEntry> entries = new ArrayList<>();
                        entries.add(new BarEntry(Float.parseFloat(tinggi), Float.parseFloat(tinggi)));

                        for(int i = 0; i < tinggi.toString().length(); i++){
                         entries.add(new Entry(tinggi[i] ,i));
                         }

                         for(int i = 1; i < xDataL.length; i++){
                         entries.add(xDataL[i]);
                         }

                        BarDataSet bardataset = new BarDataSet(entries, tanggal);

                        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        bardataset.setValueTextColor(Color.BLACK);
                        bardataset.setValueTextSize(16f);
                        BarData barData = new BarData(bardataset);
                        barChart.setFitBars(true);
                        barChart.setData(barData); // set the data and list of labels into chart
                        barChart.getDescription().setText("Bar chart");


                        barChart.animateY(2000, Easing.EaseInOutQuad);
                    }**/

                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



        /**entries.add(new BarEntry(2015, 475));
        entries.add(new BarEntry(2016, 508));
        entries.add(new BarEntry(2017, 660));
        entries.add(new BarEntry(2018, 550));
        entries.add(new BarEntry(2019, 563));**/



        /**ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");**/


    }

}