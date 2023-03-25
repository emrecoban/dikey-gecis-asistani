package tr.com.emrecoban.dgsasistani;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Mr. SHEPHERD on 4.07.2017.
 */

public class Bildirim extends AppCompatActivity {


    private static String duyuruURL = "http://dgs.emrecoban.com.tr/duyuru/duyuruList.php";
    ProgressDialog dialog;
    JSONArray duyuruArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bildirim);
        getSupportActionBar().setTitle("Bildirimler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Yükleniyor....");
        dialog.show();



        StringRequest request = new StringRequest(duyuruURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Bildirim.this);
        rQueue.add(request);

    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            duyuruArray = object.getJSONArray("duyuru");

            ArrayList ArrayTitle = new ArrayList();
            ArrayList ArrayText = new ArrayList();
            ArrayList ArrayCode = new ArrayList();
            ArrayList ArrayDate = new ArrayList();


            for(int i = 0; i < duyuruArray.length(); ++i) {


                JSONObject objeyiTanimla = duyuruArray.getJSONObject(i);
                ArrayTitle.add(objeyiTanimla.getString("duyuruTitle"));
                ArrayText.add(objeyiTanimla.getString("duyuruContent"));
                ArrayCode.add(objeyiTanimla.getString("duyuruCode"));
                ArrayDate.add(objeyiTanimla.getString("duyuruDate"));


            }


            BildiriListesi bildirimAdapter = new BildiriListesi(this, ArrayTitle, ArrayText, ArrayCode, ArrayDate);
            ListView BildirimList = (ListView) findViewById(R.id.BildirimList);
            BildirimList.setAdapter(bildirimAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }







    public class BildiriListesi extends BaseAdapter{

        ArrayList<String> duyuruTitle;
        ArrayList<String> duyuruText;
        ArrayList<String> duyuruCode;
        ArrayList<String> duyuruDate;

        Context mContext;
        //constructor
        public BildiriListesi(Context mContext, ArrayList<String> duyuruTitle, ArrayList<String> duyuruText, ArrayList<String> duyuruCode, ArrayList<String> duyuruDate) {
            this.mContext = mContext;
            this.duyuruTitle = duyuruTitle;
            this.duyuruText = duyuruText;
            this.duyuruCode = duyuruCode;
            this.duyuruDate = duyuruDate;
        }


        @Override
        public int getCount() {
            return duyuruArray.length();
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
            view = getLayoutInflater().inflate(R.layout.bildirim_list, null);

            TextView TVTitle = (TextView)view.findViewById(R.id.TVTitle);
            TextView TVText = (TextView)view.findViewById(R.id.TVText);
            TextView TVCode = (TextView)view.findViewById(R.id.TVCode);
            TextView TVDate = (TextView)view.findViewById(R.id.TVDate);

            TVTitle.setText(duyuruTitle.get(i));
            TVText.setText(duyuruText.get(i));
            TVCode.setText(duyuruCode.get(i));
            TVDate.setText(duyuruDate.get(i));

            return view;
        }
    }

    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }


}
