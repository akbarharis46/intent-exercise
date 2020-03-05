package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_PASSWORD_KEY = "confirmPassword";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private TextView fullnameOutput;
    private TextView emailOutput;
    private TextView passwordOutput;
    private TextView confirmPasswordOutput;
    private TextView homePageOutput;
    private TextView aboutOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullnameOutput = findViewById(R.id.fullnameOutput);
        emailOutput = findViewById(R.id.emailOutput);
        homePageOutput = findViewById(R.id.homepageOutput);
        aboutOutput = findViewById(R.id.aboutOutput);
        Button b = findViewById(R.id.button_homepage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String home = homePageOutput.getText().toString();
                web(home);
            }

            private void web(String home) {
                Uri webpage = Uri.parse(home);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            fullnameOutput.setText(extras.getString(FULLNAME_KEY));
            emailOutput.setText(extras.getString(EMAIL_KEY));
            homePageOutput.setText(extras.getString(HOMEPAGE_KEY));
            aboutOutput.setText(extras.getString(ABOUT_KEY));
        }

    }

    public void visit(String url) {

    }
}
