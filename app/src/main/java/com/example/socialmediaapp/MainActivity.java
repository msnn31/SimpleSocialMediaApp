package com.example.socialmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView logoimage;
    ProgressBar progressBar;
    private Animation animFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoimage = findViewById(R.id.logoimage);
        progressBar = findViewById(R.id.progressBar);
        animFadeIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.activity_fade__in);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();

            if (network != null) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logoimage.setVisibility(View.VISIBLE);
                        logoimage.startAnimation(animFadeIn);
//                        progressBar.setVisibility(View.VISIBLE);

                        startActivity(new Intent(MainActivity.this, Register.class));
                    }
                }, 7000);
            }

            else {
                Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}