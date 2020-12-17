package com.example.lifefit.IndeksMassaTubuh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class page_monitoring extends AppCompatActivity {

    private TextView tanggalMonitoring;
    private TextView jamMonitoring;
    private TextView quote;
    private TextView sumberQuote;
    private CircularImageView imageQuote;
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
        sumberQuote = findViewById(R.id.sumber_quote);
        imageQuote = findViewById(R.id.image_quote);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("id"));
        hourFormat = new SimpleDateFormat("h:mm a");
        date = dateFormat.format(calendar.getTime());
        hour = hourFormat.format(calendar.getTime());
        tanggalMonitoring.setText(date);
        jamMonitoring.setText(hour);

        Uri audrey = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_audrey);
        Uri jean = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_jean_pierre_barral);
        Uri jim = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_jim_rohn);
        Uri terri = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_terri_guillemets);
        Uri denis = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_denis_waitley);
        Uri mahatma = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_mahatma_gandhi);
        Uri anna = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_anna_wilson_schaef);
        Uri steve = Uri.parse("android.resource://"+ getPackageName() + "/" + R.drawable.quote_steve_jobs);

        int quoteHarian = calendar.get(Calendar.DAY_OF_WEEK);
        if(quoteHarian == 0) {
            quote.setText("Kesehatan adalah hubungan antara Anda dan tubuh Anda.");
            sumberQuote.setText("- Terri Guillemets -");
            imageQuote.setImageURI(terri);
        }else if(quoteHarian == 1){
            quote.setText("Waktu dan kesehatan adalah dua aset berharga yang tidak dikenali dan hargai sampai keduanya hilang.");
            sumberQuote.setText("- Denis Waitley -");
            imageQuote.setImageURI(denis);
        }else if(quoteHarian == 2){
            quote.setText("Harta sejati adalah kesehatan, bukan emas dan perak.");
            sumberQuote.setText("- Mahatma Gandhi -");
            imageQuote.setImageURI(mahatma);
        }else if(quoteHarian == 3){
            quote.setText("Air adalah kehidupan dan air bersih berarti kesehatan.");
            sumberQuote.setText("- Audrey Hepburn -");
            imageQuote.setImageURI(audrey);
        }else if(quoteHarian == 4){
            quote.setText("Kesehatan yang baik bukanlah sesuatu yang dapat kita beli. Namun, sesuatu yang dapat menjadi tabungan yang sangat berharga.");
            sumberQuote.setText("- Anne Wilson Schaef -");
            imageQuote.setImageURI(anna);
        }else if(quoteHarian == 5){
            quote.setText("Makan dengan sehat, tidur dengan baik, bernapas dengan dalam, bergerak dengan harmoni.");
            sumberQuote.setText("- Jean Pierre Barral -");
            imageQuote.setImageURI(jean);
        }else if(quoteHarian == 6){
            quote.setText("Jaga tubuhmu. Itulah satu-satunya tempat yang kamu miliki untuk hidup.");
            sumberQuote.setText("- Jim Rohn -");
            imageQuote.setImageURI(jim);
        }else if(quoteHarian == 7){
            quote.setText("Saat kamu masuk ke ruang operasi, kamu baru sadar bahwa kesehatan itu betapa berharganya.");
            sumberQuote.setText("- Steve Jobs -");
            imageQuote.setImageURI(steve);
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