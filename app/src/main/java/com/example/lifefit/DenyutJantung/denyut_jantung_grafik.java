package com.example.lifefit.DenyutJantung;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.lifefit.R;
import com.example.lifefit.TekananDarah.TekananDarah;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class denyut_jantung_grafik extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("DenyutJantung");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //private List<indeks_massa_tubuh_grafik> list;
    BarChart barChart;
    private List<DenyutJantung> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denyut_jantung_grafik);
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
                    DenyutJantung value = snapshot.getValue(DenyutJantung.class);
                    //Bmi valueTinggi = dataSnapshot.getValue(Bmi.class);
                    list.add(0, value);

                    /**if (mAuth.getCurrentUser().getUid().equals(value.getId())) {
                     values = (HashMap)dataSnapshot.getValue(fieldsOb2.getClass());
                     values.size();
                     }**/

                    BarChart barChart = (BarChart) findViewById(R.id.barChartJantung);

                    ArrayList<BarEntry> entries = new ArrayList<>();
                    //entries.add(new BarEntry(1, Float.parseFloat(listData.get(0).getTinggi()), "Tinggi Badan"));
                    //entries.add(new BarEntry(2, Float.parseFloat(berat), "Berat Badan"));
                    //entries.add(new BarEntry(3, Float.parseFloat(imt), "IMT"));

                    //for(int i = 0; i < listData.get(0).getTinggi().length() && i < listData.get(0).getBerat().length() && i < listData.get(0).getImt().length(); i++)

                    for(int i = 0; i < list.size(); i++){

                        if (mAuth.getCurrentUser().getUid().equals(list.get(i).getKey())) {


                            //String string = list.get(0).getTanggal();
                            DateFormat formatTanggal = new SimpleDateFormat("ddMMyyyy");
                            Date date = null;
                            try {
                                date = new SimpleDateFormat("EEEE, d MMMM", new Locale("id")).parse(list.get(i).getTanggal().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String tanggal = formatTanggal.format(date);

                            entries.add(new BarEntry(Integer.parseInt(tanggal)/1000000, Float.parseFloat(list.get(i).getDenyutJantung()), list.get(i).getTanggal()));


                            final ArrayList<String> label = new ArrayList<>();
                            label.add(list.get(i).getTanggal());


                            //BarDataSet bardataset = new BarDataSet(entries, label.toString());
                            BarDataSet bardataset = new BarDataSet(entries, "Denyut Jantung");

                            //bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
                            bardataset.setColors(Color.parseColor("#424874"));
                            bardataset.setValueTextColor(Color.BLACK);
                            bardataset.setValueTextSize(16f);


                            BarData barData = new BarData(bardataset);

                            barChart.setFitBars(true);
                            barChart.setData(barData);
                            barChart.getDescription().setText("");
                            barChart.animateY(2000, Easing.EaseInOutQuad);
                            barChart.setTouchEnabled(true);
                            barChart.setDrawGridBackground(false);
                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setGranularity(1f);
                            xAxis.setCenterAxisLabels(false);
                            xAxis.setEnabled(true);
                            xAxis.setDrawGridLines(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            //xAxis.setAxisMinimum(1);
                            //xAxis.setAxisMaximum(31);
                            xAxis.setLabelCount(Integer.parseInt(tanggal)/1000000);
                            /**\xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                            return label.get((int)value);
                            }
                            });
                             final int finalI = i;
                             xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                            return list.get(finalI).getTanggal();
                            }
                            });**/

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