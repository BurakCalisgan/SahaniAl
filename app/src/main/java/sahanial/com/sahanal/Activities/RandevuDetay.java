package sahanial.com.sahanal.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import sahanial.com.sahanal.Models.RandevuModel;
import sahanial.com.sahanal.R;

import static java.lang.Integer.valueOf;

public class RandevuDetay extends AppCompatActivity {

    EditText txTel;
    EditText txAdSoyad;
    EditText txSaat;
    TextView dtTarih;
    Spinner ddSahalar;
    Button btnGuncelle;
    Button btnSil;
    String key;
    String tel;
    String adSoyad;
    String dolu;
    String saat;
    String tarih;
    String sahaAdi;
    Spinner sItems;
    private FirebaseAuth mAuth;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatabaseReference databaseReference;
    ArrayAdapter<String> adapter2;
    FirebaseUser user;
    List<String> sahalar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_detay);
        getWindow().setBackgroundDrawableResource(R.drawable.footballpitch);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();

        tel =intent.getStringExtra("tel");
        adSoyad =intent.getStringExtra("adSoyad");
        dolu =intent.getStringExtra("dolu");
        saat =intent.getStringExtra("saat");
        tarih =intent.getStringExtra("tarih");
        key =intent.getStringExtra("key");
        sahaAdi =intent.getStringExtra("sahaAdi");
        txTel=(EditText) findViewById(R.id.txTel);
        txAdSoyad=(EditText) findViewById(R.id.txAdSoyad);
        //txSaat=(EditText) findViewById(R.id.txSaat);
        dtTarih=(TextView) findViewById(R.id.txTarih);
        btnGuncelle =(Button) findViewById(R.id.btnGuncelle);
        btnSil = (Button) findViewById(R.id.btnSil);
        txTel.setText(tel);
        txAdSoyad.setText(adSoyad);

        dtTarih.setText(tarih);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
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
        sItems.setSelection(valueOf(saat));
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("randevular/"+currentUser.getUid()+"/"+tarih+"/"+key);
                myRef.removeValue();
                showMessage("Randevu Başarıyla Silinmiştir.");
                finish();
            }
        });
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txAdSoyad.getText().toString().trim().isEmpty()) {
                    showMessage("Lütfen Ad Soyad Alanını Boş Geçmeyiniz !");
                }
                else if (txTel.getText().toString().trim().isEmpty()) {
                    showMessage("Lütfen Telefon Numarası Alanını Boş Geçmeyiniz !");
                }
                else if (dtTarih.getText().toString().trim().isEmpty() || dtTarih.getText().toString().equals("Tarih Seçiniz")) {
                    showMessage("Lütfen Bir Tarih Seçiniz!");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("randevular/"+currentUser.getUid()+"/"+tarih+"/"+key);
                    myRef.removeValue();
                    RandevuModel model = new RandevuModel();

                    model.tarih=dtTarih.getText().toString();
                    model.tel=txTel.getText().toString();
                    model.adSoyad=txAdSoyad.getText().toString();
                    model.saat=sItems.getSelectedItem().toString();
                    model.sahaAdi=ddSahalar.getSelectedItem().toString();
                    DatabaseReference myRef2 = database.getReference("randevular/"+currentUser.getUid()+"/"+model.tarih+"/"+model.saat);
                    myRef2.setValue(model);
                    showMessage("Randevu Başarıyla Güncellenmiştir.");
                    finish();



                }


            }
        });
        dtTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RandevuDetay.this,
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
                RandevuDetay.this, android.R.layout.simple_spinner_item, sahalar);
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
                int counter=0;
                int sahaNo=0;
                for(DataSnapshot sahaSnapshot:dataSnapshot.getChildren()){
                    Map<String,String> map = (Map<String,String>) sahaSnapshot.getValue();
                    id=map.get("sahaID");
                    sahaAd=map.get("sahaAd");
                    if( sahaAd.equals(sahaAdi))
                        sahaNo=counter;
                    sahalar.add(sahaAd);
                    adapter2.notifyDataSetChanged();
                    counter++;
                }
                ddSahalar.setSelection(sahaNo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //Toast mesajı göstermek için basit bir metot
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }
}
