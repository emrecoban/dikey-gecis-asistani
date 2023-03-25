package tr.com.emrecoban.dgsasistani;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by Mr. SHEPHERD on 11/26/2017.
 */

public class BagirCagir extends AppCompatActivity {

    ArrayList<Item> animalList=new ArrayList<>();
    private static String commentURL;
    JSONArray commentArray, userArray;


    ImageView btnGonder;
    EditText gelenVeri, aranacak;
    TextView kalankarakter, quote, quoteCancel,quoteID, commentID, reportOk, reportCancel, reportMesaj;
    LinearLayout reportLL, userLL;

    SwipeRefreshLayout swipeLayout, swipe_container;
    ListView userList;

    ImageView btnsort, onlineuser;
    TextView onlineTitle;
    String[] listSecenek;

    private static final String TAG = "Bağır Çağır";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bagircagir);
        getSupportActionBar().setTitle("Bağır Çağır");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        userLL = (LinearLayout) findViewById(R.id.userLL);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Tazele();
            }
        });

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


        //verileri getir
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String SPusername = preferences.getString("SPusername", "null");
        final String SPuserpass = preferences.getString("SPuserpass", "0");

        commentURL = "http://dgs.emrecoban.com.tr/comment/commentDGS.php?filtre=0&userName=" + SPusername;

        //Bahsedenler Seçimi
        listSecenek = new String[]{"Herkes", "Bahsedenler"};
        btnsort = (ImageView) findViewById(R.id.btnsort);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        btnsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Filtrele")
                        .setItems(listSecenek, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0){
                                    commentURL = "http://dgs.emrecoban.com.tr/comment/commentDGS.php?filtre=0&userName=" + SPusername;
                                    swipeLayout.setRefreshing(true);
                                    Tazele();
                                }else{
                                    if(!SPusername.equals("0")) {
                                        commentURL = "http://dgs.emrecoban.com.tr/comment/commentDGS.php?filtre=1&userName=" + SPusername;
                                        swipeLayout.setRefreshing(true);
                                        Tazele();
                                    }
                                }


                            }
                        })
                ;
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        reportLL = (LinearLayout) findViewById(R.id.reportLL);
        commentID = (TextView) findViewById(R.id.commentID);
        reportMesaj = (TextView) findViewById(R.id.reportMesaj);
        reportOk = (TextView) findViewById(R.id.reportOk);
        reportCancel = (TextView) findViewById(R.id.reportCancel);

        btnGonder = (ImageView) findViewById(R.id.btnYorumGit);
        gelenVeri = (EditText) findViewById(R.id.etGidenYorum);
        kalankarakter = (TextView) findViewById(R.id.kalankarakter);
        quote = (TextView) findViewById(R.id.quote);
        quoteCancel = (TextView) findViewById(R.id.quoteCancel);
        quoteID = (TextView) findViewById(R.id.quoteID);

        quoteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quote.setVisibility(View.GONE);
                quoteCancel.setVisibility(View.GONE);
                quoteID.setText("0");
            }
        });

        reportCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportLL.setVisibility(View.GONE);
                commentID.setText("0");
            }
        });


        //Gönder Butonunu Göster, Filtrelemeyi Gizle
        gelenVeri.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(gelenVeri.length()>3){
                    btnGonder.setVisibility(View.VISIBLE);
                    btnsort.setVisibility(View.GONE);
                    onlineuser.setVisibility(View.GONE);
                }else{
                    btnGonder.setVisibility(View.GONE);
                    btnsort.setVisibility(View.VISIBLE);
                    onlineuser.setVisibility(View.VISIBLE);
                }
                int hesaplakarakter = 300 - gelenVeri.length();
                kalankarakter.setText(String.valueOf(hesaplakarakter));
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //Online Kullanıcı Listesi
        onlineuser = (ImageView) findViewById(R.id.onlineuser);
        onlineTitle = (TextView) findViewById(R.id.onlineTitle);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        userList = (ListView) findViewById(R.id.userList);

        onlineuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onlineTitle.getVisibility()==View.GONE){
                    onlineTitle.setVisibility(View.VISIBLE);
                    userLL.setVisibility(View.VISIBLE);
                    btnsort.setVisibility(View.GONE);
                    swipe_container.setVisibility(View.GONE);
                    gelenVeri.setVisibility(View.GONE);
                    kalankarakter.setVisibility(View.GONE);
                    quote.setVisibility(View.GONE);
                    quoteCancel.setVisibility(View.GONE);
                    reportLL.setVisibility(View.GONE);
                }else{
                    userLL.setVisibility(View.GONE);
                    onlineTitle.setVisibility(View.GONE);
                    btnsort.setVisibility(View.VISIBLE);
                    swipe_container.setVisibility(View.VISIBLE);
                    gelenVeri.setVisibility(View.VISIBLE);
                    kalankarakter.setVisibility(View.VISIBLE);
                }
            }
        });

        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                klavyeyikapat();
                btnGonder.setEnabled(false);
                btnGonder.setVisibility(View.GONE);
                quote.setVisibility(View.GONE);
                quoteCancel.setVisibility(View.GONE);
                gelenVeri.setEnabled(false);
                if(gelenVeri.length()>3 && !SPuserpass.equals("0")) {

                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            if(!s.equals("0")) {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                            }
                            gelenVeri.setText("");
                            quoteID.setText("0");
                            Tazele();
                        }
                    };

                    PostRequest pr = new PostRequest(SPusername, SPuserpass, gelenVeri.getText().toString(), quoteID.getText().toString(), responselistener);
                    RequestQueue queue = Volley.newRequestQueue(BagirCagir.this);
                    queue.add(pr);
                    btnGonder.setVisibility(View.VISIBLE);
                    btnGonder.setEnabled(true);
                    gelenVeri.setEnabled(true);
                }else{
                    Toast.makeText(getApplicationContext(), "Mesaj çok kısa veya kayıtlı değilsiniz. Ayarlar > Kayıt Ol", Toast.LENGTH_SHORT).show();
                    btnGonder.setVisibility(View.VISIBLE);
                    btnGonder.setEnabled(true);
                    gelenVeri.setEnabled(true);
                }
            }
        });

        reportOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        commentID.setText("");
                        reportLL.setVisibility(View.GONE);
                    }
                };
                PostReport pr = new PostReport(SPusername, SPuserpass, commentID.getText().toString(), responselistener);
                RequestQueue queue = Volley.newRequestQueue(BagirCagir.this);
                queue.add(pr);
            }
        });






        StringRequest request = new StringRequest(commentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                swipeLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(BagirCagir.this);
        rQueue.add(request);


        // Kullanıcı arama
        aranacak = (EditText) findViewById(R.id.aranacak);
        aranacak.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                            StringRequest requestUSER = new StringRequest("http://dgs.emrecoban.com.tr/comment/usersDGS.php?aranacak=" + aranacak.getText().toString(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    parseJsonUserData(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            RequestQueue rQueueUSER = Volley.newRequestQueue(BagirCagir.this);
                            rQueueUSER.add(requestUSER);

                    klavyeyikapat();
                    return true;
                }
                return false;
            }
        });


        StringRequest requestUSER = new StringRequest("http://dgs.emrecoban.com.tr/comment/usersDGS.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonUserData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueueUSER = Volley.newRequestQueue(BagirCagir.this);
        rQueueUSER.add(requestUSER);
    }

    private void klavyeyikapat() {
        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnGonder.getWindowToken(), 0);
    }

    private void Tazele(){
        StringRequest request = new StringRequest(commentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
                swipeLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Bağlantı kurulamadı.", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(BagirCagir.this);
        rQueue.add(request);
    }



    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            commentArray = object.getJSONArray("comment");

            ArrayList ArrayID = new ArrayList();
            ArrayList ArrayUsername = new ArrayList();
            ArrayList ArrayAvatar = new ArrayList();
            ArrayList ArrayComment = new ArrayList();
            ArrayList ArrayQuote = new ArrayList();
            ArrayList ArrayDate = new ArrayList();
            ArrayList ArrayuserRName = new ArrayList();
            ArrayList ArrayDep = new ArrayList();


            for(int i = 0; i < commentArray.length(); ++i) {


                JSONObject objeyiTanimla = commentArray.getJSONObject(i);
                ArrayID.add(objeyiTanimla.getString("ComID"));
                ArrayUsername.add(objeyiTanimla.getString("commentUsername"));
                ArrayuserRName.add(objeyiTanimla.getString("commentuserRName"));
                ArrayDep.add(objeyiTanimla.getString("commentDep"));
                ArrayAvatar.add(objeyiTanimla.getInt("commentAvatar"));
                ArrayComment.add(objeyiTanimla.getString("commentComment"));
                ArrayQuote.add(objeyiTanimla.getString("commentQuote"));
                ArrayDate.add(objeyiTanimla.getString("commentDate"));

            }

            CommentListesi commentAdapter = new CommentListesi(this, ArrayID, ArrayUsername, ArrayuserRName, ArrayDep, ArrayAvatar, ArrayComment, ArrayQuote, ArrayDate);
            ListView CommentList = (ListView) findViewById(R.id.comList);
            CommentList.setAdapter(commentAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void parseJsonUserData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            userArray = object.getJSONArray("user");


            ArrayList ArrayonlineRName = new ArrayList();
            ArrayList ArrayonlineName = new ArrayList();
            ArrayList ArrayonlineUpdate = new ArrayList();
            ArrayList ArrayonlineAvatar = new ArrayList();
            ArrayList ArrayonlineDep = new ArrayList();
            ArrayList ArrayonlinePoint = new ArrayList();
            ArrayList ArrayonlineCity = new ArrayList();


            for(int i = 0; i < userArray.length(); ++i) {


                JSONObject objeyiTanimla = userArray.getJSONObject(i);
                ArrayonlineRName.add(objeyiTanimla.getString("userRName"));
                ArrayonlineName.add(objeyiTanimla.getString("userName"));
                ArrayonlineUpdate.add(objeyiTanimla.getString("userUpDate"));
                ArrayonlineAvatar.add(objeyiTanimla.getInt("userAvatar"));
                ArrayonlineDep.add(objeyiTanimla.getString("userDep"));
                ArrayonlinePoint.add(objeyiTanimla.getString("userPoint"));
                ArrayonlineCity.add(objeyiTanimla.getString("userCity"));

            }

            UserListesi userAdapter = new UserListesi(this, ArrayonlineRName, ArrayonlineName, ArrayonlineUpdate, ArrayonlineAvatar, ArrayonlineDep, ArrayonlinePoint, ArrayonlineCity);
            userList = (ListView) findViewById(R.id.userList);
            userList.setAdapter(userAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public class CommentListesi extends BaseAdapter {

        ArrayList<String> comID;
        ArrayList<String> comUsername;
        ArrayList<String> comuserRName;
        ArrayList<String> comDep;
        ArrayList<Integer> comAvatar;
        ArrayList<String> comComment;
        ArrayList<String> comQuote;
        ArrayList<String> comDate;

        Context mContext;
        //constructor
        public CommentListesi(Context mContext, ArrayList<String> comID, ArrayList<String> comUsername, ArrayList<String> comuserRName, ArrayList<String> comDep, ArrayList<Integer> comAvatar, ArrayList<String> comComment, ArrayList<String> comQuote, ArrayList<String> comDate) {
            this.mContext = mContext;
            this.comID = comID;
            this.comUsername = comUsername;
            this.comuserRName = comuserRName;
            this.comDep = comDep;
            this.comAvatar = comAvatar;
            this.comComment = comComment;
            this.comQuote = comQuote;
            this.comDate = comDate;
        }

        @Override
        public int getCount() {
            return commentArray.length();
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
            view = getLayoutInflater().inflate(R.layout.bagircagir_list, null);

            TextView tvcomID = (TextView)view.findViewById(R.id.comID);
            TextView tvcomUsername = (TextView)view.findViewById(R.id.comuserName);
            TextView tvcomuserRName = (TextView)view.findViewById(R.id.comuserRName);
            TextView tvcomDep = (TextView)view.findViewById(R.id.comDep);
            TextView tvcomComment = (TextView)view.findViewById(R.id.comComment);
            TextView tvgelenQuote = (TextView)view.findViewById(R.id.gelenQuote);
            TextView tvcomDate = (TextView)view.findViewById(R.id.comDate);
            ImageView tvcomAvatar = (ImageView) view.findViewById(R.id.comAvatar);
            ImageView tvcomReply = (ImageView)view.findViewById(R.id.comReply);
            ImageView tvcomQuote = (ImageView)view.findViewById(R.id.comQuote);
            ImageView tvcomReport = (ImageView)view.findViewById(R.id.comReport);

            tvcomID.setText(comID.get(i));
            tvcomUsername.setText(comUsername.get(i));
            tvcomuserRName.setText(comuserRName.get(i));
            tvcomDep.setText(comDep.get(i));
            tvcomComment.setText(comComment.get(i));
            tvgelenQuote.setText(comQuote.get(i));
            tvcomDate.setText(comDate.get(i));
            tvcomAvatar.setImageResource(comAvatar.get(i));

            final String cvpKll = comUsername.get(i);
            final String yorumAl = comComment.get(i);
            final String yorumID = comID.get(i);


            tvcomReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reportLL.setVisibility(View.VISIBLE);
                    reportMesaj.setText("@" + cvpKll + ": " +yorumAl);
                    commentID.setText(""+yorumID);
                }
            });

            if(!comQuote.get(i).toString().equals("0")){
                tvgelenQuote.setVisibility(View.VISIBLE);
            }


            tvcomReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gelenVeri.setText(gelenVeri.getText().toString() +  "@" + cvpKll + " ");
                    gelenVeri.setSelection(gelenVeri.getText().length());
                }
            });


            tvcomQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quote.setVisibility(View.VISIBLE);
                    quoteCancel.setVisibility(View.VISIBLE);
                    gelenVeri.setText(gelenVeri.getText().toString() +  "@" + cvpKll + " ");
                    gelenVeri.setSelection(gelenVeri.getText().length());
                    quote.setText("@" + cvpKll + ": " +yorumAl);
                    quoteID.setText(""+yorumID);
                }
            });

            return view;
        }
    }



    public class UserListesi extends BaseAdapter {

        ArrayList<String> onlineRName;
        ArrayList<String> onlineName;
        ArrayList<String> onlineUpdate;
        ArrayList<Integer> onlineAvatar;
        ArrayList<String> onlineDep;
        ArrayList<String> onlinePoint;
        ArrayList<String> onlineCity;

        Context mContext;
        //constructor
        public UserListesi(Context mContext, ArrayList<String> onlineRName, ArrayList<String> onlineName, ArrayList<String> onlineUpdate, ArrayList<Integer> onlineAvatar, ArrayList<String> onlineDep, ArrayList<String> onlinePoint, ArrayList<String> onlineCity) {
            this.mContext = mContext;
            this.onlineRName = onlineRName;
            this.onlineName = onlineName;
            this.onlineUpdate = onlineUpdate;
            this.onlineAvatar = onlineAvatar;
            this.onlineDep = onlineDep;
            this.onlinePoint = onlinePoint;
            this.onlineCity = onlineCity;
        }

        @Override
        public int getCount() {
            return userArray.length();
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
            view = getLayoutInflater().inflate(R.layout.users_list, null);

            ImageView tvonlineAvatar = (ImageView) view.findViewById(R.id.onlineAvatar);

            TextView tvonlineRName = (TextView)view.findViewById(R.id.onlineRName);
            TextView tvonlineName = (TextView)view.findViewById(R.id.onlineName);
            TextView tvonlineUpdate = (TextView)view.findViewById(R.id.onlineUpdate);
            TextView tvonlineDep = (TextView)view.findViewById(R.id.onlineDep);
            TextView tvonlinePoint = (TextView)view.findViewById(R.id.onlinePoint);
            TextView tvonlineCity = (TextView)view.findViewById(R.id.onlineCity);

            LinearLayout LLonlineUserBar = (LinearLayout)view.findViewById(R.id.onlineUserBar);
            final LinearLayout LLuserContent = (LinearLayout)view.findViewById(R.id.userContent);

            tvonlineRName.setText(onlineRName.get(i));
            tvonlineName.setText(onlineName.get(i));
            tvonlineUpdate.setText(onlineUpdate.get(i));
            tvonlineDep.setText(onlineDep.get(i));
            tvonlinePoint.setText(onlinePoint.get(i));
            tvonlineCity.setText(onlineCity.get(i));
            tvonlineAvatar.setImageResource(onlineAvatar.get(i));

            LLonlineUserBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView IVonlineML = (ImageView) view.findViewById(R.id.onlineML);
                    if(LLuserContent.getVisibility()==View.GONE){
                        IVonlineML.setImageResource(R.mipmap.onlineless);
                        LLuserContent.setVisibility(View.VISIBLE);
                    }else{
                        IVonlineML.setImageResource(R.mipmap.onlinemore);
                        LLuserContent.setVisibility(View.GONE);
                    }
                }
            });


            return view;
        }
    }
    @Override
    public boolean onSupportNavigateUp(){//actionbar geri gel
        finish();
        return true;
    }
}
