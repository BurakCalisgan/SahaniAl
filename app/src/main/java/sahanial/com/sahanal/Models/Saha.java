package sahanial.com.sahanal.Models;

public class Saha {
    private String sahaID;
    private String sahaAd;
    private String sahaOzellik;
    private String sahaGenislik;
    private String sahaYukseklik;

    public Saha(String sahaID,String sahaAd,String sahaOzellik,String sahaGenislik,String sahaYukseklik){
        this.sahaID=sahaID;
        this.sahaAd=sahaAd;
        this.sahaOzellik=sahaOzellik;
        this.sahaGenislik=sahaGenislik;
        this.sahaYukseklik=sahaYukseklik;
    }

    public String getSahaID(){
        return sahaID;
    }
    public void setSahaID(String sahaID){
        this.sahaID=sahaID;
    }

    public String getSahaAd(){
        return sahaAd;
    }
    public void setSahaAd(String sahaAd){
        this.sahaAd=sahaAd;
    }

    public String getSahaOzellik(){
        return sahaOzellik;
    }
    public void setSahaOzellik(String sahaOzellik){
        this.sahaOzellik=sahaOzellik;
    }

    public String getSahaGenislik(){
        return sahaGenislik;
    }
    public void setSahaGenislik(String sahaGenislik){
        this.sahaGenislik=sahaGenislik;
    }

    public String getSahaYukseklik(){
        return sahaYukseklik;
    }
    public void setSahaYukseklik(String sahaYukseklik){
        this.sahaYukseklik=sahaYukseklik;
    }
}
