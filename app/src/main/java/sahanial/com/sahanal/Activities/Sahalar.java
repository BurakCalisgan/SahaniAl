package sahanial.com.sahanal.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Sahalar extends Fragment {
    EditText eTxtSahaName;
    EditText eTxtSahaProperty;
    EditText eTxtSahaWidth;
    EditText eTxtSahaHeight;
    Button btnSend;
    ListView sahaList;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ArrayList<Saha> sahalar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sahalar, container, false);
        eTxtSahaName=(EditText)rootView.findViewById(R.id.editTextSahaAdi);
        eTxtSahaProperty=(EditText)rootView.findViewById(R.id.editTextSahaOzellik);
        eTxtSahaWidth=(EditText)rootView.findViewById(R.id.editTextSahaGenislik);
        eTxtSahaHeight=(EditText)rootView.findViewById(R.id.editTextSahaYukseklik);
        btnSend=(Button)rootView.findViewById(R.id.buttonSend);
        sahaList=(ListView)rootView.findViewById(R.id.listSaha);

        sahalar=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        databaseReference =FirebaseDatabase.getInstance().getReference("sahalar/"+user.getUid().toString());


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
                sahaAdapter adapter=new sahaAdapter(getActivity(),sahalar,databaseReference,eTxtSahaName,eTxtSahaProperty,eTxtSahaWidth,eTxtSahaHeight);
                sahaList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(eTxtSahaName,eTxtSahaProperty,eTxtSahaWidth,eTxtSahaHeight)){
                    Toast.makeText(getContext(),"Ekleme Başarısız", Toast.LENGTH_LONG).show();
                }
                else{

                        String id = databaseReference.push().getKey();
                        Saha saha = new Saha(id, eTxtSahaName.getText().toString(), eTxtSahaProperty.getText().toString(), eTxtSahaWidth.getText().toString(), eTxtSahaHeight.getText().toString());
                        databaseReference.child(id).setValue(saha);
                        Toast.makeText(getContext(), "Yeni Saha Eklendi", Toast.LENGTH_LONG).show();

                        eTxtSahaName.setText("");
                        eTxtSahaProperty.setText("");
                        eTxtSahaWidth.setText("");
                        eTxtSahaHeight.setText("");
                }
            }
        });

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String aa="aaa";
    }
    boolean isEmpty(EditText et1,EditText et2,EditText et3,EditText et4){
        if(et1.getText().toString().isEmpty()||et2.getText().toString().isEmpty()||et3.getText().toString().isEmpty()||et4.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
}
