package sahanial.com.sahanal.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import sahanial.com.sahanal.R;

public class Router extends Fragment {


    Button btnSaha;
    Button btnRandevu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_router, container, false);
        //Intent sahaYonetim = new Intent(getContext(),SahaYonetimActivity.class);
        //startActivity(sahaYonetim);


        // Read from the database
        btnSaha=rootView.findViewById(R.id.buttonSahaYonetim);
        btnRandevu=rootView.findViewById(R.id.buttonRandevuYonetim);

        btnSaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sahaYonetim = new Intent(getContext(),SahaYonetimActivity.class);
                startActivity(sahaYonetim);
            }
        });
        btnRandevu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ranvdevular newGamefragment = new Ranvdevular();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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