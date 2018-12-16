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

    public sahaAdapter(Activity context, List<Saha> sahalar){
        super(context,R.layout.saha_list,sahalar);
        this.context=context;
        this.sahalar=sahalar;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listViewItem=inflater.inflate(R.layout.saha_list,null,true);
        TextView sahaAd=listViewItem.findViewById(R.id.txtSahaAdi);
        TextView sahaOzellik=listViewItem.findViewById(R.id.txtSahaOzellik);
        TextView sahaGenislik=listViewItem.findViewById(R.id.txtSahaGenislik);
        TextView sahaYukseklik=listViewItem.findViewById(R.id.txtSahaYukseklik);

        Saha saha =sahalar.get(position);
        sahaAd.setText(saha.getSahaAd());
        sahaOzellik.setText(saha.getSahaOzellik());
        sahaGenislik.setText(saha.getSahaGenislik());
        sahaYukseklik.setText(saha.getSahaYukseklik());

        return listViewItem;
    }
}
