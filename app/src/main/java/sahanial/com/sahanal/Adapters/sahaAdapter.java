package sahanial.com.sahanal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import sahanial.com.sahanal.Activities.MainActivity;
import sahanial.com.sahanal.Activities.RandevuDetay;
import sahanial.com.sahanal.Activities.SahaDetayActivity;
import sahanial.com.sahanal.Models.Saha;
import sahanial.com.sahanal.R;

public class sahaAdapter extends ArrayAdapter<Saha> {
    private Context context;
    private List<Saha> sahalar;
    private DatabaseReference databaseReference;
    private EditText ad;
    private EditText ozellik;
    private EditText genislik;
    private EditText yukseklik;

    public sahaAdapter(Activity context, List<Saha> sahalar,DatabaseReference databaseReference,EditText ad,EditText ozellik,EditText genislik,EditText yukseklik){
        super(context,R.layout.saha_list,sahalar);
        this.context=context;
        this.sahalar=sahalar;
        this.databaseReference=databaseReference;
        this.ad=ad;
        this.ozellik=ozellik;
        this.genislik=genislik;
        this.yukseklik=yukseklik;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listViewItem=inflater.inflate(R.layout.saha_list,null,true);
        final TextView sahaAd=listViewItem.findViewById(R.id.txtSahaAdi);
        final TextView sahaOzellik=listViewItem.findViewById(R.id.txtSahaOzellik);
        final TextView sahaGenislik=listViewItem.findViewById(R.id.txtSahaGenislik);
        final TextView sahaYukseklik=listViewItem.findViewById(R.id.txtSahaYukseklik);
        Button detay=listViewItem.findViewById(R.id.btnDetay);


        final Saha saha =sahalar.get(position);

        //Saha listelenirken saha adi 8 karakter olarak gosterilecek.
        String strSahaAd;
        strSahaAd=saha.getSahaAd();
        if(strSahaAd.length()<8){
            for (int i=strSahaAd.length();i<8;i++){
                strSahaAd+=".";
            }
        }
        else{
            strSahaAd=strSahaAd.substring(0,7)+".";
        }
        sahaAd.setText(strSahaAd);

        //Saha listelenirken saha ozelligi 8 karakter olarak gosterilecek.
        String strSahaOzellik;
        strSahaOzellik=saha.getSahaOzellik();
        if(strSahaOzellik.length()<8){
            for (int i=strSahaOzellik.length();i<8;i++){
                strSahaOzellik+=".";
            }
        }
        else{
            strSahaOzellik=strSahaOzellik.substring(0,7)+".";
        }
        sahaOzellik.setText(strSahaOzellik);

        String strSahaGenislik;
        strSahaGenislik=saha.getSahaGenislik();
        if(strSahaGenislik.length()<8){
            for (int i=strSahaGenislik.length();i<8;i++){
                strSahaGenislik+=".";
            }
        }
        else{
            strSahaGenislik=strSahaOzellik.substring(0,7)+".";
        }
        sahaGenislik.setText(strSahaGenislik);

        String strSahaYukseklik;
        strSahaYukseklik=saha.getSahaYukseklik();
        if(strSahaYukseklik.length()<8){
            for (int i=strSahaYukseklik.length();i<8;i++){
                strSahaYukseklik+=".";
            }
        }
        else{
            strSahaYukseklik=strSahaYukseklik.substring(0,7)+".";
        }
        sahaYukseklik.setText(strSahaYukseklik);


        detay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deneme=new Intent(getContext(),SahaDetayActivity.class);
                deneme.putExtra("SahaID",saha.getSahaID().toString());
                deneme.putExtra("SahaName",saha.getSahaAd().toString());
                deneme.putExtra("SahaProperty",saha.getSahaOzellik().toString());
                deneme.putExtra("SahaWidth",saha.getSahaGenislik().toString());
                deneme.putExtra("SahaHeight",saha.getSahaYukseklik().toString());
                context.startActivity(deneme);
            }
        });

        return listViewItem;
    }
}
