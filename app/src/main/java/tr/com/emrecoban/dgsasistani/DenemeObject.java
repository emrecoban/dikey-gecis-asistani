package tr.com.emrecoban.dgsasistani;

/**
 * Created by Mr. SHEPHERD on 11/24/2017.
 */

public class DenemeObject {
    int _id;
    String DenemeAd;
    String SayNet;
    String SozNet;
    String PuanSay;
    String PuanSoz;
    String PuanEA;
    String Tarih;

    public DenemeObject(){ super(); }

    public DenemeObject(String denemeAd, String sayNet, String sozNet, String puanSay, String puanSoz, String puanEA, String tarih) {
        super();
        DenemeAd = denemeAd;
        SayNet = sayNet;
        SozNet = sozNet;
        PuanSay = puanSay;
        PuanSoz = puanSoz;
        PuanEA = puanEA;
        Tarih = tarih;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDenemeAd() {
        return DenemeAd;
    }

    public void setDenemeAd(String denemeAd) {
        DenemeAd = denemeAd;
    }

    public String getSayNet() {
        return SayNet;
    }

    public void setSayNet(String sayNet) {
        SayNet = sayNet;
    }

    public String getSozNet() {
        return SozNet;
    }

    public void setSozNet(String sozNet) {
        SozNet = sozNet;
    }

    public String getPuanSay() {
        return PuanSay;
    }

    public void setPuanSay(String puanSay) {
        PuanSay = puanSay;
    }

    public String getPuanSoz() {
        return PuanSoz;
    }

    public void setPuanSoz(String puanSoz) {
        PuanSoz = puanSoz;
    }

    public String getPuanEA() {
        return PuanEA;
    }

    public void setPuanEA(String puanEA) {
        PuanEA = puanEA;
    }

    public String getTarih() {
        return Tarih;
    }

    public void setTarih(String tarih) {
        Tarih = tarih;
    }
}
