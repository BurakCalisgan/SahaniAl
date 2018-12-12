package sahanial.com.sahanal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningScreenActivity extends AppCompatActivity {

    Button btnSignInPage;
    Button btnRegisterPage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

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
                Intent signInPage = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(signInPage);
            }
        });
    }
}
