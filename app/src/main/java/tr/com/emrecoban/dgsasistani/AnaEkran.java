package tr.com.emrecoban.dgsasistani;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AnaEkran extends AppCompatActivity {

    Button bPuanHesap, bOBPHesap, bSayac, bEbobEkok, bKonuAnlat, bKonuTakip, bBagirCagir, bDenemeler;
    TextView kalanzaman, kalanzaman0, name, degree;
    CountDownTimer CDTimer;
    long days, hours, minutes, seconds, diff;
    Button bprofil;
    ImageView avatar;

    String Sn720 = " Saniye";
    String SPexamDate2, SPexamYear;

    private static final String TAG = "AnaEkran";
    private AdView mAdView;

    private FirebaseAnalytics mFirebaseAnalytics;//firabase için
    ArrayList<Item> animalList=new ArrayList<>();
    String[] bolumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anaekran);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);//firabase için
        getSupportActionBar().setTitle("  " + getApplicationInfo().loadLabel(getPackageManager()).toString());
        getSupportActionBar().setLogo(R.mipmap.dgsico);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Sınav Bilgleri Gelsin
        SPexamDate2 = preferences.getString("SPexamDate2", "0");
        SPexamYear = preferences.getString("SPexamYear", "0000");


        baslangic();
        bolumList = getApplicationContext().getResources().getStringArray(R.array.bolumList);



        //Avatar listesi oluştur
        //final MyAdapter listeleme=new MyAdapter(this,R.layout.avatar_list,animalList);

        animalList.add(new Item(R.mipmap.avatar1));
        animalList.add(new Item(R.mipmap.avatar2));
        animalList.add(new Item(R.mipmap.avatar3));
        animalList.add(new Item(R.mipmap.avatar4));
        animalList.add(new Item(R.mipmap.avatar5));
        animalList.add(new Item(R.mipmap.avatar6));
        animalList.add(new Item(R.mipmap.avatar7));
        animalList.add(new Item(R.mipmap.avatar8));
        animalList.add(new Item(R.mipmap.avatar9));
        animalList.add(new Item(R.mipmap.avatar10));
        animalList.add(new Item(R.mipmap.avatar11));
        animalList.add(new Item(R.mipmap.avatar12));
        animalList.add(new Item(R.mipmap.avatar13));
        animalList.add(new Item(R.mipmap.avatar14));
        animalList.add(new Item(R.mipmap.avatar15));
        animalList.add(new Item(R.mipmap.avatar16));
        animalList.add(new Item(R.mipmap.avatar17));

        //verileri getir

        /*
        //test zamanı
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("SPname");
        editor.remove("SPdegree");
        editor.remove("SPdepartment");
        editor.commit();
        */


        String SPname = preferences.getString("SPname", "Dikey Geçiş Asistanı");
        String SPdegree = preferences.getString("SPdegree", "0");
        int SPdepartment = preferences.getInt("SPdepartmentYeni", 0);
        int SPavatar = preferences.getInt("SPavatar", animalList.get(0).getAnimalImage());

        name.setText(SPname);
        degree.setText(bolumList[SPdepartment].toString() + " (" + SPdegree + ")");
        avatar.setImageResource(SPavatar);



        bPuanHesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PuanHesapla.class));
            }
        });
        bOBPHesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnaEkran.this, OBPHesapla.class));
            }
        });
        bSayac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Kronometre.class));
            }
        });
        bEbobEkok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EbobEkok.class));
            }
        });
        bKonuTakip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), KonuTakip.class));
            }
        });
        bKonuAnlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), KonuList.class));
            }
        });
        bDenemeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Denemeler.class));
            }
        });
        bBagirCagir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BagirCagir.class));
            }
        });
        bprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profil.class));
            }
        });


        KalanZamanla();
        VersionCTRL();
    }

    private void KalanZamanla(){
        final long[] refresh = {500};
        if(SPexamDate2 != "0") {
            CDTimer = new CountDownTimer(refresh[0], 250) {
                public void onTick(long millisUntilFinished) {
                    String sinavtarihi = SPexamDate2;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date gelecekzaman = null;
                    try {
                        gelecekzaman = dateFormat.parse(sinavtarihi);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date bugun = new Date();
                    diff = gelecekzaman.getTime() - bugun.getTime();
                    seconds = ((diff / 1000) % 60);
                    minutes = ((((diff / 1000) / 60)) % 24);
                    hours = ((((diff / 1000) / 60) / 60) % 24);
                    days = ((((diff / 1000) / 60) / 60) / 24);
                    if (diff > 0) {
                        kalanzaman.setText("" + days + " Gün " + hours + " Saat " + minutes + " Dakika " + seconds + Sn720);
                    } else {
                        kalanzaman0.setText(SPexamYear + "-DGS Tamamlandı.");
                        kalanzaman.setText("Başarılar!");
                    }

                }

                public void onFinish() {
                    refresh[0] = 500;
                    CDTimer.start();
                }
            }.start();
        }else{
            kalanzaman.setText("Ayarlar > Sınav Bilgileri > Güncelle");
        }
    }




    private void baslangic() {
        kalanzaman0 = (TextView)findViewById(R.id.kalanzaman0);
        kalanzaman = (TextView)findViewById(R.id.kalanzaman);
        bPuanHesap = (Button)findViewById(R.id.bPuanHesap);
        bOBPHesap = (Button)findViewById(R.id.bOBPHesap);
        bSayac = (Button)findViewById(R.id.bSayac);
        bEbobEkok = (Button)findViewById(R.id.bEbobEkok);
        bKonuAnlat = (Button)findViewById(R.id.bKonuAnlat);
        bKonuTakip = (Button)findViewById(R.id.bKonuTakip);
        bBagirCagir = (Button)findViewById(R.id.bBagirCagir);
        bDenemeler = (Button)findViewById(R.id.bDenemeler);
        bprofil = (Button) findViewById(R.id.bprofil);
        name = (TextView)findViewById(R.id.name);
        degree = (TextView)findViewById(R.id.degree);
        avatar = (ImageView) findViewById(R.id.avatar);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        //int height=dm.heightPixels;
        //Toast.makeText(getApplicationContext(), ""+scrWidth, Toast.LENGTH_SHORT).show();
        if(width<=720){
            kalanzaman0.setText(SPexamYear + "-DGS'ye Kalan Zaman:");
            kalanzaman.setTextSize(16);
            bKonuAnlat.setText("KONULAR");
            Sn720 = " Sn";
        }else{
            kalanzaman0.setText(SPexamYear + "-Dikey Geçiş Sınavı Kalan Zaman:");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anaekran_menu, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bildirimClick:
                startActivity(new Intent(getApplicationContext(), Bildirim.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Geri döndüğünde profili yenile
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String SPname = preferences.getString("SPname", "Dikey Geçiş Asistanı");
        String SPdegree = preferences.getString("SPdegree", "0");
        int SPdepartment = preferences.getInt("SPdepartmentYeni", 0);
        int SPavatar = preferences.getInt("SPavatar", animalList.get(0).getAnimalImage());
        SPexamDate2 = preferences.getString("SPexamDate2", "0");
        SPexamYear = preferences.getString("SPexamYear", "0000");
        KalanZamanla();
        name.setText(SPname);
        degree.setText(bolumList[SPdepartment].toString() + " (" + SPdegree + ")");
        avatar.setImageResource(SPavatar);
        kalanzaman0.setText(SPexamYear + "-Dikey Geçiş Sınavı Kalan Zaman:");
    }


    private void VersionCTRL(){
        String Yenilikler = getResources().getString(R.string.yenilik);
        int CurrentVersion = getResources().getInteger(R.integer.versionCode);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            int version = preferences.getInt("dgsVersion0", 1);
                if(CurrentVersion != version){ // Version kontrol et
                    //Yenilikleri göster
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(Yenilikler)
                            .setTitle("Yenilikler")
                            .setIcon(R.mipmap.yenilik)
                            .setPositiveButton("TAMAM", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    //Version güncelle

                    editor.putInt("dgsVersion0", CurrentVersion);
                    editor.commit();
                }
    }
}
