package com.example.lifefit.IndeksMassaTubuh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lifefit.Aktivitas.aktivitas;
import com.example.lifefit.MainActivity;
import com.example.lifefit.R;
import com.example.lifefit.TekananDarah.tensi;
import com.example.lifefit.DenyutJantung.denyut_jantung;
import com.example.lifefit.TekananDarah.tensi_grafik;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class page_monitoring extends AppCompatActivity {

    private TextView tanggalMonitoring;
    private TextView jamMonitoring;
    private TextView quote;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat hourFormat;
    private String date;
    private String hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page_monitoring);

        tanggalMonitoring = findViewById(R.id.tanggalMonitoring);
        jamMonitoring = findViewById(R.id.jamMonitoring);
        quote = findViewById(R.id.quote);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("id"));
        hourFormat = new SimpleDateFormat("h:mm a");
        date = dateFormat.format(calendar.getTime());
        hour = hourFormat.format(calendar.getTime());
        tanggalMonitoring.setText(date);
        jamMonitoring.setText(hour);

        int quoteHarian = calendar.get(Calendar.DAY_OF_WEEK);
        if(quoteHarian == 0) {
            quote.setText("\"Kesehatan adalah hubungan antara Anda dan tubuh Anda\". -Terri Guillemets");
        }else if(quoteHarian == 1){
            quote.setText("\"Waktu dan kesehatan adalah dua aset berharga yang tidak dikenali dan hargai sampai keduanya hilang\". -Denis Waitley");
        }else if(quoteHarian == 2){
            quote.setText("\"Harta sejati adalah kesehatan, bukan emas dan perak\". -Mahatma Gandhi");
        }else if(quoteHarian == 3){
            quote.setText("\"Air adalah kehidupan dan air bersih berarti kesehatan\". -Audrey Hepburn");
        }else if(quoteHarian == 4){
            quote.setText("\"Kesehatan selalu tampak berharga setelah kita kehilangannya\". -Jonathan Swift");
        }else if(quoteHarian == 5){
            quote.setText("\"Makan dengan sehat, tidur dengan baik, bernapas dengan dalam, bergerak dengan harmoni\". -Jean Pierre Barral");
        }else if(quoteHarian == 6){
            quote.setText("\"Jaga tubuhmu. Itulah satu-satunya tempat yang kamu miliki untuk hidup\". -Jim Rohn");
        }else if(quoteHarian == 7){
            quote.setText("\"Seseorang yang terlalu sibuk untuk menjaga kesehatannya seperti mekanik yang terlalu sibuk untuk merawat peralatannya\". -Spanish Proverb");
        }

    }

    public void toIndeksMassaTubuh(View view) {
        Intent intent = new Intent(this, indeks_massa_tubuh.class);
        startActivity(intent);
    }

    public void toTensi(View view) {
        Intent intent = new Intent(this, tensi.class);
        startActivity(intent);
    }

    public void toDenyutJantung(View view) {
        Intent intent = new Intent(this, denyut_jantung.class);
        startActivity(intent);
    }

    public void toAktivitas(View view) {
        Intent intent = new Intent(this, aktivitas.class);
        startActivity(intent);
    }

    public void toDashboard(View view) {
        finish();
    }
}