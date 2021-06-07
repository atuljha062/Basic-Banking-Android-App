package com.atul.banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation logoAnimation, textAnimation;

    ImageView logo;
    TextView logoText,createdByText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logoAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        textAnimation = AnimationUtils.loadAnimation(this,R.anim.text_animation);

        logo = (ImageView) findViewById(R.id.bankLogoSplash);
        logoText = (TextView) findViewById(R.id.logoTextSplash);
        createdByText = (TextView) findViewById(R.id.createdByText);

        logo.setAnimation(logoAnimation);
        logoText.setAnimation(textAnimation);
        createdByText.setAnimation(textAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}