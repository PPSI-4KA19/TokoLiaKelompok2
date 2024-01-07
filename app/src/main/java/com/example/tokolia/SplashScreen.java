package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  Loading page sebelum ke Main
 *  delay set 3000milis
 */
public class SplashScreen extends AppCompatActivity {

    ImageView image;
    TextView loadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //define items
        image = findViewById(R.id.imageViewSplash);
        loadText = findViewById(R.id.textViewSplash);

        //load animasi
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        loadText.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent openMain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(openMain);
                finish();

            }
        },3000); //set waktu loading
    }
}