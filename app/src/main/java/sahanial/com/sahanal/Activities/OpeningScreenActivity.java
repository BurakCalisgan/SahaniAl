package sahanial.com.sahanal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sahanial.com.sahanal.Activities.LoginActivity;
import sahanial.com.sahanal.R;

public class OpeningScreenActivity extends AppCompatActivity {

    Button btnSignInPage;
    Button btnRegisterPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        btnSignInPage = findViewById(R.id.btnSignInPage);
        btnRegisterPage = findViewById(R.id.btnRegisterPage);

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
}
