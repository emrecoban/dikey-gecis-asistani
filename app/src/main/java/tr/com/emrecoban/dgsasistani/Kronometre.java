package tr.com.emrecoban.dgsasistani;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by Mr. COBAN on 29.01.2017.
 */

public class Kronometre extends AppCompatActivity {

    Button bStart, bStop, bZero;
    TextView TickTock;
    CountDownTimer CDTimer;
    MediaPlayer MPlayer;
    private static final String TAG = "Kronometre";
    private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kronometre);
        getSupportActionBar().setTitle("Sınav Kronometresi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();



        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTickTock = TickTock.getText().toString();
                String[] splitTickTock = strTickTock.split(" : ");
                int gelenSaat = Integer.parseInt(splitTickTock[0]) * 60 * 60;
                int gelenDakika = Integer.parseInt(splitTickTock[1]) * 60;
                int gelenSaniye = Integer.parseInt(splitTickTock[2]);
                int ToplamSaniye = gelenSaat + gelenDakika + gelenSaniye;
                long kalanzaman = Long.parseLong((int) ToplamSaniye + "000");
                CDTimer = new CountDownTimer(kalanzaman, 1000) {

                    public void onTick(long millisUntilFinished) {

                        int seconds = (int) (millisUntilFinished/1000) % 60 ;
                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                        int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);

                        TickTock.setText(hours + " : " + minutes + " : " + seconds);
                    }

                    public void onFinish() {
                        TickTock.setText("Süre bitti!");
                        bStart.setEnabled(false);
                        bStop.setEnabled(false);
                        bZero.setEnabled(true);

                        MPlayer = MediaPlayer.create(getApplicationContext(), R.raw.kronometre);
                        MPlayer.start();
                    }
                }.start();
                bStart.setText("Devam Et");
                bStart.setEnabled(false);
                bStop.setEnabled(true);
                bZero.setEnabled(false);
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CDTimer.cancel();
                bStart.setEnabled(true);
                bStop.setEnabled(false);
                bZero.setEnabled(true);
                MPlayer = MediaPlayer.create(getApplicationContext(), R.raw.kronometre);
            }
        });

        bZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bZero.setEnabled(false);
                bStart.setEnabled(true);
                TickTock.setText("2 : 30 : 00");
                bStart.setText("Başlat");
                MPlayer.stop();
                MPlayer.release();
            }
        });


    }

    private void baslangic() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//Uyku modunu engelle
        bStart = (Button)findViewById(R.id.bStart);
        bStop = (Button)findViewById(R.id.bStop);
        bZero = (Button)findViewById(R.id.bZero);
        TickTock = (TextView)findViewById(R.id.TickTock);
        bStop.setEnabled(false);
        bZero.setEnabled(false);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        if(width<=480){
            bZero.setText("RESET");
        }

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
