package sahanial.com.sahanal.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import sahanial.com.sahanal.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView userPhoto;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loadingProgress;
    private Button btnRegister;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setBackgroundDrawableResource(R.drawable.footballpitch);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //inu views
        userEmail = findViewById(R.id.edtRegisterMail);
        userPassword = findViewById(R.id.edtRegisterPassword);
        userPassword2 = findViewById(R.id.edtRegisterPassword2);
        userName = findViewById(R.id.edtRegisterName);
        userPhoto = findViewById(R.id.imgvUserPhoto);
        loadingProgress = findViewById(R.id.progresbarRegister);
        loadingProgress.setVisibility(View.INVISIBLE);
        btnRegister = findViewById(R.id.btnRegister);


        mAuth = FirebaseAuth.getInstance();



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegister.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String password  = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String name = userName.getText().toString();

                if ( email.isEmpty() || name.isEmpty() || password.isEmpty() || !password2.equals(password)){
                    //Yanlış birşeyler oluyor demektir.
                    //Hata mesajı göstermek gerekir

                    showMessage("Lütfen Tüm Alanları Doğrulayın !");
                    btnRegister.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                }
                else{
                    //Herşey doğru ve tüm alanlar doldurulmuştur. Kullanıcı hesabı oluşturmaya başlayabiliriz.
                    //Eğer eposta geçerliyse CreateUserAccount metodu kullanıcı oluşturmaya çalışacak.

                    CreateUserAccount(email,name,password);


                }




            }
        });


        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22){
                    checkAndRequestPermission();

                }
                else{
                    openGallery();

                }

            }
        });
    }

    //Bu metot belli bir eposta ve şifre ile kullanıcı hesabı oluşturacaktır.
    private void CreateUserAccount(String email, final String name, String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful()  ){

                            // Kullanıcı hesabı başarılı bir şekilde oluşturulmuştur.

                            showMessage("Kullanıcı Oluşturuldu.");

                            //Kullanıcı hesabını oluşturduktan sonra profil resmini ve ismi güncellememiz gerekiyor.
                            updateUserInfo(name,pickedImgUri,mAuth.getCurrentUser());


                        }
                        else{

                            // Kullanıcı hesabı oluşturma işlemi başarısız.

                            showMessage("Kullanıcı Oluşturulamadı. Hata !" + task.getException().getMessage());
                            btnRegister.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);

                        }
                    }
                });
    }

    //Kullanıcı resim ve fotoğrafı güncellenecek
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        //Önce kullanıcı fotoğrafını firebase depolama alanına yüklememiz ve url almamız gerekiyor.

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Resim başarıyla yüklendi.
                //Şimdi bizim resmimizin uri'sini alabiliriz.

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //uri kullanıcı resminin url'ni içerir.

                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            //Kullanıcı bilgileri başarıyla güncellenmişir.
                                            showMessage("Kayıt Başarılı.");
                                            updateUI();

                                        }
                                    }
                                });

                    }
                });


            }
        });



    }

    private void updateUI() {
        Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainActivity);
    }


    //Toast mesajı göstermek için basit bir metot
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        //Galeriyi intentte açacağı ve kullanıcının bir fotoğraf seçmesini bekleyeceğiz

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);

    }



    private void checkAndRequestPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this,"Lütfen Gerekli İzinleri Kabul Edin.",Toast.LENGTH_SHORT).show();
            }
            else{

                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else{
            openGallery();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null ) {

            // Kullanıcı başarıyla resim seçmiştir
            // Resmin referansını Uri değişkenine kaydetmemiz gerekir.
            pickedImgUri = data.getData() ;
            userPhoto.setImageURI(pickedImgUri);


        }

    }

}
