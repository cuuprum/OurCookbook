package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

public class SplashScreenActivity extends AppCompatActivity {

    // Splsh timer time out
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MovieActivity.class));

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
