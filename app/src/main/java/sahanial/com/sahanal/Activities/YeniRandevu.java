package sahanial.com.sahanal.Activities;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import sahanial.com.sahanal.Adapters.sahaAdapter;
import sahanial.com.sahanal.Models.RandevuModel;
import sahanial.com.sahanal.Models.Saha;
import sahanial.com.sahanal.R;

public class YeniRandevu extends AppCompatActivity {

    EditText txTel;
    EditText txAdSoyad;
    EditText txSaat;
    TextView dtTarih;
    Spinner ddSahalar;
    Spinner ddSaatler;
    Button btnEkle;
    FirebaseUser currentUser;
    Spinner sItems;
    RandevuModel model;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ArrayAdapter<String> adapter2;
    FirebaseUser user;
    List<String> sahalar;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_randevu);
        txTel=(EditText) findViewById(R.id.txTel);
        txAdSoyad=(EditText) findViewById(R.id.txAdSoyad);
       // txSaat=(EditText) findViewById(R.id.txSaat);
        dtTarih=(TextView) findViewById(R.id.txTarih);
        btnEkle =(Button) findViewById(R.id.btnEkle);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //saatleri ekle
        List<String> spinnerArray =  new ArrayList<String>();
        for (int i=0; i<24 ;i++)
        {
            String counterStr=String.valueOf(i);
            spinnerArray.add(counterStr);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.ddSaatler);
        sItems.setAdapter(adapter);
        //saatleri ekle
        dtTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(YeniRandevu.this,
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
        sahalar =  new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(
                YeniRandevu.this, android.R.layout.simple_spinner_item, sahalar);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddSahalar = (Spinner) findViewById(R.id.ddSahalar);
        ddSahalar.setAdapter(adapter2);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        databaseReference =FirebaseDatabase.getInstance().getReference("sahalar/"+user.getUid().toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id,sahaAd,sahaOzellik,sahaGenislik,sahaYukseklik;

                for(DataSnapshot sahaSnapshot:dataSnapshot.getChildren()){
                    Map<String,String> map = (Map<String,String>) sahaSnapshot.getValue();
                    id=map.get("sahaID");
                    sahaAd=map.get("sahaAd");

                    sahalar.add(sahaAd);
                    adapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                model = new RandevuModel();
                model.sahaAdi=ddSahalar.getSelectedItem().toString();
                model.sahaId="11";
                model.tarih=dtTarih.getText().toString();
                model.tel=txTel.getText().toString();
                model.adSoyad=txAdSoyad.getText().toString();
                model.saat= sItems.getSelectedItem().toString();

                DatabaseReference myRef = database.getReference("randevular"+"/"+currentUser.getUid()+"/");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       Boolean varmi= dataSnapshot.child(model.tarih+"/"+model.saat).exists();

                        if(varmi)
                        {
                            Toast.makeText(YeniRandevu.this, "Bu tarih ve sate ait başka bir kayit zaten ekli",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseDatabase database = FirebaseDatabase.getInstance();


                        DatabaseReference myRef = database.getReference("randevular"+"/"+currentUser.getUid()+"/"+model.tarih+"/"+model.saat);
                        myRef.setValue(model);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });




            }
        });
    }
}
