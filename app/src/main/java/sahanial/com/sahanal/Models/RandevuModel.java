package sahanial.com.sahanal.Models;

public class RandevuModel {
    public boolean dolu;
    public  String  tel;
    public String saat;
    public String sahaAdi;
    public  String adSoyad;
    public  RandevuModel()
    {

    }
    public RandevuModel(boolean dolu, String tel,String saat, String sahaAdi,String adSoyad)
    {
        this.adSoyad=adSoyad;
        this.dolu=dolu;
        this.tel=tel;
        this.saat=saat;
        this.sahaAdi=sahaAdi;
    }
}
