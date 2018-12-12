package sahanial.com.sahanal.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import sahanial.com.sahanal.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView userPhoto;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userPhoto = findViewById(R.id.imgvUserPhoto);

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
