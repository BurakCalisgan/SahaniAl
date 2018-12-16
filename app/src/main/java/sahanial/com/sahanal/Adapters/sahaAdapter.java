package sahanial.com.sahanal.Adapters;

import android.app.Activity;
import android.content.Context;
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
import sahanial.com.sahanal.Activities.SahaYonetimActivity;
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
        Button update=listViewItem.findViewById(R.id.btnUpdate);
        Button delete=listViewItem.findViewById(R.id.btnDelete);

        final Saha saha =sahalar.get(position);
        sahaAd.setText(saha.getSahaAd());
        sahaOzellik.setText(saha.getSahaOzellik());
        sahaGenislik.setText(saha.getSahaGenislik());
        sahaYukseklik.setText(saha.getSahaYukseklik());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad.setText(saha.getSahaAd());
                ozellik.setText(saha.getSahaOzellik());
                genislik.setText(saha.getSahaGenislik());
                yukseklik.setText(saha.getSahaYukseklik());

                SahaYonetimActivity.ID=saha.getSahaID();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(saha.getSahaID()).removeValue();
                Toast.makeText(context,"Saha Başarıyla Silindi.",Toast.LENGTH_SHORT).show();
            }
        });

        return listViewItem;
    }
}