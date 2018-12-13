package sahanial.com.sahanal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sahanial.com.sahanal.Activities.LoginActivity;
import sahanial.com.sahanal.R;

public class OpeningScreenActivity extends AppCompatActivity {

    private Button btnSignInPage;
    private Button btnRegisterPage;
    private FirebaseAuth mAuth;
    private Intent MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        //inu views
        btnSignInPage = findViewById(R.id.btnSignInPage);
        btnRegisterPage = findViewById(R.id.btnRegisterPage);
        mAuth = FirebaseAuth.getInstance();
        MainActivity = new Intent(getApplicationContext(),sahanial.com.sahanal.Activities.MainActivity.class);

        btnSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInPage = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(signInPage);
            }
        });

        btnRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInPage = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(signInPage);
            }
        });
    }

    //Diğer sayfaya geçiş için yazıldı.
    private void updateUI() {

        startActivity(MainActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            //Kullanıcı zaten giriş yapmıştır. Bu yüzden onu MainActivity'ye yönlendirmemiz gerek.

            updateUI();

        }
    }

}
