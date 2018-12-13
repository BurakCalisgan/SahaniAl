package sahanial.com.sahanal.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sahanial.com.sahanal.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent MainActivity;
    private ImageView imgvLoginUserPhoto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inu views
        userMail = findViewById(R.id.edtLoginMail);
        userPassword  = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginProgress = findViewById(R.id.progresbarLogin);
        mAuth = FirebaseAuth.getInstance();
        MainActivity = new Intent(getApplicationContext(),sahanial.com.sahanal.Activities.MainActivity.class);
        imgvLoginUserPhoto = findViewById(R.id.imgvLoginPhoto);


        loginProgress.setVisibility(View.INVISIBLE);

        imgvLoginUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity );

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if ( mail.isEmpty() || password.isEmpty() ){
                    showMessage("Lütfen Tüm Alanları Doğrulayın !");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    signIn(mail,password);
                }


            }
        });

    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful()){

                    showMessage("Başarılı Bir Şekilde Giriş Yapıldı.");
                    loginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    updateUI();


                }
                else{
                    showMessage("Giriş Yaparken Hata Oluştu !" + task.getException().getMessage());
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    //Diğer sayfaya geçiş için yazıldı.
    private void updateUI() {

        startActivity(MainActivity);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
