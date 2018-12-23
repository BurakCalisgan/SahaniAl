package sahanial.com.sahanal.Models;

import java.util.Date;

public class RandevuModel {
    public boolean dolu;
    public  String  tel;
    public String saat;
    public String sahaAdi;
    public  String adSoyad;
    public String key;
    public String tarih;
    public String sahaId;
    public  RandevuModel()
    {

    }
    public RandevuModel(boolean dolu, String tel,String saat, String sahaAdi,String adSoyad,String tarih,String sahaId)
    {
        this.adSoyad=adSoyad;
        this.dolu=dolu;
        this.tel=tel;
        this.saat=saat;
        this.sahaAdi=sahaAdi;
        this.tarih=tarih;
        this.sahaId=sahaId;
    }
}
