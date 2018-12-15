package sahanial.com.sahanal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sahanial.com.sahanal.Models.Saha;
import sahanial.com.sahanal.R;

public class SahaYonetimActivity extends AppCompatActivity {

    EditText txtSahaAdi;
    EditText txtSahaOzellik;
    EditText txtSahaGenislik;
    EditText txtSahaYukseklik;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saha_yonetim);

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        final DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("sahalar/"+user.getUid().toString());


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
                    String id=databaseReference.push().getKey();
                    Saha saha=new Saha(id,txtSahaAdi.getText().toString(),txtSahaOzellik.getText().toString(),txtSahaGenislik.getText().toString(),txtSahaYukseklik.getText().toString());
                    databaseReference.child(id).setValue(saha);
                    Toast.makeText(getApplicationContext(),"Yeni Saha Eklendi",Toast.LENGTH_LONG).show();
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
