package tr.com.emrecoban.dgsasistani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr. SHEPHERD on 5/26/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, AnaEkran.class);
        startActivity(intent);
        finish();

    }
}
