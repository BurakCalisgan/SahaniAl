package sahanial.com.sahanal.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sahanial.com.sahanal.Models.RandevuModel;
import sahanial.com.sahanal.R;


public class Ranvdevular extends Fragment {

    Button btnYeni,btnSorgula;
    ListView liste;
    private FirebaseAuth mAuth;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ArrayList<RandevuModel> itemList;
    private TextView dtTarih;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner sItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ranvdevular, container, false);

        liste=(ListView) rootView.findViewById(R.id.liste);
        arrayList = new ArrayList<String>();
        itemList = new ArrayList<RandevuModel>();
        btnYeni = (Button) rootView.findViewById(R.id.btnYeni);
        dtTarih= (TextView) rootView.findViewById(R.id.dtTarih);
        btnSorgula = (Button) rootView.findViewById(R.id.btnSorgula);
        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data

        dtTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dtTarih.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(itemList.size()<1)
                    return view;
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date now =Calendar.getInstance().getTime();
                String str=itemList.get(position).tarih;
                Date randevu=format.parse(str,new ParsePosition(0));
                String date="Sat Jun 01 12:53:10 IST 2013";

                if( now.before(randevu))//önceyse
                    view.setBackgroundColor(Color.parseColor("#70cc20"));
                else
                    view.setBackgroundColor(Color.parseColor("#cc2020"));
                return view;
            }
        };
        btnYeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act = new Intent(getContext(),YeniRandevu.class);
                startActivity(act);
            }
        });
        // Here, you set the data in your ListView
        liste.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("randevular/"+currentUser.getUid()+"/");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();
                arrayList.clear();
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot innerZone: zoneSnapshot.getChildren())
                    {
                        //String tel= zoneSnapshot.child("tel").getValue(String.class);
                        RandevuModel value = innerZone.getValue(RandevuModel.class);
                        value.key=innerZone.getKey();
                        itemList.add(value);
                        arrayList.add(value.sahaAdi+"\nTel:"+value.tel+"\nTarih:"+value.tarih+"-"+value.saat+":00");
                        // arrayList.add(tel);
                        adapter.notifyDataSetChanged();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Error 404.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RandevuModel model=itemList.get(i);
                //view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                Toast.makeText(getContext(),"i:"+i+" id:"+model.key,Toast. LENGTH_SHORT).show();
                Intent act = new Intent(getContext(),RandevuDetay.class);
                act.putExtra("tel",model.tel);
                act.putExtra("adSoyad",model.adSoyad);
                act.putExtra("dolu",model.dolu);
                act.putExtra("saat",model.saat);
                act.putExtra("tarih",model.tarih);
                act.putExtra("sahaAdi",model.sahaAdi);
                act.putExtra("key",model.key);
                act.putExtra("sahaAdi",model.sahaAdi);
                startActivity(act );

            }
        });
        btnSorgula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, you set the data in your ListView
                liste.setAdapter(adapter);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                DatabaseReference myRef = database.getReference("randevular/"+currentUser.getUid()+"/"+dtTarih.getText().toString());


                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        itemList.clear();
                        arrayList.clear();
                        for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {

                            //String tel= zoneSnapshot.child("tel").getValue(String.class);
                            RandevuModel value = zoneSnapshot.getValue(RandevuModel.class);
                            value.key=zoneSnapshot.getKey();
                            itemList.add(value);
                            arrayList.add(value.sahaAdi+"\nTel:"+value.tel+"\nTarih:"+value.tarih+"-"+value.saat+":00");
                            // arrayList.add(tel);
                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getActivity(), "Error 404.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String aa="aaa";
    }

}
