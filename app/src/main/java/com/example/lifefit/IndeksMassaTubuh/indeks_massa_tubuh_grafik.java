package com.example.lifefit.IndeksMassaTubuh;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.lifefit.R;
import com.example.lifefit.IndeksMassaTubuh.Bmi;
import com.example.lifefit.IndeksMassaTubuh.BmiAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class indeks_massa_tubuh_grafik extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Bmi");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //private List<indeks_massa_tubuh_grafik> list;
    BarChart barChart;
    private List<Bmi> list = new ArrayList<>();
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_indeks_massa_tubuh_grafik);
        showBartChart();

    }

    private void showBartChart () {

        //final ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Object fieldsObj = new Object();
        HashMap fldObj;

        mDatabase.orderByValue().addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    Bmi value = snapshot.getValue(Bmi.class);
                    //Bmi valueTinggi = dataSnapshot.getValue(Bmi.class);
                    list.add(0, value);

                    /**if (mAuth.getCurrentUser().getUid().equals(value.getId())) {
                     values = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());
                     values.size();
                     }**/

                    BarChart barChart = (BarChart) findViewById(R.id.barChart);
                    BarChart barChart2 = (BarChart) findViewById(R.id.barChart2);
                    BarChart barChart3 = (BarChart) findViewById(R.id.barChart3);


                    ArrayList<BarEntry> entries = new ArrayList<>();
                    ArrayList<BarEntry> entries2 = new ArrayList<>();
                    ArrayList<BarEntry> entries3 = new ArrayList<>();
                    //entries.add(new BarEntry(1, Float.parseFloat(listData.get(0).getTinggi()), "Tinggi Badan"));
                    //entries.add(new BarEntry(2, Float.parseFloat(berat), "Berat Badan"));
                    //entries.add(new BarEntry(3, Float.parseFloat(imt), "IMT"));

                    //for(int i = 0; i < listData.get(0).getTinggi().length() && i < listData.get(0).getBerat().length() && i < listData.get(0).getImt().length(); i++)

                        for(int i = 0; i < list.size(); i++){

                            if (mAuth.getCurrentUser().getUid().equals(list.get(i).getId())) {

                                entries.add(new BarEntry(Float.parseFloat(list.get(i).getTinggi()), Float.parseFloat(list.get(i).getTinggi()), list.get(i).getTanggal()));
                                entries2.add(new BarEntry(Float.parseFloat(list.get(i).getBerat()), Float.parseFloat(list.get(i).getBerat()), list.get(i).getTanggal()));
                                entries3.add(new BarEntry(Float.parseFloat(list.get(i).getImt()), Float.parseFloat(list.get(i).getImt()), list.get(i).getTanggal()));


                                /**ArrayList<String> label = new ArrayList<>();
                                label.add(list.get(i).getTanggal());
                                ArrayList<String> label2 = new ArrayList<>();
                                label2.add(list.get(i).getTanggal());
                                ArrayList<String> label3 = new ArrayList<>();
                                label3.add(list.get(i).getTanggal());**/


                                //BarDataSet bardataset = new BarDataSet(entries, label.toString());
                                BarDataSet bardataset = new BarDataSet(entries, "Tinggi");
                                BarDataSet bardataset2 = new BarDataSet(entries2, "Berat");
                                BarDataSet bardataset3 = new BarDataSet(entries3, "IMT");

                                //bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
                                bardataset.setColors(Color.parseColor("#424874"));
                                bardataset.setValueTextColor(Color.BLACK);
                                bardataset.setValueTextSize(16f);

                                //bardataset2.setColors(ColorTemplate.JOYFUL_COLORS);
                                bardataset2.setColors(Color.parseColor("#424874"));
                                bardataset2.setValueTextColor(Color.BLACK);
                                bardataset2.setValueTextSize(16f);

                                //bardataset3.setColors(ColorTemplate.JOYFUL_COLORS);
                                bardataset3.setColors(Color.parseColor("#424874"));
                                bardataset3.setValueTextColor(Color.BLACK);
                                bardataset3.setValueTextSize(16f);


                                BarData barData = new BarData(bardataset);
                                BarData barData2 = new BarData(bardataset2);
                                BarData barData3 = new BarData(bardataset3);

                                barChart.setFitBars(true);
                                barChart.setData(barData);
                                barChart.getDescription().setText("Tanggal");
                                barChart.animateY(2000, Easing.EaseInOutQuad);
                                barChart.setTouchEnabled(true);
                                barChart.setDrawGridBackground(false);



                                barChart2.setFitBars(true);
                                barChart2.setData(barData2);
                                barChart2.getDescription().setText("Tanggal");
                                barChart2.animateY(2000, Easing.EaseInOutQuad);
                                barChart2.setTouchEnabled(true);

                                barChart3.setFitBars(true);
                                barChart3.setData(barData3);
                                barChart3.getDescription().setText("Tanggal");
                                barChart3.animateY(2000, Easing.EaseInOutQuad);
                                barChart3.setTouchEnabled(true);

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

    /**@Override
    public int getItemCount() {
        return list.size();
    }**/

}