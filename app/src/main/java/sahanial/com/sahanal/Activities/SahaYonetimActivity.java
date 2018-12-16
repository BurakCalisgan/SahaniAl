package sahanial.com.sahanal.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sahanial.com.sahanal.Adapters.sahaAdapter;
import sahanial.com.sahanal.Models.Saha;
import sahanial.com.sahanal.R;

public class SahaYonetimActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    ArrayList<Saha> sahalar;
    EditText txtSahaAdi;
    EditText txtSahaOzellik;
    EditText txtSahaGenislik;
    EditText txtSahaYukseklik;
    Button btnSend;
    public static String ID;
    ListView listSaha;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id,sahaAd,sahaOzellik,sahaGenislik,sahaYukseklik;
                sahalar.clear();
                for(DataSnapshot sahaSnapshot:dataSnapshot.getChildren()){
                    Map<String,String> map = (Map<String,String>) sahaSnapshot.getValue();
                    id=map.get("sahaID");
                    sahaAd=map.get("sahaAd");
                    sahaOzellik=map.get("sahaOzellik");
                    sahaGenislik=map.get("sahaGenislik");
                    sahaYukseklik=map.get("sahaYukseklik");
                    Saha saha=new Saha(id,sahaAd,sahaOzellik,sahaGenislik,sahaYukseklik);
                    sahalar.add(saha);
                }
                sahaAdapter adapter=new sahaAdapter(SahaYonetimActivity.this,sahalar,databaseReference);
                listSaha.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saha_yonetim);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        databaseReference =FirebaseDatabase.getInstance().getReference("sahalar/"+user.getUid().toString());

        listSaha=findViewById(R.id.listSaha);

        sahalar=new ArrayList<>();
        txtSahaAdi=findViewById(R.id.editTextSahaAdi);
        txtSahaOzellik=findViewById(R.id.editTextSahaOzellik);
        txtSahaGenislik=findViewById(R.id.editTextSahaGenislik);
        txtSahaYukseklik=findViewById(R.id.editTextSahaYukseklik);
        btnSend=findViewById(R.id.buttonSend);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(txtSahaAdi,txtSahaOzellik,txtSahaGenislik,txtSahaYukseklik)){
                    Toast.makeText(getApplicationContext(),"Ekleme Başarısız", Toast.LENGTH_LONG).show();
                }
                else{
                    ID=databaseReference.push().getKey();
                    Saha saha=new Saha(ID,txtSahaAdi.getText().toString(),txtSahaOzellik.getText().toString(),txtSahaGenislik.getText().toString(),txtSahaYukseklik.getText().toString());
                    databaseReference.child(ID).setValue(saha);
                    Toast.makeText(getApplicationContext(),"Yeni Saha Eklendi",Toast.LENGTH_LONG).show();
                    txtSahaAdi.setText("");
                    txtSahaOzellik.setText("");
                    txtSahaGenislik.setText("");
                    txtSahaYukseklik.setText("");
                }
            }
        });

    }

    boolean isEmpty(EditText et1,EditText et2,EditText et3,EditText et4){
        if(et1.getText().toString().isEmpty()||et2.getText().toString().isEmpty()||et3.getText().toString().isEmpty()||et4.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
}
