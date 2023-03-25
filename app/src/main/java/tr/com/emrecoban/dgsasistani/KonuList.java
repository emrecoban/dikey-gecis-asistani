package tr.com.emrecoban.dgsasistani;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;


/**
 * Created by Mr. SHEPHERD on 1.06.2017.
 */

public class KonuList extends AppCompatActivity {
    TabHost tabHost;
    ListView LVMat, LVTR, LVGEO;
    private String[] MatKonu, MatURL, TRKonu, TRURL, GEOKonu, GEOURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konulist);
        getSupportActionBar().setTitle("Konu Anlatımları");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();

        LVMat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), KonuDetay.class);
                i.putExtra("URLPosition", ""+MatURL[position]);
                startActivity(i);
            }
        });

        LVTR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), KonuDetay.class);
                i.putExtra("URLPosition", ""+TRURL[position]);
                startActivity(i);
            }
        });

        LVGEO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), KonuDetay.class);
                i.putExtra("URLPosition", ""+GEOURL[position]);
                startActivity(i);
            }
        });





    }

    private void baslangic() {

        //Konuların aktarılması
        //--Matematik
        MatKonu = getResources().getStringArray(R.array.MatKonu);
        MatURL = getResources().getStringArray(R.array.MatURL);
        LVMat = (ListView)findViewById(R.id.LVMat);
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, MatKonu);
        LVMat.setAdapter(veriAdaptoru);

        //--Türkçe
        TRKonu = getResources().getStringArray(R.array.TRKonu);
        TRURL = getResources().getStringArray(R.array.TRURL);
        LVTR = (ListView)findViewById(R.id.LVTR);
        ArrayAdapter<String> veriAdaptoru_2=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, TRKonu);
        LVTR.setAdapter(veriAdaptoru_2);

        //--Geometri
        GEOKonu = getResources().getStringArray(R.array.GEOKonu);
        GEOURL = getResources().getStringArray(R.array.GEOURL);
        LVGEO = (ListView)findViewById(R.id.LVGEO);
        ArrayAdapter<String> veriAdaptoru_3=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, GEOKonu);
        LVGEO.setAdapter(veriAdaptoru_3);

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
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
