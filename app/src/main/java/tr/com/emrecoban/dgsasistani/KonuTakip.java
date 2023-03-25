package tr.com.emrecoban.dgsasistani;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mr. SHEPHERD on 15.07.2017.
 */

public class KonuTakip extends AppCompatActivity {

    TabHost tabHost;
    TextView bitenKonuTR, bitenSoruTR, bitenKonuMat, bitenSoruMat, bitenKonuGEO, bitenSoruGEO;
    int TplmSoruMat, TplmKonuMat, TplmSoruTR, TplmKonuTR, TplmSoruGEO, TplmKonuGEO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konutakibi);
        getSupportActionBar().setTitle("Konu Takibi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();
        toplamDenetimMat();
        toplamDenetimTR();
        toplamDenetimGEO();

    }

    private void toplamDenetimTR(){
        TplmKonuTR=0;
        TplmSoruTR=0;
        SharedPreferences takipDeposu = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int TRToplam = getResources().getStringArray(R.array.KTTR).length;

        ArrayList trkonuBul = new ArrayList();
        List<String> traraci = Arrays.asList(getResources().getStringArray(R.array.KTTR));
        trkonuBul.addAll(traraci);

        for(int i=0;i<TRToplam;i++){
            final int soruSayisi = takipDeposu.getInt(replaceVeri((String) trkonuBul.get(i)) + "Soru", 0);
            Boolean konuDurumu = takipDeposu.getBoolean(replaceVeri((String) trkonuBul.get(i)) + "Durum", false);
            if(konuDurumu){
                TplmKonuTR++;
            }
            TplmSoruTR += soruSayisi;
        }
        bitenKonuTR.setText(String.valueOf(TplmKonuTR));
        bitenSoruTR.setText(String.valueOf(TplmSoruTR));
    }

    private void toplamDenetimMat(){
        TplmKonuMat=0;
        TplmSoruMat=0;
        SharedPreferences takipDeposu = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int MatToplam = getResources().getStringArray(R.array.KTMat).length;

        ArrayList konuBul = new ArrayList();
        List<String> araci = Arrays.asList(getResources().getStringArray(R.array.KTMat));
        konuBul.addAll(araci);

        for(int i=0;i<MatToplam;i++){
            final int soruSayisi = takipDeposu.getInt(replaceVeri((String) konuBul.get(i)) + "Soru", 0);
            Boolean konuDurumu = takipDeposu.getBoolean(replaceVeri((String) konuBul.get(i)) + "Durum", false);
            if(konuDurumu){
                TplmKonuMat++;
            }
            TplmSoruMat += soruSayisi;
        }
        bitenKonuMat.setText(String.valueOf(TplmKonuMat));
        bitenSoruMat.setText(String.valueOf(TplmSoruMat));
    }

    private void toplamDenetimGEO(){
        TplmKonuGEO=0;
        TplmSoruGEO=0;
        SharedPreferences takipDeposu = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int GEOToplam = getResources().getStringArray(R.array.KTGEO).length;

        ArrayList geokonuBul = new ArrayList();
        List<String> geoaraci = Arrays.asList(getResources().getStringArray(R.array.KTGEO));
        geokonuBul.addAll(geoaraci);

        for(int i=0;i<GEOToplam;i++){
            final int soruSayisi = takipDeposu.getInt(replaceVeri((String) geokonuBul.get(i)) + "Soru", 0);
            Boolean konuDurumu = takipDeposu.getBoolean(replaceVeri((String) geokonuBul.get(i)) + "Durum", false);
            if(konuDurumu){
                TplmKonuGEO++;
            }
            TplmSoruGEO += soruSayisi;
        }
        bitenKonuGEO.setText(String.valueOf(TplmKonuGEO));
        bitenSoruGEO.setText(String.valueOf(TplmSoruGEO));
    }

    private void baslangic() {
        bitenKonuTR = (TextView) findViewById(R.id.bitenKonuTR);
        bitenSoruTR = (TextView) findViewById(R.id.bitenSoruTR);
        bitenKonuMat = (TextView) findViewById(R.id.bitenKonuMat);
        bitenSoruMat = (TextView) findViewById(R.id.bitenSoruMat);
        bitenKonuGEO = (TextView) findViewById(R.id.bitenKonuGEO);
        bitenSoruGEO = (TextView) findViewById(R.id.bitenSoruGEO);

        // Sekmelerin kurulumu
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabLar;

        tabLar = tabHost.newTabSpec("tab1");
        tabLar.setContent(R.id.tab1);
        tabLar.setIndicator("Matematik");
        tabHost.addTab(tabLar);

        tabLar = tabHost.newTabSpec("tab2");
        tabLar.setContent(R.id.tab2);
        tabLar.setIndicator("Türkçe");
        tabHost.addTab(tabLar);

        tabLar = tabHost.newTabSpec("tab3");
        tabLar.setContent(R.id.tab3);
        tabLar.setIndicator("Geometri");
        tabHost.addTab(tabLar);



        final ArrayList konuTitle = new ArrayList(); //verilerin saklanacağı arrayList

        //Başlangıçta verileri getir
        List<String> araci = Arrays.asList(getResources().getStringArray(R.array.KTMat));
        konuTitle.addAll(araci);

        KonuListesi bildirimAdapter = new KonuListesi(getApplicationContext(), konuTitle);
        ListView KTList = (ListView) findViewById(R.id.LVMat);
        KTList.setAdapter(bildirimAdapter);


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {//Tab değişime uğrarsa içeriği değiştir
            @Override
            public void onTabChanged(String s) {
                if(tabHost.getCurrentTab()==0) {
                    konuTitle.clear();
                    List<String> araci = Arrays.asList(getResources().getStringArray(R.array.KTMat));//array_string.xml'deki verileri Listeye ekle
                    konuTitle.addAll(araci);//listedekleri arraylist'e ekle

                    KonuListesi bildirimAdapter = new KonuListesi(getApplicationContext(), konuTitle);
                    ListView KTList = (ListView) findViewById(R.id.LVMat);
                    KTList.setAdapter(bildirimAdapter);
                }else if(tabHost.getCurrentTab()==1){
                    konuTitle.clear();
                    List<String> araci = Arrays.asList(getResources().getStringArray(R.array.KTTR));
                    konuTitle.addAll(araci);

                    KonuListesi bildirimAdapter = new KonuListesi(getApplicationContext(), konuTitle);
                    ListView KTList = (ListView) findViewById(R.id.LVTR);
                    KTList.setAdapter(bildirimAdapter);
                }else if(tabHost.getCurrentTab()==2){
                    konuTitle.clear();
                    List<String> araci = Arrays.asList(getResources().getStringArray(R.array.KTGEO));
                    konuTitle.addAll(araci);

                    KonuListesi bildirimAdapter = new KonuListesi(getApplicationContext(), konuTitle);
                    ListView KTList = (ListView) findViewById(R.id.LVGEO);
                    KTList.setAdapter(bildirimAdapter);
                }
            }
        });




    }

    public class KonuListesi extends BaseAdapter{

        SharedPreferences takipDeposu = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor takipGuncelle = takipDeposu.edit();

        ArrayList<String> konuTitle;
        Context mContext;

        public KonuListesi(Context mContext, ArrayList<String> konuTitle){
            this.mContext = mContext;
            this.konuTitle = konuTitle;
        }

        @Override
        public int getCount() {return konuTitle.size();}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.konutakip_list, null);

            final ImageView durumIco = (ImageView) view.findViewById(R.id.durumIco);
            TextView KonuTitle = (TextView)view.findViewById(R.id.KonuTitle);
            final Switch KonuDurum = (Switch)view.findViewById(R.id.KonuDurum);
            final TextView KonuSoru = (TextView)view.findViewById(R.id.KonuSoru);

            Button btnPozitif = (Button)view.findViewById(R.id.btnPozitif);
            Button btnNegatif = (Button)view.findViewById(R.id.btnNegatif);

            //Verileri getir:
            final int soruSayisi = takipDeposu.getInt(replaceVeri(konuTitle.get(i)) + "Soru", 0);
            Boolean konuDurumu = takipDeposu.getBoolean(replaceVeri(konuTitle.get(i)) + "Durum", false);


            //Verileri yerleştir:
            KonuTitle.setText(konuTitle.get(i));
            KonuSoru.setText("Çözülen Soru Sayısı: "+soruSayisi);


            //custom layout:dialog


            btnPozitif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater linf = getLayoutInflater(); //LayoutInflater.from(KonuTakip.this);
                    ViewGroup inflator = (ViewGroup)linf.inflate(R.layout.dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(KonuTakip.this);
                    builder.setTitle("Soru Sayısı Ekle");
                    builder.setView(inflator);

                    final EditText etSoruSayisi = (EditText) inflator.findViewById(R.id.etSoruSayisi);

                    builder.setPositiveButton("EKLE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if(!etSoruSayisi.getText().toString().isEmpty()) {
                                        int soruSayisi = takipDeposu.getInt(replaceVeri(konuTitle.get(i)) + "Soru", 0);
                                        int islem = soruSayisi + Integer.parseInt(etSoruSayisi.getText().toString());
                                        takipGuncelle.putInt(replaceVeri(konuTitle.get(i)) + "Soru", islem);
                                        takipGuncelle.commit();

                                        KonuSoru.setText("Çözülen Soru Sayısı: " + islem);

                                        toplamDenetimMat();
                                        toplamDenetimTR();
                                        toplamDenetimGEO();
                                    }
                                    dialog.dismiss();
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("VAZGEÇ",null);
                    AlertDialog Pencere = builder.create();
                    Pencere.show();
                }
            });
            btnNegatif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater linf = getLayoutInflater(); //LayoutInflater.from(KonuTakip.this);
                    ViewGroup inflator = (ViewGroup)linf.inflate(R.layout.dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(KonuTakip.this);
                    builder.setTitle("Soru Sayısı Çıkar");
                    builder.setView(inflator);

                    final EditText etSoruSayisi = (EditText) inflator.findViewById(R.id.etSoruSayisi);

                    builder.setPositiveButton("ÇIKAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if(!etSoruSayisi.getText().toString().isEmpty()) {
                                        int soruSayisi = takipDeposu.getInt(replaceVeri(konuTitle.get(i)) + "Soru", 0);
                                        int islem = soruSayisi - Integer.parseInt(etSoruSayisi.getText().toString());
                                        takipGuncelle.putInt(replaceVeri(konuTitle.get(i)) + "Soru", islem);
                                        takipGuncelle.commit();

                                        KonuSoru.setText("Çözülen Soru Sayısı: " + islem);

                                        toplamDenetimMat();
                                        toplamDenetimTR();
                                        toplamDenetimGEO();
                                    }
                                    dialog.dismiss();
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("VAZGEÇ",null);
                    AlertDialog Pencere = builder.create();
                    Pencere.show();
                }
            });


            KonuDurum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean kontrolEt) {
                    if(kontrolEt){
                        durumIco.setVisibility(View.VISIBLE);
                        KonuDurum.setText("Tamamlandı");
                        takipGuncelle.putBoolean(replaceVeri(konuTitle.get(i)) + "Durum", kontrolEt);
                        takipGuncelle.commit();

                        toplamDenetimMat();
                        toplamDenetimTR();
                        toplamDenetimGEO();
                    }else{
                        durumIco.setVisibility(View.GONE);
                        KonuDurum.setText("Tamamlanmadı");
                        takipGuncelle.putBoolean(replaceVeri(konuTitle.get(i)) + "Durum", kontrolEt);
                        takipGuncelle.commit();

                        toplamDenetimMat();
                        toplamDenetimTR();
                        toplamDenetimGEO();
                    }
                }
            });
            if(konuDurumu){
                durumIco.setVisibility(View.VISIBLE);
                KonuDurum.setText("Tamamlandı");
                KonuDurum.setChecked(konuDurumu);
            }else{
                durumIco.setVisibility(View.GONE);
                KonuDurum.setText("Tamamlanmadı");
                KonuDurum.setChecked(konuDurumu);
            }


            return view;
        }
        private String replaceVeri(String veri){
            veri = veri.toLowerCase()
                    .replace(" ", "")
                    .replace(".", "")
                    .replace("ö", "o")
                    .replace("ç", "c")
                    .replace("ı", "i")
                    .replace("ş", "s")
                    .replace("ğ", "g")
                    .replace("ü", "u");
            return veri;
        }
    }

    private String replaceVeri(String veri){
        veri = veri.toLowerCase()
                .replace(" ", "")
                .replace(".", "")
                .replace("ö", "o")
                .replace("ç", "c")
                .replace("ı", "i")
                .replace("ş", "s")
                .replace("ğ", "g")
                .replace("ü", "u");
        return veri;
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
