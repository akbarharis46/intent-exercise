package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_PASSWORD_KEY = "confirmPassword";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private EditText fullnameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText homePageInput;
    private EditText aboutInput;
    private ImageView imageInput;

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageInput.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageInput.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                            if (data != null) {
                                try {
                                    Uri imageUri = data.getData();
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                    imageInput.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }

                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullnameInput = findViewById(R.id.fullnameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirm_passwordInput);
        homePageInput = findViewById(R.id.homepageInput);
        aboutInput = findViewById(R.id.aboutInput);

        imageInput = (ImageView) findViewById(R.id.image_profile);
        Button btn = findViewById(R.id.editImage);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectImage(RegisterActivity.this);
            }
        });
    }

    public void handleProfile(View view) {
        if((fullnameInput.getText().toString().length()==0) && (emailInput.getText().toString().length()==0) && (passwordInput.getText().toString().length()==0) && (confirmPasswordInput.getText().toString().length()==0) && (homePageInput.getText().toString().length()==0) && (aboutInput.getText().toString().length()==0)){
            fullnameInput.setError("Isi Fullname anda");
            emailInput.setError("Isi Email anda");
            passwordInput.setError("Isi Password");
            confirmPasswordInput.setError("Isi Password");
            homePageInput.setError("Isi Homepage");
            aboutInput.setError("Isi About");
        }
        else if(fullnameInput.getText().toString().length()==0){
            fullnameInput.setError("Isi Fullname anda");
        }
        else if(emailInput.getText().toString().length()==0){
            emailInput.setError("Isi Email anda");
        }
        else if(passwordInput.getText().toString().length()==0){
            passwordInput.setError("Isi Password");
        }
        else if(confirmPasswordInput.getText().toString().length()==0){
            confirmPasswordInput.setError("Isi Password");
        }
        else if(homePageInput.getText().toString().length()==0){
            homePageInput.setError("Isi Homepage");
        }
        else if(aboutInput.getText().toString().length()==0){
            aboutInput.setError("Isi About");
        }
        else{
            Intent intentTrue = new Intent(this, ProfileActivity.class);
            String fullname = fullnameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();
            String homepage = homePageInput.getText().toString();
            String about = aboutInput.getText().toString();

            intentTrue.putExtra(FULLNAME_KEY, fullname);
            intentTrue.putExtra(EMAIL_KEY, email);
            intentTrue.putExtra(PASSWORD_KEY, password);
            intentTrue.putExtra(CONFIRM_PASSWORD_KEY, confirmPassword);
            intentTrue.putExtra(HOMEPAGE_KEY, homepage);
            intentTrue.putExtra(ABOUT_KEY, about);
            startActivity(intentTrue);
        }
    }
}