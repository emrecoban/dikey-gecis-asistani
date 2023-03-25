package tr.com.emrecoban.dgsasistani;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Mr. SHEPHERD on 20.06.2017.
 */

public class Profil extends AppCompatActivity {

    ImageView PRavatar, btnSnUpdate;
    EditText PRname, PRdegree, PRusername, PRmail;
    Spinner PRcity, PRdepartment;
    LinearLayout PRlayout;

    private static String tarihURL = "http://dgs.emrecoban.com.tr/comment/tarih.php";
    TextView s_resultDate, s_LapplicationDate1, s_applicationDate2, s_applicationDate1, s_examDate, s_songuncelleme;

    private static String registerURL = "http://dgs.emrecoban.com.tr/comment/userRegister.php";
    Button btnRegister, btnUpdate;
    TextView userResult;
    String macAddress, TokenID;

    SharedPreferences preferences;

    ArrayList<Item> animalList=new ArrayList<>();

    String[] bolumList;
    String[] sehirList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        getSupportActionBar().setTitle("Ayarlar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baslangic();
        bolumList = getApplicationContext().getResources().getStringArray(R.array.bolumList);
        sehirList = getApplicationContext().getResources().getStringArray(R.array.sehirList);

        TokenID = FirebaseInstanceId.getInstance().getToken();

        //Avatar listesi oluştur
        final MyAdapter listeleme=new MyAdapter(this,R.layout.avatar_list,animalList);
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


        //Verileri getir
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferencesGet.edit();

        //Sınav Bilgileri
        String SPexamDate1 = preferences.getString("SPexamDate1", "-");
        String SPapplicationDate1 = preferences.getString("SPapplicationDate1", "-");
        String SPapplicationDate2 = preferences.getString("SPapplicationDate2", "-");
        String SPLapplicationDate1 = preferences.getString("SPLapplicationDate1", "-");
        String SPresultDate = preferences.getString("SPresultDate", "-");
        String SPsonguncelleme = preferences.getString("SPsonguncelleme", "0");

        s_examDate.setText(SPexamDate1.toString());
        s_applicationDate1.setText(SPapplicationDate1.toString());
        s_applicationDate2.setText(SPapplicationDate2.toString());
        s_LapplicationDate1.setText(SPLapplicationDate1.toString());
        s_resultDate.setText(SPresultDate.toString());
        if(SPsonguncelleme.toString() == "0"){
            s_songuncelleme.setText("Lütfen Sınav Bilgilerini güncelleyin.");
        }else{
            s_songuncelleme.setText("*Son güncelleme tarihi: " + SPsonguncelleme.toString());
        }


        //Profil Bilgileri
        String SPusername = preferences.getString("SPusername", "");
        final String SPuserpass = preferences.getString("SPuserpass", "0");
        String SPmail = preferences.getString("SPmail", "");
        String SPname = preferences.getString("SPname", "");
        String SPdegree = preferences.getString("SPdegree", "");
        final int SPdepartment = preferences.getInt("SPdepartmentYeni", 0);
        final int SPcity = preferences.getInt("SPcity", 0);
        final int SPavatar = preferences.getInt("SPavatar", animalList.get(0).getAnimalImage());

        //Gelen verileri yaz
        PRusername.setText(SPusername);
        PRname.setText(SPname);
        PRmail.setText(SPmail);
        PRdegree.setText(SPdegree);
        PRdepartment.setSelection(SPdepartment);
        PRcity.setSelection(SPcity);
        PRavatar.setImageResource(SPavatar);

        //Kullanıcı kayıt kontrol
        if(SPuserpass=="0"){
            btnUpdate.setVisibility(View.GONE);
        }else{
            btnRegister.setVisibility(View.GONE);
            PRusername.setEnabled(false);
        }

        //MAC Al
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userResult.setText("Yükleniyor...");
                btnRegister.setEnabled(false);

                //PASSWORD Oluştur
                Random r = new Random();
                int pass1 = r.nextInt(99) + 10;
                int pass2 = r.nextInt(999) + 100;
                int pass3 = r.nextInt(9999) + 1000;
                int pass4 = r.nextInt(99999) + 10000;
                int pass5 = r.nextInt(99) + 10;
                int pass6 = r.nextInt(99) + 10;
                final int gelenPass = pass1 + pass2 + pass3 + pass4 + pass5 + pass6;

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(PRmail.getText().toString()).matches() && PRname.length()>3 && PRusername.length()>3) {
                    //Profil Bilgileri
                    int SPdepartment = preferences.getInt("SPdepartmentYeni", 0);
                    int SPcity = preferences.getInt("SPcity", 0);
                    int SPavatar = preferences.getInt("SPavatar", animalList.get(0).getAnimalImage());
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        userResult.setText(s);


                            if (s.substring(0, 2).equals("(1")) {
                                editor.putString("SPuserpass", String.valueOf(gelenPass));
                                editor.commit();

                                btnRegister.setVisibility(View.GONE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                PRusername.setEnabled(false);
                            }
                        }
                    }

                    ;
                    userRegister pr = new userRegister("0", macAddress, TokenID, SPavatar, PRusername.getText().toString(), String.valueOf(gelenPass), PRname.getText().toString(), PRmail.getText().toString(), sehirList[SPcity].toString(), bolumList[SPdepartment].toString(), PRdegree.getText().toString(), responselistener);
                    RequestQueue queue = Volley.newRequestQueue(Profil.this);
                queue.add(pr);
                }else{
                    userResult.setText("E-Posta adresi veya isim geçersiz");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userResult.setText("Yükleniyor...");

                if(android.util.Patterns.EMAIL_ADDRESS.matcher(PRmail.getText().toString()).matches() && PRname.length()>3){
                    //Profil Bilgileri
                    int SPdepartment = preferences.getInt("SPdepartmentYeni", 0);
                    int SPcity = preferences.getInt("SPcity", 0);
                    int SPavatar = preferences.getInt("SPavatar", animalList.get(0).getAnimalImage());
                    String SPuserpass = preferences.getString("SPuserpass", "0");

                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            userResult.setText(s);
                        }
                    };
                    userRegister pr = new userRegister("1", macAddress, TokenID, SPavatar, PRusername.getText().toString(), SPuserpass, PRname.getText().toString(), PRmail.getText().toString(), sehirList[SPcity].toString(), bolumList[SPdepartment].toString(), PRdegree.getText().toString(), responselistener);
                    RequestQueue queue = Volley.newRequestQueue(Profil.this);
                    queue.add(pr);

            }else{
                userResult.setText("E-Posta adresi veya isim geçersiz");
            }
        }

        });





        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        PRlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Profil Resmi")
                        .setAdapter(listeleme, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PRavatar.setImageResource(animalList.get(i).getAnimalImage());
                                editor.putInt("SPavatar", animalList.get(i).getAnimalImage());
                                editor.commit();
                            }
                        })
                        ;
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        PRcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("SPcity", i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        PRname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("SPname", PRname.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });
        PRusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("SPusername", PRusername.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });
        PRmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("SPmail", PRmail.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });
        PRdegree.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editor.putString("SPdegree", PRdegree.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });

        PRdepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("SPdepartmentYeni", i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_songuncelleme.setText("*Son güncelleme tarihi: Yükleniyor...");
                StringRequest request = new StringRequest(tarihURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        parseJsonData(string);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        s_songuncelleme.setText("Bir hata oluştu. Lütfen daha sonra tekrar deneyin.");
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(Profil.this);
                rQueue.add(request);
            }
        });

    }


    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            String birlestirTarih = object.getString("examDateD") + "." + object.getString("examDateM") + "." + object.getString("examDateY") + " - " + object.getString("examDateT").substring(0,5);
            String birlestirTarih2 = object.getString("examDateY") + "-" + object.getString("examDateM") + "-" + object.getString("examDateD") + " " + object.getString("examDateT");
            s_examDate.setText(birlestirTarih);
            s_applicationDate1.setText(object.getString("applicationDate1"));
            s_applicationDate2.setText(object.getString("applicationDate2"));
            s_LapplicationDate1.setText(object.getString("LapplicationDate1"));
            s_resultDate.setText(object.getString("resultDate"));

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = new Date();
            s_songuncelleme.setText("*Son güncelleme tarihi: " + formatter.format(date).toString());

            SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferencesGet.edit();
            editor.putString("SPexamYear", object.getString("examDateY"));
            editor.putString("SPexamDate1", birlestirTarih);
            editor.putString("SPexamDate2", birlestirTarih2);
            editor.putString("SPapplicationDate1", object.getString("applicationDate1"));
            editor.putString("SPapplicationDate2", object.getString("applicationDate2"));
            editor.putString("SPLapplicationDate1", object.getString("LapplicationDate1"));
            editor.putString("SPresultDate", object.getString("resultDate"));
            editor.putString("SPsonguncelleme", formatter.format(date).toString());
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void baslangic() {
        PRavatar = (ImageView) findViewById(R.id.PRavatar);
        btnSnUpdate = (ImageView) findViewById(R.id.btnSnUpdate);
        PRusername = (EditText) findViewById(R.id.PRusername);
        PRname = (EditText) findViewById(R.id.PRname);
        PRmail = (EditText) findViewById(R.id.PRmail);
        PRdegree = (EditText) findViewById(R.id.PRdegree);
        PRdepartment = (Spinner) findViewById(R.id.PRdepartment);
        PRcity = (Spinner) findViewById(R.id.PRcity);
        PRlayout = (LinearLayout) findViewById(R.id.PRlayout);

        //Profil Button
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        userResult = (TextView) findViewById(R.id.userResult);

        //Sınav Bilgileri
        s_examDate = (TextView) findViewById(R.id.s_examDate);
        s_applicationDate1 = (TextView) findViewById(R.id.s_applicationDate1);
        s_applicationDate2 = (TextView) findViewById(R.id.s_applicationDate2);
        s_LapplicationDate1 = (TextView) findViewById(R.id.s_LapplicationDate1);
        s_resultDate = (TextView) findViewById(R.id.s_resultDate);
        s_songuncelleme = (TextView) findViewById(R.id.s_songuncelleme);

        //Şehir listesini getir
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sehirList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PRcity.setAdapter(adapter);

        //Bölüm listesini getir
        ArrayAdapter<CharSequence> adapterBolum = ArrayAdapter.createFromResource(this,
                R.array.bolumList, android.R.layout.simple_spinner_item);
        adapterBolum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PRdepartment.setAdapter(adapterBolum);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ayarlar_menu, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hakkindaEkran:
                Intent i = new Intent(getApplicationContext(), Hakkinda.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
