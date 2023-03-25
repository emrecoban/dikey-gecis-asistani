package tr.com.emrecoban.dgsasistani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Mr. COBAN on 29.01.2017.
 */

public class OBPHesapla extends AppCompatActivity {
    Button btnHesapla;
    EditText diploma;
    RadioButton rb100;
    RadioButton rb4;
    RadioGroup degerlendirme;
    TextView obpDeger, degerlendirmeTxt, diplomaTxt;
    LinearLayout sonucLayout3;
    double sonuc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obphesapla);
        getSupportActionBar().setTitle("Ö.B.P. Hesapla");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();


        btnHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int secim = degerlendirme.getCheckedRadioButtonId();
                if(!(secim==-1) && !diploma.getText().toString().isEmpty() && !diploma.getText().toString().startsWith(".") && !diploma.getText().toString().endsWith(".") ) {
                    double diplomadouble = Double.parseDouble(diploma.getText().toString());
                    if(rb100.isChecked()) {
                        sonuc = diplomadouble * 0.8;
                    }else if(rb4.isChecked()){
                        sonuc = diplomadouble * 25 * 0.8;
                    }
                    sonucLayout3.setVisibility(View.VISIBLE);
                    obpDeger.setText(""+(Double) sonuc);


                    klavyeyikapat();

                }else {
                    Toast.makeText(getApplicationContext(), "Puanınızın hesaplanabilmesi için tüm alanlar doldurulmalıdır.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void baslangic() {
        btnHesapla = (Button)findViewById(R.id.btnHesapla);
        diploma = (EditText)findViewById(R.id.diploma);
        rb100 = (RadioButton)findViewById(R.id.rb100);
        rb4 = (RadioButton)findViewById(R.id.rb4);
        degerlendirme = (RadioGroup)findViewById(R.id.degerlendirme);
        obpDeger = (TextView)findViewById(R.id.obpDeger);
        sonucLayout3 = (LinearLayout)findViewById(R.id.sonucLayout3);
        degerlendirmeTxt = (TextView)findViewById(R.id.degerlendirmeTxt);
        diplomaTxt = (TextView)findViewById(R.id.diplomaTxt);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        if(width<=480){
            diplomaTxt.setText("Diploma P.:");
            degerlendirmeTxt.setText("Kriter:");
        }
    }

    private void klavyeyikapat() {
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnHesapla.getWindowToken(), 0);
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
