package tr.com.emrecoban.dgsasistani;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Mr. SHEPHERD on 11/24/2017.
 */

public class DenemeAdd extends AppCompatActivity {


    private Calendar calendar;
    private TextView etdDenemeAd, etdSayNet, etdSozNet, etdSayPuan, etdSozPuan, etdEAPuan, etdTarih;
    private Button btndEkle;
    private int year, month, day;
    Veritabani db = new Veritabani(DenemeAdd.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeadd);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Yeni Deneme Ekle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();


        btndEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.baglantiAc();
                db.veriEkle(etdDenemeAd.getText().toString(),etdSayNet.getText().toString(), etdSozNet.getText().toString(), etdSayPuan.getText().toString(), etdSozPuan.getText().toString(), etdEAPuan.getText().toString(), etdTarih.getText().toString());
                db.baglantiKapat();
                Toast.makeText(getApplicationContext(), "Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Denemeler.class));

            }
        });







        /*---TARİH*/
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etdTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DenemeAdd.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        /*---TARİH*/
    }

    private void baslangic() {
        etdDenemeAd = (TextView) findViewById(R.id.etdDenemeAd);
        etdSayNet = (TextView) findViewById(R.id.etdSayNet);
        etdSozNet = (TextView) findViewById(R.id.etdSozNet);
        etdSayPuan = (TextView) findViewById(R.id.etdSayPuan);
        etdSozPuan = (TextView) findViewById(R.id.etdSozPuan);
        etdEAPuan = (TextView) findViewById(R.id.etdEAPuan);
        etdTarih = (TextView) findViewById(R.id.etdTarih);
        btndEkle = (Button) findViewById(R.id.btndEkle);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            String sy = extras.getString("sy","0");
            String sz = extras.getString("sz","0");
            String ea = extras.getString("ea","0");
            String sznet = extras.getString("sznet","0");
            String synet = extras.getString("synet","0");

            etdSayNet.setText(synet);
            etdSozNet.setText(sznet);
            etdSayPuan.setText(sy);
            etdSozPuan.setText(sz);
            etdEAPuan.setText(ea);
        }


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String tarih = day + "." + month + "." + year;
        etdTarih.setText(tarih);


    }
    private void updateLabel() {
        String myFormat = "dd.MM.yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdTarih.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
