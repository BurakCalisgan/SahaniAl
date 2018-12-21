package sahanial.com.sahanal.Activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sahanial.com.sahanal.Models.Saha;
import sahanial.com.sahanal.R;

public class SahaDetayActivity extends AppCompatActivity {
    EditText eTxtSahaName;
    EditText eTxtSahaProperty;
    EditText eTxtSahaWidth;
    EditText eTxtSahaHeight;
    Button btnUpdate;
    Button btnDelete;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Saha saha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saha_detay);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        databaseReference =FirebaseDatabase.getInstance().getReference("sahalar/"+user.getUid().toString());

        eTxtSahaName=(EditText)findViewById(R.id.eTxtSahaNameD);
        eTxtSahaProperty=(EditText)findViewById(R.id.eTxtSahaPropertyD);
        eTxtSahaWidth=(EditText)findViewById(R.id.eTxtSahaWidthD);
        eTxtSahaHeight=(EditText)findViewById(R.id.eTxtSahaHeightD);
        btnUpdate=(Button)findViewById(R.id.buttonUpdateD);
        btnDelete=(Button)findViewById(R.id.buttonDeleteD);

        Intent intent=getIntent();
        saha=new Saha();
        saha.setSahaID(intent.getStringExtra("SahaID").toString());
        saha.setSahaAd(intent.getStringExtra("SahaName").toString());
        saha.setSahaOzellik(intent.getStringExtra("SahaProperty").toString());
        saha.setSahaGenislik(intent.getStringExtra("SahaWidth").toString());
        saha.setSahaYukseklik(intent.getStringExtra("SahaHeight").toString());


        eTxtSahaName.setText(saha.getSahaAd());
        eTxtSahaProperty.setText(saha.getSahaOzellik());
        eTxtSahaWidth.setText(saha.getSahaGenislik());
        eTxtSahaHeight.setText(saha.getSahaYukseklik());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(saha.getSahaID()).child("sahaAd").setValue(eTxtSahaName.getText().toString());
                databaseReference.child(saha.getSahaID()).child("sahaOzellik").setValue(eTxtSahaProperty.getText().toString());
                databaseReference.child(saha.getSahaID()).child("sahaGenislik").setValue(eTxtSahaWidth.getText().toString());
                databaseReference.child(saha.getSahaID()).child("sahaYukseklik").setValue(eTxtSahaHeight.getText().toString());
                Toast.makeText(getApplicationContext(),"Saha Başarıyla Güncellendi.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(saha.getSahaID()).removeValue();
                finish();
            }
        });

    }
}
