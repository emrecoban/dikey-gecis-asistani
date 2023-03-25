package tr.com.emrecoban.dgsasistani;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. SHEPHERD on 11/24/2017.
 */

public class Denemeler extends AppCompatActivity {

    LinearLayout ortalamaLayout;
    ListView EkrandakiList;
    ImageView uzgun;
    TextView uzguntxt, tvSayOrt, tvSozOrt;

    List<DenemeObject> denemeler;

    Veritabani db = new Veritabani(this);
    int kayitsay=0;
    DenemeListAdapter denemeAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeler);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Denemelerim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();

        yenile();
    }

    public void yenile(){
        db.baglantiAc();

        denemeler = db.tumKayitlar();

        ArrayList Array_id = new ArrayList();
        ArrayList ArrayDenemeAd = new ArrayList();
        ArrayList ArraySayNet = new ArrayList();
        ArrayList ArraySozNet = new ArrayList();
        ArrayList ArrayPuanSay = new ArrayList();
        ArrayList ArrayPuanSoz = new ArrayList();
        ArrayList ArrayPuanEA = new ArrayList();
        ArrayList ArrayTarih = new ArrayList();

        int kayitSayisi = denemeler.size();
        double toplamSay = 0, toplamSoz = 0;
        for(int i = 0; i < denemeler.size(); ++i) {
            Array_id.add(denemeler.get(i).get_id());
            ArrayDenemeAd.add(kayitSayisi + ". " + denemeler.get(i).getDenemeAd());
            ArraySayNet.add(denemeler.get(i).getSayNet());
            ArraySozNet.add(denemeler.get(i).getSozNet());
            ArrayPuanSay.add(denemeler.get(i).getPuanSay());
            ArrayPuanSoz.add(denemeler.get(i).getPuanSoz());
            ArrayPuanEA.add(denemeler.get(i).getPuanEA());
            ArrayTarih.add(denemeler.get(i).getTarih());
            kayitsay++;
            kayitSayisi--;
            if(!denemeler.get(i).getSayNet().toString().trim().equals("")) {
                toplamSay = toplamSay + Double.valueOf(denemeler.get(i).getSayNet());
            }
            if(!denemeler.get(i).getSozNet().toString().trim().equals("")) {
                toplamSoz = toplamSoz + Double.valueOf(denemeler.get(i).getSozNet());
            }
        }

        db.baglantiKapat();

        double ortSay = 0;
        double ortSoz = 0;

        if(kayitsay!=0){
            ortSay = toplamSay / denemeler.size();
            ortSoz = toplamSoz / denemeler.size();
        }
        tvSayOrt.setText(""+new DecimalFormat("##.##").format(ortSay));
        tvSozOrt.setText(""+new DecimalFormat("##.##").format(ortSoz));


        if(kayitsay==0){
            ortalamaLayout.setVisibility(View.GONE);
            EkrandakiList.setVisibility(View.GONE);
            uzgun.setVisibility(View.VISIBLE);
            uzguntxt.setVisibility(View.VISIBLE);
        }

        denemeAdapter = new DenemeListAdapter(this, Array_id, ArrayDenemeAd, ArraySayNet, ArraySozNet, ArrayPuanSay, ArrayPuanSoz, ArrayPuanEA, ArrayTarih);
        denemeAdapter.notifyDataSetChanged();
        EkrandakiList.setAdapter(denemeAdapter);
    }

    private void baslangic() {
        tvSozOrt = (TextView) findViewById(R.id.tvSozOrt);
        tvSayOrt = (TextView) findViewById(R.id.tvSayOrt);
        ortalamaLayout = (LinearLayout) findViewById(R.id.ortalamaLayout);
        uzguntxt = (TextView) findViewById(R.id.uzguntxt);
        uzgun = (ImageView) findViewById(R.id.uzgun);
        EkrandakiList = (ListView) findViewById(R.id.denemeListesi);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deneme_menu, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yeniDenemeClick:
                Intent i = new Intent(getApplicationContext(), DenemeAdd.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class DenemeListAdapter extends BaseAdapter {

        ArrayList<Integer> _id;
        ArrayList<String> DenemeAd;
        ArrayList<String> SayNet;
        ArrayList<String> SozNet;
        ArrayList<String> PuanSay;
        ArrayList<String> PuanSoz;
        ArrayList<String> PuanEA;
        ArrayList<String> Tarih;

        Context mContext;
        //constructor
        public DenemeListAdapter(Context mContext, ArrayList<Integer> _id, ArrayList<String> DenemeAd, ArrayList<String> SayNet, ArrayList<String> SozNet, ArrayList<String> PuanSay, ArrayList<String> PuanSoz, ArrayList<String> PuanEA, ArrayList<String> Tarih) {
            this.mContext = mContext;
            this._id = _id;
            this.DenemeAd = DenemeAd;
            this.SayNet = SayNet;
            this.SozNet = SozNet;
            this.PuanSay = PuanSay;
            this.PuanSoz = PuanSoz;
            this.PuanEA = PuanEA;
            this.Tarih = Tarih;
        }


        @Override
        public int getCount() {
            return denemeler.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.deneme_item, null);

            final TextView tvDenemeAd = (TextView)view.findViewById(R.id.tvDenemeAd);
            TextView tvSayNet = (TextView)view.findViewById(R.id.tvSayNet);
            TextView tvSozNet = (TextView)view.findViewById(R.id.tvSozNet);
            TextView tvPuanSay = (TextView)view.findViewById(R.id.tvPuanSay);
            TextView tvPuanSoz = (TextView)view.findViewById(R.id.tvPuanSoz);
            TextView tvPuanEA = (TextView)view.findViewById(R.id.tvPuanEA);
            TextView tvTarih = (TextView)view.findViewById(R.id.tvTarih);
            Button btndSil = (Button)view.findViewById(R.id.btndSil);
            final int birsey = _id.get(i);
            btndSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.baglantiAc();
                    db.veriSil(birsey);
                    db.baglantiKapat();
                    yenile();
                    Toast.makeText(getApplicationContext(), "Başarıyla Silindi", Toast.LENGTH_SHORT).show();
                }
            });

            tvDenemeAd.setText(DenemeAd.get(i));
            tvSayNet.setText(SayNet.get(i));
            tvSozNet.setText(SozNet.get(i));
            tvPuanSay.setText(PuanSay.get(i));
            tvPuanSoz.setText(PuanSoz.get(i));
            tvPuanEA.setText(PuanEA.get(i));
            tvTarih.setText(Tarih.get(i));

            return view;
        }
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        startActivity(new Intent(getApplicationContext(), AnaEkran.class));
        return true;
    }
}
