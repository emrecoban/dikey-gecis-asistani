package tr.com.emrecoban.dgsasistani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Mr. SHEPHERD on 14.07.2017.
 */

public class KonuDetay extends AppCompatActivity {

    private static final String TAG = "KonuDetay";
    private AdView mAdView;

    WebView Explorer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konudetay);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getSupportActionBar().setTitle("Konu Detayı");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle gelenURL = getIntent().getExtras();

        Explorer = (WebView) findViewById(R.id.Explorer);

        Explorer.loadUrl("file:///android_asset/"+gelenURL.get("URLPosition").toString()+".html");
        Explorer.getSettings().setJavaScriptEnabled(true);
        Explorer.getSettings().setLoadWithOverviewMode(true);
        Explorer.getSettings().setUseWideViewPort(true);
        //Explorer.getSettings().setBuiltInZoomControls(true);


    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
