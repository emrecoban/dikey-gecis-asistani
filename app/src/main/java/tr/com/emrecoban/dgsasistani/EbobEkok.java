package tr.com.emrecoban.dgsasistani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by Mr. COBAN on 29.01.2017.
 */

public class EbobEkok extends AppCompatActivity {




    static int ebobHesap(int sayi1, int sayi2, int sayi3, int sayi4){

        int sonuc;
        int say;
        int enbuyuk=0;

        if(sayi1>=enbuyuk){
            enbuyuk = sayi1;
        }else if(sayi2>=enbuyuk){
            enbuyuk = sayi2;
        }else if(sayi3>=enbuyuk){
            enbuyuk = sayi3;
        }else if(sayi4>=enbuyuk){
            enbuyuk = sayi4;
        }

        say = enbuyuk;



        while (true) {
            if ((sayi1 % say == 0) && (sayi2 % say == 0) && (sayi3 % say == 0) && (sayi4 % say == 0)) {
                sonuc = say;
                break;
            }
            say--;
        }
        return sonuc;
    }
    static int ekokHesap(int sayi1, int sayi2, int sayi3, int sayi4){
        int say = 2,sonuc;
        while (true) {
            if ((say % sayi1 == 0) && (say % sayi2 == 0) && (say % sayi3 == 0) && (say % sayi4 == 0)) {
                sonuc = say;
                break;
            }
            say++;
        }
        return sonuc;
    }

    EditText sayi1, sayi2, sayi3, sayi4;
    Button hesapla;
    TextView tvEbob, tvEkok,
            carpan1, carpan2, carpan3, carpan4, carpan5,
            carpan01, carpan02, carpan03, carpan04, carpan05,
            ekokBaslik, ebobBaslik;
    LinearLayout LL1, LL2, LL3, LL4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebobekok);
        getSupportActionBar().setTitle("EBOB - EKOK Hesaplama");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();


        hesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gelen1 = sayi1.getText().toString();
                String gelen2 = sayi2.getText().toString();
                String gelen3 = sayi3.getText().toString();
                String gelen4 = sayi4.getText().toString();
                if(!gelen1.isEmpty() && !gelen2.isEmpty() && !gelen3.isEmpty() && !gelen4.isEmpty()) {
                    int sy1 = Integer.parseInt(gelen1);
                    int sy2 = Integer.parseInt(gelen2);
                    int sy3 = Integer.parseInt(gelen3);
                    int sy4 = Integer.parseInt(gelen4);
                    if (sy1 == 0 || sy2 == 0 || sy3 == 0 || sy4 == 0) {
                        Toast.makeText(getApplicationContext(), "Sıfır değerini giremezsiniz.", Toast.LENGTH_SHORT).show();
                    } else {
                        int ebob = ebobHesap(sy1, sy2, sy3, sy4);
                        int ekok = ekokHesap(sy1, sy2, sy3, sy4);
                        LL1.setVisibility(View.VISIBLE);
                        LL2.setVisibility(View.VISIBLE);
                        tvEbob.setText(""+ebob);
                        tvEkok.setText(""+ekok);


                        CarpanlarEkok(ekok,sy1,sy2,sy3,sy4);
                        CarpanlarEbob(ebob,sy1,sy2,sy3,sy4);
                        ekokBaslik.setVisibility(view.VISIBLE);
                        ebobBaslik.setVisibility(view.VISIBLE);
                        LL3.setVisibility(view.VISIBLE);
                        LL4.setVisibility(view.VISIBLE);
                    }
                    klavyeyikapat();
                }else{
                    Toast.makeText(getApplicationContext(), "Lütfen tüm alanları doldurun. Boş kalan alan için aynı sayıyı girebilirsiniz.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void baslangic() {
        hesapla = (Button)findViewById(R.id.hesapla);
        sayi1 = (EditText)findViewById(R.id.sayi1);
        sayi2 = (EditText)findViewById(R.id.sayi2);
        sayi3 = (EditText)findViewById(R.id.sayi3);
        sayi4 = (EditText)findViewById(R.id.sayi4);
        tvEbob = (TextView)findViewById(R.id.tvEbob);
        tvEkok = (TextView)findViewById(R.id.tvEkok);
        carpan1 = (TextView)findViewById(R.id.carpan1);
        carpan2 = (TextView)findViewById(R.id.carpan2);
        carpan3 = (TextView)findViewById(R.id.carpan3);
        carpan4 = (TextView)findViewById(R.id.carpan4);
        carpan5 = (TextView)findViewById(R.id.carpan5);
        ekokBaslik = (TextView)findViewById(R.id.carpanBaslikEKOK);
        ebobBaslik = (TextView)findViewById(R.id.carpanBaslikEBOB);
        carpan01 = (TextView)findViewById(R.id.carpan01);
        carpan02 = (TextView)findViewById(R.id.carpan02);
        carpan03 = (TextView)findViewById(R.id.carpan03);
        carpan04 = (TextView)findViewById(R.id.carpan04);
        carpan05 = (TextView)findViewById(R.id.carpan05);
        LL1 = (LinearLayout)findViewById(R.id.LL1);
        LL2 = (LinearLayout)findViewById(R.id.LL2);
        LL3 = (LinearLayout)findViewById(R.id.LL3);
        LL4 = (LinearLayout)findViewById(R.id.LL4);
    }

    public void CarpanlarEkok(int gelenSayi,int sy1, int sy2, int sy3, int sy4){
        carpan1.setText("");
        carpan2.setText("");
        carpan3.setText("");
        carpan4.setText("");
        carpan5.setText("");
            int kullanSayi = gelenSayi;
            int i;
            for(i=2;i<=kullanSayi;i++){
                if(kullanSayi % i == 0){
                    carpan1.setText(carpan1.getText()+""+sy1+"\n");
                    carpan2.setText(carpan2.getText()+""+sy2+"\n");
                    carpan3.setText(carpan3.getText()+""+sy3+"\n");
                    carpan4.setText(carpan4.getText()+""+sy4+"\n");
                    carpan5.setText(carpan5.getText()+""+i+"\n");


                        if(sy1 % i == 0){sy1=sy1/i;}
                        if(sy2 % i == 0){sy2=sy2/i;}
                        if(sy3 % i == 0){sy3=sy3/i;}
                        if(sy4 % i == 0){sy4=sy4/i;}


                    kullanSayi = kullanSayi / i;
                    i=1;
                }
            }
    }
    public void CarpanlarEbob(int gelenSayi,int sy1, int sy2, int sy3, int sy4){
        carpan01.setText("");
        carpan02.setText("");
        carpan03.setText("");
        carpan04.setText("");
        carpan05.setText("");
        int kullanSayi = gelenSayi;
        int i;
        for(i=2;i<=kullanSayi;i++){
            if(kullanSayi % i == 0){
                carpan01.setText(carpan01.getText()+""+sy1+"\n");
                carpan02.setText(carpan02.getText()+""+sy2+"\n");
                carpan03.setText(carpan03.getText()+""+sy3+"\n");
                carpan04.setText(carpan04.getText()+""+sy4+"\n");
                carpan05.setText(carpan05.getText()+""+i+"\n");


                if(sy1 % i == 0){sy1=sy1/i;}
                if(sy2 % i == 0){sy2=sy2/i;}
                if(sy3 % i == 0){sy3=sy3/i;}
                if(sy4 % i == 0){sy4=sy4/i;}


                kullanSayi = kullanSayi / i;
                i=1;
            }
        }
    }

    private void klavyeyikapat() {
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(hesapla.getWindowToken(), 0);
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
