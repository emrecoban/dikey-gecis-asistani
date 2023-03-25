package tr.com.emrecoban.dgsasistani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by Mr. COBAN on 29.01.2017.
 */

public class PuanHesapla extends AppCompatActivity {


    ImageView btnPaylas;
    Button btnHesapla, btnEkleyeGec;
    EditText etSayisalD, etSayisalY, etSayisalN, etSozelD, etSozelY, etSozelN, etOBP;
    TextView aciklama, aciklama14, aciklama16, aciklama17, obpTxt, eaTxt17, eaTxt, eaTxt14, tvSY17, tvSY16, tvSY14, tvSZ17, tvSZ16, tvSZ14, tvEA17, tvEA16, tvEA14;
    CheckBox yerlesmeDurumu;
    LinearLayout sonuc17Layout2, sonuc17Layout, sonuc16Layout2, sonuc16Layout, sonuc14Layout2, sonuc14Layout, LLpaylas;
    double sayisalNET, sozelNET, sonucSY17, sonucSZ17, sonucEA17, sonucSY16, sonucSZ16, sonucEA16, sonucSY14, sonucSZ14, sonucEA14;
    String sncSY17, sncSZ17, sncEA17, sncSY16, sncSZ16, sncEA16, sncSY14, sncSZ14, sncEA14;


    private static final String TAG = "PuanHesap";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puanhesapla);
        getSupportActionBar().setTitle("Puan Hesapla");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();


        etSayisalD.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(!etSayisalY.getText().toString().isEmpty()) {
                    int etSayisalD2;
                    if(etSayisalD.getText().toString().isEmpty()){
                        etSayisalD2 = 0;
                    }else{
                        etSayisalD2 = Integer.parseInt(etSayisalD.getText().toString());
                    }
                        sayisalNET = etSayisalD2 - (Integer.parseInt(etSayisalY.getText().toString()) * 0.25);
                        etSayisalN.setText(""+sayisalNET);
                }else {
                    etSayisalN.setText(etSayisalD.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });
        etSayisalY.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(!etSayisalD.getText().toString().isEmpty() && !etSayisalY.getText().toString().isEmpty()) {
                    sayisalNET = Integer.parseInt(etSayisalD.getText().toString()) - (Integer.parseInt(etSayisalY.getText().toString()) * 0.25);
                    etSayisalN.setText(""+sayisalNET);
                }else {
                    if(!etSayisalD.getText().toString().isEmpty()) {
                        etSayisalN.setText(etSayisalD.getText().toString());
                    }else{
                        etSayisalN.setText("0");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });
        etSozelD.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(!etSozelY.getText().toString().isEmpty()) {
                    int etSozelD2;
                    if(etSozelD.getText().toString().isEmpty()){
                        etSozelD2 = 0;
                    }else{
                        etSozelD2 = Integer.parseInt(etSozelD.getText().toString());
                    }
                        sozelNET = etSozelD2 - (Integer.parseInt(etSozelY.getText().toString()) * 0.25);
                        etSozelN.setText(""+sozelNET);
                }else {
                    etSozelN.setText(etSozelD.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });
        etSozelY.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(!etSozelD.getText().toString().isEmpty() && !etSozelY.getText().toString().isEmpty()) {
                    sozelNET = Integer.parseInt(etSozelD.getText().toString()) - (Integer.parseInt(etSozelY.getText().toString()) * 0.25);
                    etSozelN.setText(""+sozelNET);
                }else {
                    if(!etSozelD.getText().toString().isEmpty()) {
                        etSozelN.setText(etSozelD.getText().toString());
                    }else{
                        etSozelN.setText("0");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });


        btnHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSayisalN.getText().toString().isEmpty() && !etSozelN.getText().toString().isEmpty() && !etOBP.getText().toString().isEmpty() && !etOBP.getText().toString().startsWith(".") && !etOBP.getText().toString().endsWith(".")) {
                    if (!yerlesmeDurumu.isChecked()) {
                        sonucSY17 = 145.3784847 + (Double.parseDouble(etSayisalN.getText().toString()) * 3.176284) + (Double.parseDouble(etSozelN.getText().toString()) * 0.441631) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucSZ17 = 122.3821006 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.635257) + (Double.parseDouble(etSozelN.getText().toString()) * 2.208155) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucEA17 = 133.8802926 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.90577) + (Double.parseDouble(etSozelN.getText().toString()) * 1.324893) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);

                        sonucSY16 = 144.781724007457 + (Double.parseDouble(etSayisalN.getText().toString()) * 2.96882731321128) + (Double.parseDouble(etSozelN.getText().toString()) * 0.455131608890213) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucSZ16 = 132.850070587437 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.78129638792677) + (Double.parseDouble(etSozelN.getText().toString()) * 1.36539482667072) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucEA16 = 120.918417167417 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.593765462642267) + (Double.parseDouble(etSozelN.getText().toString()) * 2.27565804445118) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);

                        sonucSY14 = 145.442 + (Double.parseDouble(etSayisalN.getText().toString()) * 3.135) + (Double.parseDouble(etSozelN.getText().toString()) * 0.498) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucSZ14 = 121.342 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.627) + (Double.parseDouble(etSozelN.getText().toString()) * 2.489) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                        sonucEA14 = 133.392 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.881) + (Double.parseDouble(etSozelN.getText().toString()) * 1.494) + (Double.parseDouble(etOBP.getText().toString()) * 0.6);
                    } else {
                        sonucSY17 = 145.3784847 + (Double.parseDouble(etSayisalN.getText().toString()) * 3.176284) + (Double.parseDouble(etSozelN.getText().toString()) * 0.441631) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucSZ17 = 122.3821006 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.635257) + (Double.parseDouble(etSozelN.getText().toString()) * 2.208155) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucEA17 = 133.8802926 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.90577) + (Double.parseDouble(etSozelN.getText().toString()) * 1.324893) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);

                        sonucSY16 = 144.781724007457 + (Double.parseDouble(etSayisalN.getText().toString()) * 2.96882731321128) + (Double.parseDouble(etSozelN.getText().toString()) * 0.455131608890213) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucSZ16 = 132.850070587437 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.78129638792677) + (Double.parseDouble(etSozelN.getText().toString()) * 1.36539482667072) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucEA16 = 120.918417167417 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.593765462642267) + (Double.parseDouble(etSozelN.getText().toString()) * 2.27565804445118) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);

                        sonucSY14 = 145.442 + (Double.parseDouble(etSayisalN.getText().toString()) * 3.135) + (Double.parseDouble(etSozelN.getText().toString()) * 0.498) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucSZ14 = 121.342 + (Double.parseDouble(etSayisalN.getText().toString()) * 0.627) + (Double.parseDouble(etSozelN.getText().toString()) * 2.489) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                        sonucEA14 = 133.392 + (Double.parseDouble(etSayisalN.getText().toString()) * 1.881) + (Double.parseDouble(etSozelN.getText().toString()) * 1.494) + (Double.parseDouble(etOBP.getText().toString()) * 0.45);
                    }
                    sncSY17 = String.valueOf(sonucSY17);
                    sncSZ17 = String.valueOf(sonucSZ17);
                    sncEA17 = String.valueOf(sonucEA17);

                    sncSY16 = String.valueOf(sonucSY16);
                    sncSZ16 = String.valueOf(sonucSZ16);
                    sncEA16 = String.valueOf(sonucEA16);

                    sncSY14 = String.valueOf(sonucSY14);
                    sncSZ14 = String.valueOf(sonucSZ14);
                    sncEA14 = String.valueOf(sonucEA14);

                    sonuc17Layout.setVisibility(View.VISIBLE);
                    sonuc17Layout2.setVisibility(View.VISIBLE);
                    tvSY17.setText(sncSY17.substring(0, YediKontrol(sncSY17)));
                    tvSZ17.setText(sncSZ17.substring(0, YediKontrol(sncSZ17)));
                    tvEA17.setText(sncEA17.substring(0, YediKontrol(sncEA17)));

                    sonuc16Layout.setVisibility(View.VISIBLE);
                    sonuc16Layout2.setVisibility(View.VISIBLE);
                    tvSY16.setText(sncSY16.substring(0, YediKontrol(sncSY16)));
                    tvSZ16.setText(sncSZ16.substring(0, YediKontrol(sncSZ16)));
                    tvEA16.setText(sncEA16.substring(0, YediKontrol(sncEA16)));

                    sonuc14Layout.setVisibility(View.VISIBLE);
                    sonuc14Layout2.setVisibility(View.VISIBLE);
                    tvSY14.setText(sncSY14.substring(0, YediKontrol(sncSY14)));
                    tvSZ14.setText(sncSZ14.substring(0, YediKontrol(sncSZ14)));
                    tvEA14.setText(sncEA14.substring(0, YediKontrol(sncEA14)));

                    LLpaylas.setVisibility(view.VISIBLE);
                    aciklama.setVisibility(View.VISIBLE);
                    aciklama14.setVisibility(View.VISIBLE);
                    aciklama16.setVisibility(View.VISIBLE);
                    aciklama17.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), "Puanınızın hesaplanabilmesi için tüm alanlar doldurulmalıdır.", Toast.LENGTH_SHORT).show();
                }
                klavyeyikapat();

            }
        });


        btnPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paylas();
            }
        });
        btnEkleyeGec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kayit = new Intent(getApplicationContext(), DenemeAdd.class);
                kayit.putExtra("sy",tvSY17.getText().toString());
                kayit.putExtra("sz",tvSZ17.getText().toString());
                kayit.putExtra("ea",tvEA17.getText().toString());
                kayit.putExtra("sznet",etSozelN.getText().toString());
                kayit.putExtra("synet",etSayisalN.getText().toString());
                startActivity(kayit);
            }
        });

    }

    private void paylas() {
        String shareText = "— Dikey Geçiş Asistanı —\n\n" +
                "• Sayısal NET: " +etSayisalN.getText().toString()+ "\n" +
                "• Sözel NET: " +etSozelN.getText().toString()+ "\n" +
                "• Ö.B.P.: " +etOBP.getText().toString()+ "\n\n" +
                "2017-DGS:\n" +
                "• SAY Puan: " +tvSY17.getText().toString()+ "\n" +
                "• SÖZ Puan: " +tvSZ17.getText().toString()+ "\n" +
                "• EA Puan: " +tvEA17.getText().toString()+ "\n\n" +
                "2016-DGS:\n" +
                "• SAY Puan: " +tvSY16.getText().toString()+ "\n" +
                "• SÖZ Puan: " +tvSZ16.getText().toString()+ "\n" +
                "• EA Puan: " +tvEA16.getText().toString()+ "\n\n" +
                "2014-DGS:\n" +
                "• SAY Puan: " + tvSY14.getText().toString() + "\n" +
                "• SÖZ Puan: " + tvSZ14.getText().toString() + "\n" +
                "• EA Puan: " + tvEA14.getText().toString() + "\n\n";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, shareText);
        i.setType("*/*");
        startActivity(Intent.createChooser(i, "Nasıl paylaşmak istersin?"));
    }
    private int YediKontrol(String veri){
        int uzunluk=1;
        if(veri.length()<7){
            uzunluk = veri.length();
        }else{
            uzunluk = 7;
        }
        return uzunluk;
    }

    private void klavyeyikapat() {
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnHesapla.getWindowToken(), 0);
    }

    @Override
    public boolean onSupportNavigateUp() {//actionbar geri gel
        finish();
        return true;
    }

    private void baslangic() {
        btnPaylas = (ImageView) findViewById(R.id.btnPaylas);
        btnHesapla = (Button) findViewById(R.id.btnHesapla);
        btnEkleyeGec = (Button) findViewById(R.id.btnEkleyeGec);
        etSayisalD = (EditText) findViewById(R.id.etSayisalD);
        etSayisalY = (EditText) findViewById(R.id.etSayisalY);
        etSayisalN = (EditText) findViewById(R.id.etSayisalN);
        etSozelD = (EditText) findViewById(R.id.etSozelD);
        etSozelY = (EditText) findViewById(R.id.etSozelY);
        etSozelN = (EditText) findViewById(R.id.etSozelN);
        etOBP = (EditText) findViewById(R.id.etOBP);
        yerlesmeDurumu = (CheckBox)findViewById(R.id.yerlesmeDurumu);

        aciklama = (TextView) findViewById(R.id.aciklama);
        aciklama17 = (TextView) findViewById(R.id.aciklama17);
        aciklama16 = (TextView) findViewById(R.id.aciklama16);
        aciklama14 = (TextView) findViewById(R.id.aciklama14);

        sonuc17Layout = (LinearLayout)findViewById(R.id.sonuc17Layout);
        sonuc17Layout2 = (LinearLayout)findViewById(R.id.sonuc17Layout2);

        sonuc16Layout = (LinearLayout)findViewById(R.id.sonuc16Layout);
        sonuc16Layout2 = (LinearLayout)findViewById(R.id.sonuc16Layout2);

        sonuc14Layout = (LinearLayout)findViewById(R.id.sonuc14Layout);
        sonuc14Layout2 = (LinearLayout)findViewById(R.id.sonuc14Layout2);

        LLpaylas = (LinearLayout) findViewById(R.id.LLpaylas);

        tvSY17 = (TextView) findViewById(R.id.tvSY17);
        tvSZ17 = (TextView) findViewById(R.id.tvSZ17);
        tvEA17 = (TextView) findViewById(R.id.tvEA17);

        tvSY16 = (TextView) findViewById(R.id.tvSY16);
        tvSZ16 = (TextView) findViewById(R.id.tvSZ16);
        tvEA16 = (TextView) findViewById(R.id.tvEA16);

        tvSY14 = (TextView) findViewById(R.id.tvSY14);
        tvSZ14 = (TextView) findViewById(R.id.tvSZ14);
        tvEA14 = (TextView) findViewById(R.id.tvEA14);
        obpTxt = (TextView) findViewById(R.id.obpTxt);
        eaTxt = (TextView) findViewById(R.id.eaTxt);
        eaTxt14 = (TextView) findViewById(R.id.eaTxt14);
        eaTxt17 = (TextView) findViewById(R.id.eaTxt17);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String SPdegree = preferences.getString("SPdegree", "");
        etOBP.setText(SPdegree);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        if(width<=480){
            obpTxt.setText("Ö.B.P.:");
            etOBP.setHint("Sadece 40-80 arası");
            eaTxt.setText("E.A.");
            eaTxt14.setText("E.A.");
            eaTxt17.setText("E.A.");
        }
    }
}


