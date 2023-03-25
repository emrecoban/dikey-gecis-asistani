package tr.com.emrecoban.dgsasistani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr. SHEPHERD on 19.06.2017.
 */

public class Hakkinda extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hakkinda);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Hakkında");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
