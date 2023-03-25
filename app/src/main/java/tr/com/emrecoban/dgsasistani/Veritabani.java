package tr.com.emrecoban.dgsasistani;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. SHEPHERD on 11/25/2017.
 */

public class Veritabani {
    private static final String Database_Ad = "DGSVB.db";
    private static final String Table_Ad = "DenemeList";
    private static final int Database_Vers = 1;

    private final Context contextim;
    private VeritabaniHelper veritabanihelper;
    private SQLiteDatabase veritabanim;

    //Tablonun Sütunları
    public static final String KEY_id = "_id";
    public static final String KEY_denemead = "denemead";
    public static final String KEY_saynet = "saynet";
    public static final String KEY_soznet = "soznet";
    public static final String KEY_puansay = "puansay";
    public static final String KEY_puansoz = "puansoz";
    public static final String KEY_puanea = "puanea";
    public static final String KEY_tarih = "tarih";

    public Veritabani(Context c){
        this.contextim = c;
    }

    public Veritabani baglantiAc() throws SQLException{
        veritabanihelper = new VeritabaniHelper(contextim);
        veritabanim = veritabanihelper.getWritableDatabase();
        return this;
    }
    public void baglantiKapat(){
        veritabanihelper.close();
    }

    public void veriEkle(String denemead, String saynet, String soznet, String puansay, String puansoz, String puanea, String tarih) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_denemead, denemead);
        cv.put(KEY_saynet, saynet);
        cv.put(KEY_soznet, soznet);
        cv.put(KEY_puansay, puansay);
        cv.put(KEY_puansoz, puansoz);
        cv.put(KEY_puanea, puanea);
        cv.put(KEY_tarih, tarih);
        veritabanim.insert(Table_Ad, null, cv);
    }
    public void veriSil(Integer _id) {
        veritabanim.delete(Table_Ad, KEY_id + "=" + _id, null);
    }


    public List<DenemeObject> tumKayitlar() {
        Cursor c = veritabanim.rawQuery("Select * From "+ Table_Ad+" ORDER by _id Desc",null);
        List<DenemeObject> denemeler = new ArrayList<>();

        DenemeObject denemeObject = null;

        if(c.moveToFirst()){//kayıt varsa
            for(int ii=0; ii<c.getCount(); ii++){//döngüyü bitene kadar devam ettir

                denemeObject = new DenemeObject();
                denemeObject.set_id(c.getInt(0));
                denemeObject.setDenemeAd(c.getString(1));
                denemeObject.setSayNet(c.getString(2));
                denemeObject.setSozNet(c.getString(3));
                denemeObject.setPuanSay(c.getString(4));
                denemeObject.setPuanSoz(c.getString(5));
                denemeObject.setPuanEA(c.getString(6));
                denemeObject.setTarih(c.getString(7));

                denemeler.add(denemeObject);
                c.moveToNext();

            }

        }

        return denemeler;
    }

    private static class VeritabaniHelper extends SQLiteOpenHelper {

        public VeritabaniHelper(Context context){
            super(context, Database_Ad, null, Database_Vers);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+Table_Ad+"("
                    + KEY_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_denemead+" TEXT,"
                    + KEY_saynet+" TEXT,"
                    + KEY_soznet+" TEXT,"
                    + KEY_puansay+" TEXT,"
                    + KEY_puansoz+" TEXT,"
                    + KEY_puanea+" TEXT,"
                    + KEY_tarih+" TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+ Table_Ad);
            onCreate(db);
        }
    }
}
