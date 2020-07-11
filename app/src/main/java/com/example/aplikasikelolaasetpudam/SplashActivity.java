package com.example.aplikasikelolaasetpudam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.aplikasikelolaasetpudam.Controllers.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;
    public static boolean Login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences =getSharedPreferences("my_preferences", MODE_PRIVATE);

                if(!preferences.getBoolean("onboarding_complete",false)) {
                    Intent onboarding = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(onboarding);
                    finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
